/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.College;
import model.Office;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.School;
import services.exceptions.NonexistentEntityException;

/**
 *
 * @author jmatar
 */
public class SchoolJpaController implements Serializable {

    public SchoolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(School school) {
        if (school.getOfficeList() == null) {
            school.setOfficeList(new ArrayList<Office>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            College collegeID = school.getCollegeID();
            if (collegeID != null) {
                collegeID = em.getReference(collegeID.getClass(), collegeID.getId());
                school.setCollegeID(collegeID);
            }
            List<Office> attachedOfficeList = new ArrayList<Office>();
            for (Office officeListOfficeToAttach : school.getOfficeList()) {
                officeListOfficeToAttach = em.getReference(officeListOfficeToAttach.getClass(), officeListOfficeToAttach.getId());
                attachedOfficeList.add(officeListOfficeToAttach);
            }
            school.setOfficeList(attachedOfficeList);
            em.persist(school);
            if (collegeID != null) {
                collegeID.getSchoolList().add(school);
                collegeID = em.merge(collegeID);
            }
            for (Office officeListOffice : school.getOfficeList()) {
                School oldSchoolIDOfOfficeListOffice = officeListOffice.getSchoolID();
                officeListOffice.setSchoolID(school);
                officeListOffice = em.merge(officeListOffice);
                if (oldSchoolIDOfOfficeListOffice != null) {
                    oldSchoolIDOfOfficeListOffice.getOfficeList().remove(officeListOffice);
                    oldSchoolIDOfOfficeListOffice = em.merge(oldSchoolIDOfOfficeListOffice);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(School school) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            School persistentSchool = em.find(School.class, school.getId());
            College collegeIDOld = persistentSchool.getCollegeID();
            College collegeIDNew = school.getCollegeID();
            List<Office> officeListOld = persistentSchool.getOfficeList();
            List<Office> officeListNew = school.getOfficeList();
            if (collegeIDNew != null) {
                collegeIDNew = em.getReference(collegeIDNew.getClass(), collegeIDNew.getId());
                school.setCollegeID(collegeIDNew);
            }
            List<Office> attachedOfficeListNew = new ArrayList<Office>();
            for (Office officeListNewOfficeToAttach : officeListNew) {
                officeListNewOfficeToAttach = em.getReference(officeListNewOfficeToAttach.getClass(), officeListNewOfficeToAttach.getId());
                attachedOfficeListNew.add(officeListNewOfficeToAttach);
            }
            officeListNew = attachedOfficeListNew;
            school.setOfficeList(officeListNew);
            school = em.merge(school);
            if (collegeIDOld != null && !collegeIDOld.equals(collegeIDNew)) {
                collegeIDOld.getSchoolList().remove(school);
                collegeIDOld = em.merge(collegeIDOld);
            }
            if (collegeIDNew != null && !collegeIDNew.equals(collegeIDOld)) {
                collegeIDNew.getSchoolList().add(school);
                collegeIDNew = em.merge(collegeIDNew);
            }
            for (Office officeListOldOffice : officeListOld) {
                if (!officeListNew.contains(officeListOldOffice)) {
                    officeListOldOffice.setSchoolID(null);
                    officeListOldOffice = em.merge(officeListOldOffice);
                }
            }
            for (Office officeListNewOffice : officeListNew) {
                if (!officeListOld.contains(officeListNewOffice)) {
                    School oldSchoolIDOfOfficeListNewOffice = officeListNewOffice.getSchoolID();
                    officeListNewOffice.setSchoolID(school);
                    officeListNewOffice = em.merge(officeListNewOffice);
                    if (oldSchoolIDOfOfficeListNewOffice != null && !oldSchoolIDOfOfficeListNewOffice.equals(school)) {
                        oldSchoolIDOfOfficeListNewOffice.getOfficeList().remove(officeListNewOffice);
                        oldSchoolIDOfOfficeListNewOffice = em.merge(oldSchoolIDOfOfficeListNewOffice);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = school.getId();
                if (findSchool(id) == null) {
                    throw new NonexistentEntityException("The school with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            School school;
            try {
                school = em.getReference(School.class, id);
                school.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The school with id " + id + " no longer exists.", enfe);
            }
            College collegeID = school.getCollegeID();
            if (collegeID != null) {
                collegeID.getSchoolList().remove(school);
                collegeID = em.merge(collegeID);
            }
            List<Office> officeList = school.getOfficeList();
            for (Office officeListOffice : officeList) {
                officeListOffice.setSchoolID(null);
                officeListOffice = em.merge(officeListOffice);
            }
            em.remove(school);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<School> findSchoolEntities() {
        return findSchoolEntities(true, -1, -1);
    }

    public List<School> findSchoolEntities(int maxResults, int firstResult) {
        return findSchoolEntities(false, maxResults, firstResult);
    }

    private List<School> findSchoolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(School.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public School findSchool(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(School.class, id);
        } finally {
            em.close();
        }
    }

    public int getSchoolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<School> rt = cq.from(School.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

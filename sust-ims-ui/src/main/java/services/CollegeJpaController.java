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
import model.Office;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.College;
import model.School;
import services.exceptions.NonexistentEntityException;

/**
 *
 * @author jmatar
 */
public class CollegeJpaController implements Serializable {

    public CollegeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(College college) {
        if (college.getOfficeList() == null) {
            college.setOfficeList(new ArrayList<Office>());
        }
        if (college.getSchoolList() == null) {
            college.setSchoolList(new ArrayList<School>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Office> attachedOfficeList = new ArrayList<Office>();
            for (Office officeListOfficeToAttach : college.getOfficeList()) {
                officeListOfficeToAttach = em.getReference(officeListOfficeToAttach.getClass(), officeListOfficeToAttach.getId());
                attachedOfficeList.add(officeListOfficeToAttach);
            }
            college.setOfficeList(attachedOfficeList);
            List<School> attachedSchoolList = new ArrayList<School>();
            for (School schoolListSchoolToAttach : college.getSchoolList()) {
                schoolListSchoolToAttach = em.getReference(schoolListSchoolToAttach.getClass(), schoolListSchoolToAttach.getId());
                attachedSchoolList.add(schoolListSchoolToAttach);
            }
            college.setSchoolList(attachedSchoolList);
            em.persist(college);
            for (Office officeListOffice : college.getOfficeList()) {
                College oldCollegeIDOfOfficeListOffice = officeListOffice.getCollegeID();
                officeListOffice.setCollegeID(college);
                officeListOffice = em.merge(officeListOffice);
                if (oldCollegeIDOfOfficeListOffice != null) {
                    oldCollegeIDOfOfficeListOffice.getOfficeList().remove(officeListOffice);
                    oldCollegeIDOfOfficeListOffice = em.merge(oldCollegeIDOfOfficeListOffice);
                }
            }
            for (School schoolListSchool : college.getSchoolList()) {
                College oldCollegeIDOfSchoolListSchool = schoolListSchool.getCollegeID();
                schoolListSchool.setCollegeID(college);
                schoolListSchool = em.merge(schoolListSchool);
                if (oldCollegeIDOfSchoolListSchool != null) {
                    oldCollegeIDOfSchoolListSchool.getSchoolList().remove(schoolListSchool);
                    oldCollegeIDOfSchoolListSchool = em.merge(oldCollegeIDOfSchoolListSchool);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(College college) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            College persistentCollege = em.find(College.class, college.getId());
            List<Office> officeListOld = persistentCollege.getOfficeList();
            List<Office> officeListNew = college.getOfficeList();
            List<School> schoolListOld = persistentCollege.getSchoolList();
            List<School> schoolListNew = college.getSchoolList();
            List<Office> attachedOfficeListNew = new ArrayList<Office>();
            for (Office officeListNewOfficeToAttach : officeListNew) {
                officeListNewOfficeToAttach = em.getReference(officeListNewOfficeToAttach.getClass(), officeListNewOfficeToAttach.getId());
                attachedOfficeListNew.add(officeListNewOfficeToAttach);
            }
            officeListNew = attachedOfficeListNew;
            college.setOfficeList(officeListNew);
            List<School> attachedSchoolListNew = new ArrayList<School>();
            for (School schoolListNewSchoolToAttach : schoolListNew) {
                schoolListNewSchoolToAttach = em.getReference(schoolListNewSchoolToAttach.getClass(), schoolListNewSchoolToAttach.getId());
                attachedSchoolListNew.add(schoolListNewSchoolToAttach);
            }
            schoolListNew = attachedSchoolListNew;
            college.setSchoolList(schoolListNew);
            college = em.merge(college);
            for (Office officeListOldOffice : officeListOld) {
                if (!officeListNew.contains(officeListOldOffice)) {
                    officeListOldOffice.setCollegeID(null);
                    officeListOldOffice = em.merge(officeListOldOffice);
                }
            }
            for (Office officeListNewOffice : officeListNew) {
                if (!officeListOld.contains(officeListNewOffice)) {
                    College oldCollegeIDOfOfficeListNewOffice = officeListNewOffice.getCollegeID();
                    officeListNewOffice.setCollegeID(college);
                    officeListNewOffice = em.merge(officeListNewOffice);
                    if (oldCollegeIDOfOfficeListNewOffice != null && !oldCollegeIDOfOfficeListNewOffice.equals(college)) {
                        oldCollegeIDOfOfficeListNewOffice.getOfficeList().remove(officeListNewOffice);
                        oldCollegeIDOfOfficeListNewOffice = em.merge(oldCollegeIDOfOfficeListNewOffice);
                    }
                }
            }
            for (School schoolListOldSchool : schoolListOld) {
                if (!schoolListNew.contains(schoolListOldSchool)) {
                    schoolListOldSchool.setCollegeID(null);
                    schoolListOldSchool = em.merge(schoolListOldSchool);
                }
            }
            for (School schoolListNewSchool : schoolListNew) {
                if (!schoolListOld.contains(schoolListNewSchool)) {
                    College oldCollegeIDOfSchoolListNewSchool = schoolListNewSchool.getCollegeID();
                    schoolListNewSchool.setCollegeID(college);
                    schoolListNewSchool = em.merge(schoolListNewSchool);
                    if (oldCollegeIDOfSchoolListNewSchool != null && !oldCollegeIDOfSchoolListNewSchool.equals(college)) {
                        oldCollegeIDOfSchoolListNewSchool.getSchoolList().remove(schoolListNewSchool);
                        oldCollegeIDOfSchoolListNewSchool = em.merge(oldCollegeIDOfSchoolListNewSchool);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = college.getId();
                if (findCollege(id) == null) {
                    throw new NonexistentEntityException("The college with id " + id + " no longer exists.");
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
            College college;
            try {
                college = em.getReference(College.class, id);
                college.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The college with id " + id + " no longer exists.", enfe);
            }
            List<Office> officeList = college.getOfficeList();
            for (Office officeListOffice : officeList) {
                officeListOffice.setCollegeID(null);
                officeListOffice = em.merge(officeListOffice);
            }
            List<School> schoolList = college.getSchoolList();
            for (School schoolListSchool : schoolList) {
                schoolListSchool.setCollegeID(null);
                schoolListSchool = em.merge(schoolListSchool);
            }
            em.remove(college);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<College> findCollegeEntities() {
        return findCollegeEntities(true, -1, -1);
    }

    public List<College> findCollegeEntities(int maxResults, int firstResult) {
        return findCollegeEntities(false, maxResults, firstResult);
    }

    private List<College> findCollegeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(College.class));
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

    public College findCollege(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(College.class, id);
        } finally {
            em.close();
        }
    }

    public int getCollegeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<College> rt = cq.from(College.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

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
import model.School;
import model.Device;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Office;
import services.exceptions.NonexistentEntityException;

/**
 *
 * @author jmatar
 */
public class OfficeJpaController implements Serializable {

    public OfficeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Office office) {
        if (office.getDeviceList() == null) {
            office.setDeviceList(new ArrayList<Device>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            College collegeID = office.getCollegeID();
            if (collegeID != null) {
                collegeID = em.getReference(collegeID.getClass(), collegeID.getId());
                office.setCollegeID(collegeID);
            }
            School schoolID = office.getSchoolID();
            if (schoolID != null) {
                schoolID = em.getReference(schoolID.getClass(), schoolID.getId());
                office.setSchoolID(schoolID);
            }
            List<Device> attachedDeviceList = new ArrayList<Device>();
            for (Device deviceListDeviceToAttach : office.getDeviceList()) {
                deviceListDeviceToAttach = em.getReference(deviceListDeviceToAttach.getClass(), deviceListDeviceToAttach.getId());
                attachedDeviceList.add(deviceListDeviceToAttach);
            }
            office.setDeviceList(attachedDeviceList);
            em.persist(office);
            if (collegeID != null) {
                collegeID.getOfficeList().add(office);
                collegeID = em.merge(collegeID);
            }
            if (schoolID != null) {
                office.setCollegeID(schoolID.getCollegeID());
                schoolID.getOfficeList().add(office);
                schoolID = em.merge(schoolID);
            }
            for (Device deviceListDevice : office.getDeviceList()) {
                Office oldOfficeIDOfDeviceListDevice = deviceListDevice.getOfficeID();
                deviceListDevice.setOfficeID(office);
                deviceListDevice = em.merge(deviceListDevice);
                if (oldOfficeIDOfDeviceListDevice != null) {
                    oldOfficeIDOfDeviceListDevice.getDeviceList().remove(deviceListDevice);
                    oldOfficeIDOfDeviceListDevice = em.merge(oldOfficeIDOfDeviceListDevice);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Office office) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Office persistentOffice = em.find(Office.class, office.getId());
            College collegeIDOld = persistentOffice.getCollegeID();
            College collegeIDNew = office.getCollegeID();
            School schoolIDOld = persistentOffice.getSchoolID();
            School schoolIDNew = office.getSchoolID();
            List<Device> deviceListOld = persistentOffice.getDeviceList();
            List<Device> deviceListNew = office.getDeviceList();
            if (collegeIDNew != null) {
                collegeIDNew = em.getReference(collegeIDNew.getClass(), collegeIDNew.getId());
                office.setCollegeID(collegeIDNew);
            }
            if (schoolIDNew != null) {
                schoolIDNew = em.getReference(schoolIDNew.getClass(), schoolIDNew.getId());
                office.setSchoolID(schoolIDNew);
            }
            List<Device> attachedDeviceListNew = new ArrayList<Device>();
            for (Device deviceListNewDeviceToAttach : deviceListNew) {
                deviceListNewDeviceToAttach = em.getReference(deviceListNewDeviceToAttach.getClass(), deviceListNewDeviceToAttach.getId());
                attachedDeviceListNew.add(deviceListNewDeviceToAttach);
            }
            deviceListNew = attachedDeviceListNew;
            office.setDeviceList(deviceListNew);
            office = em.merge(office);
            if (collegeIDOld != null && !collegeIDOld.equals(collegeIDNew)) {
                collegeIDOld.getOfficeList().remove(office);
                collegeIDOld = em.merge(collegeIDOld);
            }
            if (collegeIDNew != null && !collegeIDNew.equals(collegeIDOld)) {
                collegeIDNew.getOfficeList().add(office);
                collegeIDNew = em.merge(collegeIDNew);
            }
            if (schoolIDOld != null && !schoolIDOld.equals(schoolIDNew)) {
                schoolIDOld.getOfficeList().remove(office);
                schoolIDOld = em.merge(schoolIDOld);
            }
            if (schoolIDNew != null && !schoolIDNew.equals(schoolIDOld)) {
                schoolIDNew.getOfficeList().add(office);
                schoolIDNew = em.merge(schoolIDNew);
            }
            for (Device deviceListOldDevice : deviceListOld) {
                if (!deviceListNew.contains(deviceListOldDevice)) {
                    deviceListOldDevice.setOfficeID(null);
                    deviceListOldDevice = em.merge(deviceListOldDevice);
                }
            }
            for (Device deviceListNewDevice : deviceListNew) {
                if (!deviceListOld.contains(deviceListNewDevice)) {
                    Office oldOfficeIDOfDeviceListNewDevice = deviceListNewDevice.getOfficeID();
                    deviceListNewDevice.setOfficeID(office);
                    deviceListNewDevice = em.merge(deviceListNewDevice);
                    if (oldOfficeIDOfDeviceListNewDevice != null && !oldOfficeIDOfDeviceListNewDevice.equals(office)) {
                        oldOfficeIDOfDeviceListNewDevice.getDeviceList().remove(deviceListNewDevice);
                        oldOfficeIDOfDeviceListNewDevice = em.merge(oldOfficeIDOfDeviceListNewDevice);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = office.getId();
                if (findOffice(id) == null) {
                    throw new NonexistentEntityException("The office with id " + id + " no longer exists.");
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
            Office office;
            try {
                office = em.getReference(Office.class, id);
                office.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The office with id " + id + " no longer exists.", enfe);
            }
            College collegeID = office.getCollegeID();
            if (collegeID != null) {
                collegeID.getOfficeList().remove(office);
                collegeID = em.merge(collegeID);
            }
            School schoolID = office.getSchoolID();
            if (schoolID != null) {
                schoolID.getOfficeList().remove(office);
                schoolID = em.merge(schoolID);
            }
            List<Device> deviceList = office.getDeviceList();
            for (Device deviceListDevice : deviceList) {
                deviceListDevice.setOfficeID(null);
                deviceListDevice = em.merge(deviceListDevice);
            }
            em.remove(office);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Office> findOfficeEntities() {
        return findOfficeEntities(true, -1, -1);
    }

    public List<Office> findOfficeEntities(int maxResults, int firstResult) {
        return findOfficeEntities(false, maxResults, firstResult);
    }

    private List<Office> findOfficeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Office.class));
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

    public Office findOffice(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Office.class, id);
        } finally {
            em.close();
        }
    }

    public int getOfficeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Office> rt = cq.from(Office.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

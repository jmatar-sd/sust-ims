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
import model.Property;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Device;
import services.exceptions.NonexistentEntityException;

/**
 *
 * @author jmatar
 */
public class DeviceJpaController implements Serializable {

    public DeviceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Device device) {
        if (device.getPropertyList() == null) {
            device.setPropertyList(new ArrayList<Property>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Office officeID = device.getOfficeID();
            if (officeID != null) {
                officeID = em.getReference(officeID.getClass(), officeID.getId());
                device.setOfficeID(officeID);
            }
            List<Property> attachedPropertyList = new ArrayList<>();
            for (Property propertyListPropertyToAttach : device.getPropertyList()) {
                PropertyJpaController propertyService = new PropertyJpaController(emf);
                propertyService.create(propertyListPropertyToAttach);
                propertyListPropertyToAttach = em.getReference(propertyListPropertyToAttach.getClass(), propertyListPropertyToAttach.getId());
                attachedPropertyList.add(propertyListPropertyToAttach);
            }
            device.setPropertyList(attachedPropertyList);
            em.persist(device);
            if (officeID != null) {
                officeID.getDeviceList().add(device);
                officeID = em.merge(officeID);
            }
            for (Property propertyListProperty : device.getPropertyList()) {
                Device oldDeviceIDOfPropertyListProperty = propertyListProperty.getDeviceID();
                propertyListProperty.setDeviceID(device);
                propertyListProperty = em.merge(propertyListProperty);
                if (oldDeviceIDOfPropertyListProperty != null) {
                    oldDeviceIDOfPropertyListProperty.getPropertyList().remove(propertyListProperty);
                    oldDeviceIDOfPropertyListProperty = em.merge(oldDeviceIDOfPropertyListProperty);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Device device) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Device persistentDevice = em.find(Device.class, device.getId());
            Office officeIDOld = persistentDevice.getOfficeID();
            Office officeIDNew = device.getOfficeID();
            List<Property> propertyListOld = persistentDevice.getPropertyList();
            List<Property> propertyListNew = device.getPropertyList();
            if (officeIDNew != null) {
                officeIDNew = em.getReference(officeIDNew.getClass(), officeIDNew.getId());
                device.setOfficeID(officeIDNew);
            }
            List<Property> attachedPropertyListNew = new ArrayList<Property>();
            for (Property propertyListNewPropertyToAttach : propertyListNew) {
                propertyListNewPropertyToAttach = em.getReference(propertyListNewPropertyToAttach.getClass(), propertyListNewPropertyToAttach.getId());
                attachedPropertyListNew.add(propertyListNewPropertyToAttach);
            }
            propertyListNew = attachedPropertyListNew;
            device.setPropertyList(propertyListNew);
            device = em.merge(device);
            if (officeIDOld != null && !officeIDOld.equals(officeIDNew)) {
                officeIDOld.getDeviceList().remove(device);
                officeIDOld = em.merge(officeIDOld);
            }
            if (officeIDNew != null && !officeIDNew.equals(officeIDOld)) {
                officeIDNew.getDeviceList().add(device);
                officeIDNew = em.merge(officeIDNew);
            }
            for (Property propertyListOldProperty : propertyListOld) {
                if (!propertyListNew.contains(propertyListOldProperty)) {
                    propertyListOldProperty.setDeviceID(null);
                    propertyListOldProperty = em.merge(propertyListOldProperty);
                }
            }
            for (Property propertyListNewProperty : propertyListNew) {
                if (!propertyListOld.contains(propertyListNewProperty)) {
                    Device oldDeviceIDOfPropertyListNewProperty = propertyListNewProperty.getDeviceID();
                    propertyListNewProperty.setDeviceID(device);
                    propertyListNewProperty = em.merge(propertyListNewProperty);
                    if (oldDeviceIDOfPropertyListNewProperty != null && !oldDeviceIDOfPropertyListNewProperty.equals(device)) {
                        oldDeviceIDOfPropertyListNewProperty.getPropertyList().remove(propertyListNewProperty);
                        oldDeviceIDOfPropertyListNewProperty = em.merge(oldDeviceIDOfPropertyListNewProperty);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = device.getId();
                if (findDevice(id) == null) {
                    throw new NonexistentEntityException("The device with id " + id + " no longer exists.");
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
            Device device;
            try {
                device = em.getReference(Device.class, id);
                device.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The device with id " + id + " no longer exists.", enfe);
            }
            Office officeID = device.getOfficeID();
            if (officeID != null) {
                officeID.getDeviceList().remove(device);
                officeID = em.merge(officeID);
            }
            List<Property> propertyList = device.getPropertyList();
            for (Property propertyListProperty : propertyList) {
                propertyListProperty.setDeviceID(null);
                propertyListProperty = em.merge(propertyListProperty);
            }
            em.remove(device);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Device> findDeviceEntities() {
        return findDeviceEntities(true, -1, -1);
    }

    public List<Device> findDeviceEntities(int maxResults, int firstResult) {
        return findDeviceEntities(false, maxResults, firstResult);
    }

    private List<Device> findDeviceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Device.class));
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

    public Device findDevice(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Device.class, id);
        } finally {
            em.close();
        }
    }

    public int getDeviceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Device> rt = cq.from(Device.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

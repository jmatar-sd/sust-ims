/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Device;
import model.Property;
import services.exceptions.NonexistentEntityException;

/**
 *
 * @author jmatar
 */
public class PropertyJpaController implements Serializable {

    public PropertyJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Property property) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Device deviceID = property.getDeviceID();
            if (deviceID != null) {
                deviceID = em.getReference(deviceID.getClass(), deviceID.getId());
                property.setDeviceID(deviceID);
            }
            em.persist(property);
            if (deviceID != null) {
                deviceID.getPropertyList().add(property);
                deviceID = em.merge(deviceID);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Property property) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Property persistentProperty = em.find(Property.class, property.getId());
            Device deviceIDOld = persistentProperty.getDeviceID();
            Device deviceIDNew = property.getDeviceID();
            if (deviceIDNew != null) {
                deviceIDNew = em.getReference(deviceIDNew.getClass(), deviceIDNew.getId());
                property.setDeviceID(deviceIDNew);
            }
            property = em.merge(property);
            if (deviceIDOld != null && !deviceIDOld.equals(deviceIDNew)) {
                deviceIDOld.getPropertyList().remove(property);
                deviceIDOld = em.merge(deviceIDOld);
            }
            if (deviceIDNew != null && !deviceIDNew.equals(deviceIDOld)) {
                deviceIDNew.getPropertyList().add(property);
                deviceIDNew = em.merge(deviceIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = property.getId();
                if (findProperty(id) == null) {
                    throw new NonexistentEntityException("The property with id " + id + " no longer exists.");
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
            Property property;
            try {
                property = em.getReference(Property.class, id);
                property.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The property with id " + id + " no longer exists.", enfe);
            }
            Device deviceID = property.getDeviceID();
            if (deviceID != null) {
                deviceID.getPropertyList().remove(property);
                deviceID = em.merge(deviceID);
            }
            em.remove(property);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Property> findPropertyEntities() {
        return findPropertyEntities(true, -1, -1);
    }

    public List<Property> findPropertyEntities(int maxResults, int firstResult) {
        return findPropertyEntities(false, maxResults, firstResult);
    }

    private List<Property> findPropertyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Property.class));
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

    public Property findProperty(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Property.class, id);
        } finally {
            em.close();
        }
    }

    public int getPropertyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Property> rt = cq.from(Property.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

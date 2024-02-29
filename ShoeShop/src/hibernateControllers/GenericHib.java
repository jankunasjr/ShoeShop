package hibernateControllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import model.Cart;
import model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GenericHib {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;

    public GenericHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    //<T> indikuos, kad yra generic method. Ka tik padariau visu klasiu create backend metoda
    public <T> void create(T entity) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }

    //Ka tik padariau update visoms esybems
    public <T> void update(T entity) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }

    //Su delete bus niuansai, kol kas nerasau
    public <T> void delete(Class<T> entityClass, int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            var object = em.find(entityClass, id);
            em.remove(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }


    //Get by id visoms esybems READ
    public <T> T getEntityById(Class<T> entityClass, int id) {
        T result = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            result = em.find(entityClass, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //READ operacija, tik istrauks visus irasus, kurie yra DB

    public <T> List<T> getAllRecords(Class<T> entityClass) {
        EntityManager em = null;
        List<T> result = new ArrayList<>();
        try {
            em = getEntityManager();
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(entityClass));
            Query q = em.createQuery(query);
            result = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return result;
    }

    public List<Cart> filterData(double minCost, double maxCost, int userId, LocalDate fromDate, LocalDate toDate) {
        EntityManager em = null;
        List<Cart> result = new ArrayList<>();
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cart> cq = cb.createQuery(Cart.class);

            Root<Cart> cart = cq.from(Cart.class);
            Join<Cart, User> userJoin = cart.join("user"); // Joining Cart with User
            List<Predicate> predicates = new ArrayList<>();

            // Filter by minimum cart value
            if (minCost > 0) {
                predicates.add(cb.ge(cart.get("cart_value"), minCost));
            }

            if (maxCost > 0) {
                predicates.add(cb.le(cart.get("cart_value"), maxCost));
            }
            // Filter by user ID
            if (userId > 0) {
                predicates.add(cb.equal(userJoin.get("id"), userId));
            }
            // Filter by date range
            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(cart.get("dateCreated"), fromDate));
            }
            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(cart.get("dateCreated"), toDate));
            }

            cq.where(predicates.toArray(new Predicate[0]));
            result = em.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return result;
    }

    public List<Cart> getCartsForUser(int userId) {
        EntityManager em = null;
        List<Cart> result = new ArrayList<>();
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cart> cq = cb.createQuery(Cart.class);

            Root<Cart> cart = cq.from(Cart.class);
            Join<Cart, User> userJoin = cart.join("user"); // Joining Cart with User

            // Filter by user ID
            Predicate userPredicate = cb.equal(userJoin.get("id"), userId);

            cq.where(userPredicate);
            result = em.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return result;
    }



}

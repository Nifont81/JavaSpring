package ru.geekbrains.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ProductRepository {

    private final EntityManagerFactory emFactory;

    public ProductRepository(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    public void close() {
        emFactory.close();
    }

    /**
     * Возвращает список товаров, купленных клиентом
     * @param id клиента
     * @return Список товаров
     */
    public List<Product> findProductsByClientId(long id) {
        EntityManager em = emFactory.createEntityManager();

        List list = em.createNativeQuery("SELECT * FROM products as p WHERE p.id IN " +
                "(SELECT product_id FROM clients_products WHERE client_id=:clientId)",Product.class)
                .setParameter("clientId",id)
                .getResultList();

        em.close();

        return list;
    }

    public List<Product> findAll() {
        EntityManager em = emFactory.createEntityManager();
        List<Product> list = em.createNamedQuery("allProducts", Product.class).getResultList();
        em.close();
        return list;

//        return executeForEntityManager(
//                em -> em.createNamedQuery("allProducts", Product.class)).getResultList();
    }

    public Product findById(long id) {
        return executeForEntityManager(em -> em.find(Product.class, id));
    }

    public void insert(Product product) {
        executeInTransaction(em -> em.persist(product));
    }

    public void update(Product product) {
        executeInTransaction(em -> em.merge(product));
    }

    public void delete(long id) {
        executeInTransaction(em -> {
            Product productDel = em.find(Product.class, id);
            if (productDel != null) em.remove(productDel);
        });
    }

    private <R> R executeForEntityManager(Function<EntityManager, R> function) {
        EntityManager em = emFactory.createEntityManager();
        try {
            return function.apply(em);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private void executeInTransaction(Consumer<EntityManager> consumer) {
        EntityManager em = emFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            consumer.accept(em);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}

package ru.geekbrains.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ClientRepository {

    private final EntityManagerFactory emFactory;

    public ClientRepository(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    public void close() {
        emFactory.close();
    }

    /**
     * Выдает список клиентов, купивших товар
     * @param id товара
     * @return Список клиентов
     */
    public List<Client> findProductsByClientId(long id) {
        EntityManager em = emFactory.createEntityManager();

        List list = em.createNativeQuery("SELECT * from clients as c where c.id IN (" +
                "    SELECT client_id FROM  clients_products where product_id=:productId)",Client.class)
                .setParameter("productId",id)
                .getResultList();
        em.close();

        return list;
    }

    public List<Client> findAll() {
        EntityManager em = emFactory.createEntityManager();
        List<Client> list = em.createNamedQuery("allClients", Client.class).getResultList();
        em.close();
        return list;

//        return executeForEntityManager(
//                em -> em.createNamedQuery("allProducts", Product.class)).getResultList();
    }

    public Client findById(long id) {
        return executeForEntityManager(em -> em.find(Client.class, id));
    }

    public void insert(Client client) {
        executeInTransaction(em -> em.persist(client));
    }

    public void update(Client client) {
        executeInTransaction(em -> em.merge(client));
    }

    public void delete(long id) {
        executeInTransaction(em -> {
            Client clientDel = em.find(Client.class, id);
            if (clientDel != null) em.remove(clientDel);
        });
    }

    private <R> R executeForEntityManager(Function<EntityManager, R> function) {
        EntityManager em = emFactory.createEntityManager();
        try {
            R client = function.apply(em);
            return client;
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

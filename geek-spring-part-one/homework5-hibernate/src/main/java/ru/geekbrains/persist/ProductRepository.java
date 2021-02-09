package ru.geekbrains.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ProductRepository {

    private final EntityManagerFactory emFactory;
    private EntityManager em;

    public ProductRepository(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
        em = emFactory.createEntityManager();
    }

    public void close(){
        em.close();
        emFactory.close();
    }

    public List findAll() {
        return em.createNamedQuery("allProducts").getResultList();
    }

    public Product findById(long id) {
        Product product = (Product) em.createNamedQuery("findById")
                .setParameter("id", id)
                .getSingleResult();
        return product;
    }

    public void insert(Product product) {
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }

    public void update(Product product) {
        Product productUpdate = em.find(Product.class, product.getId());
        if (productUpdate == null) return;

        em.getTransaction().begin();
        productUpdate.setName(product.getName());
        productUpdate.setDescription(product.getDescription());
        productUpdate.setPrice(product.getPrice());
        em.getTransaction().commit();
    }

    public void delete(long id) {
        Product productDel = em.find(Product.class, id);
        if (productDel == null) return;

        em.getTransaction().begin();
        em.remove(productDel);
        em.getTransaction().commit();
    }

}

package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductRepository {

    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    @PersistenceContext // Внедрение нужного em
    private EntityManager em;

    public List<Product> findAll() {
        return em.createQuery("from Product", Product.class).getResultList();
    }

    public Product findById(long id) {
        return em.find(Product.class, id);
    }

    public void insert(Product product) {
        try {
            em.persist(product);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.info("ERRROR ADD!!!" + product);
        }
        // Для rollback нужно кинуть исключение!
    }

    public void update(Product product) {
        em.merge(product);
    }

    public void delete(long id) {
//        Product product = findbyId(id);
//        if (product != null ) {
//            em.remove(product);
//        }

        em.createQuery("delete from Product where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

}

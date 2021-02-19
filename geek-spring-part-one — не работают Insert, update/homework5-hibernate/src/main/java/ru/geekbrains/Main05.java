package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;

public class Main05 {
    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration() // Входная точка Hibernate
                .configure("hibernate.cfg.xml") // emF - потокобезопасный
                .buildSessionFactory();

        ProductRepository pr = new ProductRepository(emFactory);

        Product product = new Product("Процессор i7 3","Intel i7-9700K 3", new BigDecimal(26000));
        //pr.insert(product);

        product = new Product("Мышь a4","Мышь Genius", new BigDecimal(100.3));
        product.setId(3L);
        pr.update(product);

        System.out.println(pr.findAll());

//        pr.delete(15);

        System.out.println(pr.findById(4));

        pr.close();
    }

//    // SELECT
//    Product product1 = em.find(Product.class, 1L);
//        System.out.println(product1);
//
//    Product product2 = (Product) em.createQuery("from Product p where p.name = :name")
//            .setParameter("name", "Продукт 3")
//            .getSingleResult();
//
//        System.out.println(product2);
//
//
//        em.createQuery("delete from Product where name=:name")
//                .setParameter("name", "Продукт 6")
//                .executeUpdate();
//        em.getTransaction().commit();
//
//    //HQL, JPQL
//    List<Product> productList = em.createQuery("from Product ", Product.class).getResultList();
//
//        for (Product product : productList) {
//        System.out.println(product);
//    }
//
//        em.close();
}

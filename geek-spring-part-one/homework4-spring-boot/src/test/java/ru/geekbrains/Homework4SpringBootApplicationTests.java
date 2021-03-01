package ru.geekbrains;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.geekbrains.persist.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class Homework4SpringBootApplicationTests {

    @PersistenceContext
    private EntityManager em;

    @Test
    void contextLoads() {
        List<Product> products = findWithFilter(null,null, null);

        products.forEach(System.out::println);

    }

    List<Product> findWithFilter(String nameFilter, Double minPrice, Double maxPrice){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class); // Аналогичный Product.class, что и в  createQuery

        // Создает и добавляет корень запроса
        Root<Product> from = query.from(Product.class);// этот Product, который после from в запросе (откуда получаем данные)

        List<Predicate> predicates = new ArrayList<>();

        // Условия - предикаты
        if (nameFilter != null && nameFilter.isBlank()) {
            predicates.add(cb.like(from.get("name"), '%'+nameFilter+'%'));
        }
        if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(from.get("price"), minPrice)); // >= ge
        }
        if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(from.get("price"), maxPrice)); // <= le
        }

        List<Product> products = em.createQuery(query.select(from).where(predicates.toArray(new Predicate[0])))
                .getResultList();

        return products;

    }

}

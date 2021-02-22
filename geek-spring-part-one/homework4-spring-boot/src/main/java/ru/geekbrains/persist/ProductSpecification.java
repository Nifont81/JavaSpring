package ru.geekbrains.persist;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public final class ProductSpecification {

    public static Specification<Product> nameLike(String nameFilter) {

        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("name"), "%" + nameFilter + "%");
            }
        };
    }

    public static Specification<Product> minPrice(Double minPrice) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice); // >= ge
    }

    public static Specification<Product> maxPrice(Double maxPrice) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice); // <= le
    }


}

package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    List<Product> findProductByNameLike(String nameFilter);

    @Query("select p from Product p where p.name like concat('%',:nameFilter,'%')")
    List<Product> findProductByNameLike(@Param("nameFilter") String nameFilter);

    @Query("select p from Product p where p.price between :minPrice and :maxPrice")
    List<Product> findProductByPriceIn(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);
}

package ru.geekbrains.service;

import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.persist.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDTO> findAll();

    Optional<ProductDTO> findById(long id);

    List<ProductDTO> findProductByNameLike(String nameFilter);

    List<ProductDTO> findProductByPriceIn(double minPrice, double maxPrice);

    void save(ProductDTO product);

    void delete(long id);

}

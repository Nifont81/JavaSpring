package ru.geekbrains.service;

import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.persist.Product;

import java.util.List;

public interface ProductService {

    List<ProductDTO> findAll();

    ProductDTO findById(long id);

    void insert(ProductDTO product);

    void update(ProductDTO product);

    void delete(long id);

}

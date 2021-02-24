package ru.geekbrains.service;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDTO> findAll();

    Optional<ProductDTO> findById(long id);

//    List<ProductDTO> findProductByNameLike(String nameFilter);
//
//    List<ProductDTO> findProductByPriceIn(double minPrice, double maxPrice);

    Page<ProductDTO> findWithFilter(String nameFilter, Double minPrice, Double maxPrice,
                                    Integer page, Integer size, String sortBy);

    void save(ProductDTO product);

    void delete(long id);

}

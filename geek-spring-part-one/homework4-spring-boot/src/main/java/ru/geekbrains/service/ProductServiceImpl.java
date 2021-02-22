package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> findAll() {

        return productRepository.findAll().stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());

/*
        List<ProductDTO> list = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            ProductDTO productDTO = new ProductDTO(product);
            list.add(productDTO);
        }
        return list;
*/
    }

    @Override
    public List<ProductDTO> findWithFilter(String nameFilter, Double minPrice, Double maxPrice) {

        Specification<Product> specification = Specification.where(null);

        if (nameFilter != null && !nameFilter.isBlank()) {
            specification = specification.and(ProductSpecification.nameLike(nameFilter));
        }

        if (minPrice != null) {
            specification = specification.and(ProductSpecification.minPrice(minPrice));
        }

        if (maxPrice != null) {
            specification = specification.and(ProductSpecification.maxPrice(maxPrice));
        }

        return productRepository.findAll(specification).stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());

//        return productRepository.findWithFilter(nameFilter, minPrice, maxPrice).stream()
//                .map(ProductDTO::new)
//                .collect(Collectors.toList());
    }

//    @Override
//    public List<ProductDTO> findProductByNameLike(String nameFilter) {
//        return productRepository.findProductByNameLike(nameFilter).stream()
//                .map(ProductDTO::new)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<ProductDTO> findProductByPriceIn(double minPrice, double maxPrice) {
//        return productRepository.findProductByPriceIn(minPrice, maxPrice).stream()
//                .map(ProductDTO::new)
//                .collect(Collectors.toList());
//    }

    //Методы могут включать несколько операций, поэтому @Транзакции нужны здесь
    @Transactional
    @Override
    public Optional<ProductDTO> findById(long id) {
        return productRepository.findById(id)
                .map(ProductDTO::new);

    }

    @Transactional
    @Override
    public void save(ProductDTO productDTO) {
        productRepository.save(new Product(productDTO));
    }

    @Transactional
    @Override
    public void delete(long id) {
        productRepository.deleteById(id);
    }
}

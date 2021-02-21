package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import java.util.List;
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

    //Методы могут включать несколько операций, поэтому @Транзакции нужны здесь
    @Transactional
    @Override
    public ProductDTO findById(long id) {
        Product product = productRepository.findById(id);
        if (product != null) {
            return new ProductDTO(product);
        } else return null;
    }

    @Transactional
    @Override
    public void insert(ProductDTO productDTO) {
        productRepository.insert(new Product(productDTO));
    }

    @Transactional
    @Override
    public void update(ProductDTO productDTO) {
        productRepository.update(new Product(productDTO));
    }

    @Transactional
    @Override
    public void delete(long id) {
        productRepository.delete(id);
    }
}

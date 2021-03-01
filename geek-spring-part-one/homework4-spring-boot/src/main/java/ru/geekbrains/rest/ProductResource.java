package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.BadRequestException;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.service.ProductDTO;
import ru.geekbrains.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductResource {

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path="/all", produces = "application/json")
    public List<ProductDTO> findAll() {
        return productService.findAll();
    }

    @GetMapping(path="/{id}")
    public ProductDTO findById(@PathVariable("id") Long id){
        return productService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(consumes = "application/json")
    public ProductDTO create(@RequestBody ProductDTO productDTO){
        if (productDTO.getId() != null) {
            throw new BadRequestException();
        }

        productService.save(productDTO);
        return productDTO;
    }

    @PutMapping(consumes = "application/json")
    public void update(@RequestBody ProductDTO productDTO){
        if (productDTO.getId() == null) {
            throw new BadRequestException();
        }
        productService.save(productDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        productService.delete(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundException(NotFoundException ex) {
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> badRequestException(BadRequestException ex) {
        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }
}

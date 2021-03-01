package ru.geekbrains.rest;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.BadRequestException;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.service.ProductDTO;
import ru.geekbrains.service.ProductService;

import java.util.List;
import java.util.Optional;
/*
http://localhost:8080/spring-mvc-app/swagger-ui/index.html

http://localhost:8080/spring-mvc-app/v3/api-docs
 */
@Tag(name = "Product resource API", description = "API to manipulate product resource")
@RestController
@RequestMapping("/api/v1/products")
public class ProductResource {

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<ProductDTO> findAll() {
        return productService.findAll();
    }

    @GetMapping(path = "/{id}")
    public ProductDTO findById(@PathVariable("id") Long id) {
        return productService.findById(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping("filter")
    public Page<ProductDTO> listPage(Model model,
                                     @RequestParam("nameFilter") Optional<String> nameFilter,
                                     @RequestParam("minPrice") Optional<Double> minPrice,
                                     @RequestParam("maxPrice") Optional<Double> maxPrice,
                                     @Parameter(example = "1") @RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size,
                                     @RequestParam("sortBy") Optional<String> sortBy) {

        Page<ProductDTO> productDTOPage = productService.findWithFilter(
                nameFilter.orElse(null),
                minPrice.orElse(null),
                maxPrice.orElse(null),
                page.orElse(1) - 1,
                size.orElse(5),
                sortBy.filter(s -> !s.isBlank()).orElse("name")
        );
        // нумерация страниц page начинается с 0: page.orElse(1) - 1

        return productDTOPage;
    }

    @PostMapping(consumes = "application/json")
    public ProductDTO create(@RequestBody ProductDTO productDTO) {
        if (productDTO.getId() != null) {
            throw new BadRequestException();
        }

        productService.save(productDTO);
        return productDTO;
    }

    @PutMapping(consumes = "application/json")
    public void update(@RequestBody ProductDTO productDTO) {
        if (productDTO.getId() == null) {
            throw new BadRequestException();
        }
        productService.save(productDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
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

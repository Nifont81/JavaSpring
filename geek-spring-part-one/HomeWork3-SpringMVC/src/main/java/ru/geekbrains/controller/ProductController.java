package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listPage(Model model) {
        logger.info("List page requested");

        model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) {
        Product product ;
        if (id!=-1) {
            logger.info("Edit page for id {} requested", id);

            model.addAttribute("product", productRepository.findById(id));
            model.addAttribute("title", "Edit Product");

        } else {

            product = new Product("","",0);
            product.setId(-1L);

            model.addAttribute("product", product);
            model.addAttribute("title", "New Product");
            logger.info("New product id = ", id);
        }
        return "product_form";
    }

    @PostMapping("/update")
    public String update(Product product) {
        logger.info("Update endpoint requested");

        if (product.getId() != -1) {
            logger.info("Updating Product with id {}", product.getId());
            productRepository.update(product);

        } else {
            logger.info("Creating new Product");
            productRepository.insert(product);
        }
        return "redirect:/products";
    }

    @GetMapping("/new")
    public String create() {
        // TODO
        return null;
    }

    @GetMapping("/{id}/delete")
    public String remove(Model model, @PathVariable("id") Long id) {

        model.addAttribute("product", productRepository.findById(id));
        productRepository.delete(id);

        return "delete";
    }
}

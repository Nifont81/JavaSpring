package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.validation.Valid;

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
        Product product;
        logger.info("Edit page for id {} requested", id);

        model.addAttribute("product", productRepository.findById(id));
        model.addAttribute("title", "Edit Product");

        return "product_form";
    }

    @PostMapping("/update")
    public String update(@Valid Product product, BindingResult result) {
        logger.info("Update endpoint requested");

        if (result.hasErrors()) {
            return "product_form";
        }

        if (product.getId() != -1) {
            logger.info("Updating Product with id {}", product.getId());
            productRepository.update(product);

        } else {
            logger.info("Creating new Product");
            productRepository.insert(product);
        }
        return "redirect:/products";
    }

//        if (!user.getPassword().equals(user.getMatchingPassword())) {
//            result.rejectValue("password", "", "Password not matching");
//            return "user_form";
//        }

     @GetMapping("/new")
    public String create(Model model) {
        Product product = new Product("", "", 0);
        product.setId(-1L);

        model.addAttribute("product", product);
        model.addAttribute("title", "Создание нового товара");
        logger.info("Создание нового товара");

        return "product_form";
    }

    @DeleteMapping("/{id}")
    public String remove(Model model, @PathVariable("id") Long id) {
        Product product = productRepository.findById(id);
        model.addAttribute("product", product);
        productRepository.delete(id);
        logger.info("Продукт " + product.getName() + " удален");

        return "delete";
    }
}

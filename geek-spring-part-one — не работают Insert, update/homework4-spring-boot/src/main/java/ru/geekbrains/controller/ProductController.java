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
import ru.geekbrains.service.ProductDTO;
import ru.geekbrains.service.ProductService;

import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listPage(Model model) {
        logger.info("Страница списка запрошена");

        model.addAttribute("products", productService.findAll());
        return "products";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) {
        logger.info("Страница редактирования id {} запрошена", id);

        model.addAttribute("product", productService.findById(id));
        model.addAttribute("title", "Edit Product");

        return "product_form";
    }

    @PostMapping("/update")
    public String update(@Valid ProductDTO productDTO, BindingResult result) {
        logger.info("Запрос обновления продукта");

        if (result.hasErrors()) {
            return "product_form";
        }

        if (productDTO.getId() != -1) {
            logger.info("Обновлен продукт с  id {}", productDTO.getId());
            productService.update(productDTO);

        } else {
            logger.info("Создан новый продукт DTO:" + productDTO);
            productService.insert(productDTO);
        }
        return "redirect:/products";
    }

//        if (!user.getPassword().equals(user.getMatchingPassword())) {
//            result.rejectValue("password", "", "Password not matching");
//            return "user_form";
//        }

    @GetMapping("/new")
    public String create(Model model) {
        ProductDTO productDTO = new ProductDTO("", "", 100.3);
        productDTO.setId(-1L);

        model.addAttribute("product", productDTO);
        model.addAttribute("title", "Создание нового товара");
        logger.info("Создание нового товара");

        return "product_form";
    }

    @DeleteMapping("/{id}")
    public String remove(Model model, @PathVariable("id") Long id) {
        ProductDTO productDTO = productService.findById(id);
        model.addAttribute("product", productDTO);
        productService.delete(id);
        logger.info("Продукт " + productDTO.getName() + " удален");

        return "delete";
    }
}

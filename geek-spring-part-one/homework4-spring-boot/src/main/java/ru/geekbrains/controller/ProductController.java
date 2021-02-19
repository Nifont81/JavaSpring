package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.service.ProductDTO;
import ru.geekbrains.service.ProductService;

import javax.validation.Valid;
import java.util.Optional;

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
    public String listPage(Model model, @RequestParam Optional<String> nameFilter) {
        logger.info("Страница списка запрошена");

        if (nameFilter.isPresent() && !nameFilter.get().isBlank()) {
            logger.info("Фильтрация по имени запрошена");
            model.addAttribute("products", productService.findProductByNameLike(nameFilter.get()));
        } else {
            model.addAttribute("products", productService.findAll());
        }
        return "products";
    }

    @GetMapping("/priceFilter")
    public String listPage(Model model, @RequestParam Optional<Integer> minPrice, @RequestParam Optional<Integer> maxPrice) {
        logger.info("Фильтрация по диапазону цен запрошена");

        if (minPrice.isPresent() && maxPrice.isPresent()) {
            model.addAttribute("products",
                    productService.findProductByPriceIn(minPrice.get(), maxPrice.get()));
        } else {
            model.addAttribute("products", productService.findAll());
        }
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

        logger.info("Обновлен продукт DTO:" + productDTO);
        productService.save(productDTO);

        return "redirect:/products";
    }

//        if (!user.getPassword().equals(user.getMatchingPassword())) {
//            result.rejectValue("password", "", "Password not matching");
//            return "user_form";
//        }

    @GetMapping("/new")
    public String create(Model model) {
        ProductDTO productDTO = new ProductDTO("", "", 10);
        productDTO.setId(-1L);

        model.addAttribute("product", productDTO);
        model.addAttribute("title", "Создание нового товара");
        logger.info("Создание нового товара");

        return "product_form";
    }

    @DeleteMapping("/{id}")
    public String remove(Model model, @PathVariable("id") Long id) {
        ProductDTO productDTO = productService.findById(id)
                .orElseThrow(NotFoundExeption::new);
        model.addAttribute("product", productDTO);
        productService.delete(id);
        logger.info("Продукт " + productDTO.getName() + " удален");

        return "delete";
    }
}

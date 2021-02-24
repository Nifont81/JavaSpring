package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.service.ProductDTO;
import ru.geekbrains.service.ProductService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final int SIZE_OF_PAGE = 3;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listPage(Model model,
                           @RequestParam("nameFilter") Optional<String> nameFilter,
                           @RequestParam("minPrice") Optional<Double> minPrice,
                           @RequestParam("maxPrice") Optional<Double> maxPrice,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("sortBy") Optional<String> sortBy) {

        logger.info("Страница списка запрошена");

        //session.setAttribute();

        Page<ProductDTO> productDTOPage = productService.findWithFilter(
                nameFilter.orElse(null),
                minPrice.orElse(null),
                maxPrice.orElse(null),
                page.orElse(1) - 1,
                SIZE_OF_PAGE,
                sortBy.filter(s -> !s.isBlank()).orElse("name")
        );
        // нумерация страниц page начинается с 0: page.orElse(1) - 1

        model.addAttribute("products", productDTOPage);

        return "products";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) {
        logger.info("Страница редактирования id {} запрошена", id);

        model.addAttribute("product", productService.findById(id)
        .orElseThrow(NotFoundException::new));

        model.addAttribute("title", "Edit Product");

        return "product_form";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("product") ProductDTO productDTO, BindingResult result) {
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
                .orElseThrow(NotFoundException::new);
        model.addAttribute("product", productDTO);
        productService.delete(id);
        logger.info("Продукт " + productDTO.getName() + " удален");

        return "delete";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView mav = new ModelAndView("not_found");
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }
}

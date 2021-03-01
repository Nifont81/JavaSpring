/*
http://localhost:8080/spring-mvc-app/swagger-ui/index.html

http://localhost:8080/spring-mvc-app/v3/api-docs
 */

package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.persist.TrashRepository;

import java.util.List;

@RestController
@RequestMapping("api/v1/trash")
public class TrashResource {

    private TrashRepository trashRepository;

    @Autowired
    public TrashResource(TrashRepository trashRepository) {
        this.trashRepository = trashRepository;
        trashRepository.addProduct(1, 2);
        trashRepository.addProduct(1, 5);
        trashRepository.addProduct(1, 25);
        trashRepository.addProduct(2, 2);
        trashRepository.addProduct(2, 4);
        trashRepository.addProduct(3, 15);
    }

    @PutMapping
    public void addProduct(@RequestParam("userId") Long userId,
                           @RequestParam("productId") Long productId) {

        trashRepository.addProduct(userId, productId);
    }


    /**
     * Выводит список id товаров, у данного покупателя
     * @param id покупателя
     * @return
     */
    @GetMapping(path = "/products/{id}")
    public List<Long> findProductsIdByUserId(@PathVariable("id") Long id) {
        return trashRepository.getProductsFromUser(id);
    }

    /**
     * Выводит список id покупателей, купивших данный товар
     * @param id товара
     * @return
     */
    @GetMapping(path = "/users/{id}")
    public List<Long> findUsersByProductId(@PathVariable("id") Long id) {
        return trashRepository.getUsersFromProduct(id);
    }

    /**
     * Очищает корзину у данного пользователя
     * @param userId
     */
    @DeleteMapping("/{id}")
    public void clearFromUser(@PathVariable("id") Long userId) {
        trashRepository.clearFromUserId(userId);
    }
}

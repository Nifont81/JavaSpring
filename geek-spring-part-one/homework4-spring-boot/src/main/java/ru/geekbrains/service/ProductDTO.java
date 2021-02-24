package ru.geekbrains.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.geekbrains.persist.Product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// Data Transfer Object

public class ProductDTO {

    //@JsonIgnore
    private Long id;

    @Size(min = 3, max = 100)
    private String name;

    private String description;

    @NotNull
    @Min(10)
    private double price;
//    private BigDecimal price;

    public ProductDTO() {
    }

    public ProductDTO(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public BigDecimal getPrice() {
//        return price;
//    }
    public double getPrice() {
        return price;
    }

//    public void setPrice(BigDecimal price) {
//        this.price = price;
//    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}

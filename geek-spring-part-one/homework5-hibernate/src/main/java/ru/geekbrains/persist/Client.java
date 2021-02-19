package ru.geekbrains.persist;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clients")
@NamedQueries({
        @NamedQuery(name = "findByName", query = "from Client c where c.name = :name"),
        @NamedQuery(name = "allClients", query = "from Client")
})
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany (cascade = CascadeType.PERSIST) // mappedBy = clients, усли не связываем @JoinTable
    @JoinTable (
            name = "clients_products",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @Column (length = 128, unique = true, nullable = false)
    private String name;

    public Client() {
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Client(String name) {
        this.name = name;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Client {" +
                "id=" + id +
                ", name='" + name + "}";
    }
}

package bg.softuni._18_EX_JSON_Processing.productshop.entities.products;

import bg.softuni._18_EX_JSON_Processing.productshop.entities.categories.Category;
import bg.softuni._18_EX_JSON_Processing.productshop.entities.users.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity //(name = "products")
@Table(name = "products")
public class Product {
    //•	Products have an id,
    // name (at least 3 characters),
    // price,
    // buyerId (optional)
    // and sellerId as IDs of users.

    //•	Products should have many categories.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    private BigDecimal price;

    @ManyToOne
    private User seller;

    @ManyToOne
    private User buyer;

    @ManyToMany
    private Set<Category> categories;

    public Product() {
        this.categories = new HashSet<>();
    }

    public Product(String name, BigDecimal price) {
        this();

        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}

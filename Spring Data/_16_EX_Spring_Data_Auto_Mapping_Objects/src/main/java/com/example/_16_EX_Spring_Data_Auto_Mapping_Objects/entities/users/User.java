package com.example._16_EX_Spring_Data_Auto_Mapping_Objects.entities.users;

import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.entities.Game;
import com.example._16_EX_Spring_Data_Auto_Mapping_Objects.entities.Order;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
public class User {
    //email,
    // password,
    // full name,
    // list of games
    // whether he/she is an administrator or not.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    private boolean admin;
    @ManyToMany
    //Много потребители, много игри. Много игри, много потребители.
    private Set<Game> games;

    @OneToMany(targetEntity = Order.class, mappedBy = "buyer")
    private Set<Order> orders;

    public User() {
        this.games = new HashSet<>();
        this.orders = new HashSet<>();
    }

    public User(String email, String password, String fullName) {
        this(); //Викаме празния конструктор,
        //който реално не е празен.
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}

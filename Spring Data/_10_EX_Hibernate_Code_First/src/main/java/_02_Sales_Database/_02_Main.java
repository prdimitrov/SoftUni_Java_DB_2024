package _02_Sales_Database;

import _02_Sales_Database.entities.Customer;
import _02_Sales_Database.entities.Product;
import _02_Sales_Database.entities.Sale;
import _02_Sales_Database.entities.StoreLocation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;

public class _02_Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CodeFirst");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();

        Product product = new Product("asd",
                123,
                BigDecimal.TEN);
        Customer customer = new Customer("customerFirst",
                "firstEmail",
                "FirstCardNum1234");

        StoreLocation location = new StoreLocation("firstLocation");
        Sale sale = new Sale(product, customer, location);

        em.persist(product);
        em.persist(customer);
        em.persist(location);
        em.persist(sale);

        em.getTransaction().commit();
        em.close();
    }
}
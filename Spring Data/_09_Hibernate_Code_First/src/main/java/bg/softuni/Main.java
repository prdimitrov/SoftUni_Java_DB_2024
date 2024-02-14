package bg.softuni;

import entities.Bike;
import entities.Car;
import entities.Plane;
import entities.Vehicle;
import hasEntities.Article;
import hasEntities.PlateNumber;
import hasEntities.Truck;
import hasEntities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("relations");
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
//
//        Vehicle car = new Car("Ford Mondeo 2.2 TDCi", "Diesel", 5);
//        Vehicle bike = new Bike();
//        Vehicle plane = new Plane("Airbus", "Petrol", 250);
//
//        em.persist(car);
//        em.persist(bike);
//        em.persist(plane);
//
//        PlateNumber number = new PlateNumber("123");
//        Truck truck1 = new Truck(number);
//        Truck truck2 = new Truck(number);
//
//        em.persist(number);
//        em.persist(truck1);
//        em.persist(truck2);
////        Car fromDB = em.find(Car.class, 1L);
////        System.out.println(fromDB.getSeats() + " " + fromDB.getModel());

        Article article = new Article("alabala");
        User author = new User("Toshko");

        author.addArticle(article);
        article.setAuthor(author);

        em.persist(author);

        em.getTransaction().commit();
        em.close();
    }
}
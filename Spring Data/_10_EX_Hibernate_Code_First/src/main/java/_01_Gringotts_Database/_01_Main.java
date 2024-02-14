package _01_Gringotts_Database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class _01_Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CodeFirst");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();


        em.getTransaction().commit();
        em.close();
    }
}
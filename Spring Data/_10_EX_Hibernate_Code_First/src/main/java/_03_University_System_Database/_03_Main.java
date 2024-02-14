package _03_University_System_Database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class _03_Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CodeFirst");
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();






        em.getTransaction().commit();
        em.close();
    }
}

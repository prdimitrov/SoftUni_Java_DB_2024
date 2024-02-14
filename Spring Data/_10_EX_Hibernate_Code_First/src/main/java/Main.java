import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ExOne");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();


        em.getTransaction().commit();
        em.close();
    }
}
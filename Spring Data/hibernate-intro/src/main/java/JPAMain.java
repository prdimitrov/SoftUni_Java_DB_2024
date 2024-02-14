import entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAMain {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("school-db");
        /*Тук трябва да се обърне внимание, че school-db всъщност не е името на базата данни, а
        това е името на persistence-unitа в persistence.xml
        */

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Student student = new Student("Teo");
        entityManager.persist(student);

        Student found = entityManager.find(Student.class, 1);
        System.out.println(found.getId() + " " + found.getName());

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

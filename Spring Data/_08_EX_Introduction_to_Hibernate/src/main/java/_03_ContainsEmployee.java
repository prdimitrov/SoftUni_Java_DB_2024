import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.Scanner;

public class _03_ContainsEmployee {
    public static void main(String[] args) {

        /*
        Use the soft_uni database.
        Write a program that checks
        if a given employee's name is contained in the database.
         */

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("PU_Name");
        EntityManager em = entityManagerFactory.createEntityManager();

        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        String firstName = input.split("\\s+")[0];
        String lastName = input.split("\\s+")[1];
        sc.close();

        em.getTransaction().begin();

        try {
            Employee employee = em.createQuery("SELECT e FROM Employee e " +
                            "WHERE firstName = :firstName AND lastName = :lastName", Employee.class)
                    .setParameter("firstName", firstName)
                    .setParameter("lastName", lastName).getSingleResult();
            if (employee != null) {
                System.out.println("Yes");
            }
        } catch (NoResultException e) {
            System.out.println("No");
        }
        em.getTransaction().commit();

        entityManagerFactory.close();
        em.close();
    }
}

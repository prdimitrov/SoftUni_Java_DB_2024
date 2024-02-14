import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.Scanner;

public class _06_Adding_a_New_Address_and_Updating_the_Employee {
    public static void main(String[] args) {
        /*
        Create a new address with the text "Vitoshka 15".
        Set that address to an employee with a last name, given as input.
         */
        Scanner sc = new Scanner(System.in);

        String inputLastName = sc.nextLine();
        sc.close();

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("PU_Name");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();

        try {
            Employee employee = em.createQuery("SELECT e FROM Employee e " +
                    "WHERE e.lastName = :last_name", Employee.class)
                    .setParameter("last_name", inputLastName)
                    .getSingleResult();

            Address address = new Address();
            address.setText("Vitoshka 15");
            em.persist(address);

            employee.setAddress(address);
            em.persist(employee);
        } catch (NoResultException e) {
            System.out.println("Employee with last name " + inputLastName + " not found.");
        }

        em.getTransaction().commit();

        entityManagerFactory.close();
        em.close();

    }
}

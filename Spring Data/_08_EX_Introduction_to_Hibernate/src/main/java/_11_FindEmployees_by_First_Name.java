import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class _11_FindEmployees_by_First_Name {
    /*
    Write a program that finds all employees, whose first name starts with a pattern given as input from the console.
    Print their first and last names, their job title and salary in the format given in the example below.
    Hint: The expected results of the next exercises are with an update of salaries in ex 10.
Example
Input	Output
SA	    Sariya Harnpadoungsataya - Marketing Specialist - ($16128.00)
        Sandra Reategui Alayo - Production Technician - ($9500.00)
        Sairaj Uddin - Scheduling Assistant - ($16000.00)
        Samantha Smith - Production Technician - ($14000.00)
        Sameer Tejani - Production Technician - ($11000.00)
        Sandeep Kaliyath - Production Technician - ($15000.00)

     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String inputPattern = sc.nextLine();
        sc.close();

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("PU_Name");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();

        em.createQuery("SELECT e FROM Employee e " +
                "WHERE e.firstName LIKE :name", Employee.class)
                        .setParameter("name", inputPattern + "%")
                                .getResultList()
                                        .forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n",
                                                e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));
        //В ЛАБ'А никъде не пише как трябва да е подреден резултата.
        //ORDER BY: e.firstName DESC, e.salary или какво?!?!
        //TODO: ДА СЕ ДООПРАВИ!!!!!
        em.getTransaction().commit();

        entityManagerFactory.close();
        em.close();
    }
}

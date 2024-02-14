import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class _08_GetEmployees_with_Project {
    public static void main(String[] args) {
//        Get an employee by his/her id.
//        Print only
//        his/her first name, last name, job title and projects (only their names).
//        The projects should be ordered by name (ascending).
//        The output should be printed in the format given in the example.
/*
Input	Output
147	    Linda Randall - Production Technician
            HL Touring Handlebars
            ML Road Rear Wheel
            Patch kit
            Touring-1000
83	    John Evans - Production Technician
            Half-Finger Gloves
            LL Mountain Handlebars
            Racing Socks
            Women's Tights
 */
        Scanner sc = new Scanner(System.in);
        int inputId = Integer.parseInt(sc.nextLine());

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("PU_Name");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();

        Employee employee = em.createQuery("SELECT e FROM Employee e " +
                        "JOIN e.projects p " +
                "WHERE e.id = :employee_id " +
                        "ORDER BY p.name ASC", Employee.class)
                .setParameter("employee_id", inputId).getSingleResult();

        System.out.printf("%s %s - %s%n", employee.getFirstName(), employee.getLastName(), employee.getJobTitle());
        for (Project project : employee.getProjects()) {
            System.out.println("\t" + project.getName());
        }

        em.getTransaction().commit();

        entityManagerFactory.close();
        em.close();

    }
}

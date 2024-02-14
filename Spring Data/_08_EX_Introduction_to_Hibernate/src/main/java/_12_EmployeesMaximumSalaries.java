import entities.Department;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class _12_EmployeesMaximumSalaries {

    /*
    Write a program that finds the max salary for each department.
    Filter the departments,
    which max salaries are not in the range between 30000 and 70000.
     Example
     Output
     Engineering 71120.00
     Sales 72100.00
     Marketing 16128.00
     Production 84100.00
     …
     */

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("PU_Name");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();

        List<Department> departmentList = em.createQuery("SELECT d FROM Department d", Department.class)
                        .getResultList();
        for (Department department : departmentList) {
            BigDecimal maxSalary = em.createQuery("SELECT MAX(e.salary) FROM Employee e " +
                    "WHERE e.salary NOT BETWEEN 30000 AND 70000 " +
                    "AND e.department.name = :department " +
                    "ORDER BY e.department.id DESC", BigDecimal.class)
                    .setParameter("department", department.getName())
                    .getSingleResult();
            if (maxSalary != null) {
                System.out.printf("%s %.2f%n", department.getName(), maxSalary);
                //По-добре да е false! :D <property name="hibernate.show_sql" value="false"/>
            }
        }
        /*
        Result:
        Engineering 71120,00
        Tool Design 28000,00
        Sales 72100,00
        Marketing 16128,00
        Purchasing 18300,00
        Research and Development 100000,00
        Production 84100,00
        Production Control 24500,00
        Human Resources 27100,00
        Finance 26400,00
        Document Control 17800,00
        Quality Assurance 28800,00
        Facilities and Maintenance 24000,00
        Shipping and Receiving 19200,00
        Executive 125500,00
         */
        em.getTransaction().commit();

        entityManagerFactory.close();
        em.close();
    }
}

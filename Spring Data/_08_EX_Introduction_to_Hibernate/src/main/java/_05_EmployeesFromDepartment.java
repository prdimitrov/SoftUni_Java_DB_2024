import entities.Department;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class _05_EmployeesFromDepartment {
    public static void main(String[] args) {
        /*
        Extract all employees from the Research and Development department.
        Order them by salary (in ascending order),
        then by id (in ascending order).
        Print only their first name, last name, department name and salary.
         */
        //department_id = 6 - Research and Development
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("PU_Name");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();


        Department department = em.createQuery("SELECT d FROM Department d WHERE name = :name", Department.class)
                .setParameter("name", "Research and Development")
                .getSingleResult();

        List<Employee> employeeList = em.createQuery("SELECT e FROM Employee e " +
                "WHERE e.department = :department " +
                        "ORDER BY e.salary ASC, e.id ASC", Employee.class)
                .setParameter("department", department).getResultList();

        for (Employee employee : employeeList) {
//            Print only their first name, last name, department name and salary.
            BigDecimal salary = employee.getSalary();
            System.out.printf("%s %s %s - $%.2f%n", employee.getFirstName(), employee.getLastName(),
                    employee.getDepartment().getName(), salary.doubleValue());
        }

        /*
        Result:
        Diane Margheim Research and Development - $40900,00
        Gigi Matthew Research and Development - $40900,00
        Michael Raheem Research and Development - $42500,00
        Svetlin Nakov Research and Development - $48000,00
        Martin Kulov Research and Development - $48000,00
        George Denchev Research and Development - $48000,00
        Dylan Miller Research and Development - $50500,00
         */

        em.getTransaction().commit();

        entityManagerFactory.close();
        em.close();
    }
}

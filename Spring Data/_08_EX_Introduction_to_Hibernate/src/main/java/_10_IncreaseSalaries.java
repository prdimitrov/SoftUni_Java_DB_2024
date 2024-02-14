import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class _10_IncreaseSalaries {
    /*
    Write a program that increases the salaries of all employees,
    who are in the Engineering, Tool Design, Marketing, or Information Services departments
    by 12%.
    Then print the first name, the last name and the salary for the employees,
    whose salary was increased.
     */
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("PU_Name");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        List<Employee> employeeList = em.createQuery("SELECT e FROM Employee e " +
                "WHERE e.department.name " +
                "IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class)
                        .getResultList();
        for (Employee employee : employeeList) {
            employee.setSalary(employee.getSalary().multiply(BigDecimal.valueOf(1.12)));
            System.out.println(employee.getFirstName() + " " + employee.getLastName() + " ($" + employee.getSalary() + ")");
        }
        /*
        Result:
        Roberto Tamburello ($48496.000000)
        Gail Erickson ($36624.000000)
        Jossef Goldberg ($36624.000000)
        Terri Duffy ($71120.000000)
        Michael Sullivan ($40432.000000)
        Sharon Salavaria ($36624.000000)
        Rob Walters ($33376.000000)
        Thierry D'Hers ($28000.000000)
        Ovidiu Cracium ($32256.000000)
        Janice Galvin ($28000.000000)
        Kevin Brown ($15120.000000)
        Sariya Harnpadoungsataya ($16128.000000)
        Mary Gibson ($16128.000000)
        Jill Williams ($16128.000000)
        Terry Eminhizer ($16128.000000)
        Wanida Benshoof ($15120.000000)
        John Wood ($16128.000000)
        Mary Dempsey ($15120.000000)
        Ashvini Sharma ($36400.000000)
        Jean Trenary ($56560.000000)
        Janaina Bueno ($30688.000000)
        Dan Bacon ($30688.000000)
        Francois Ajenstat ($43120.000000)
        Dan Wilson ($43120.000000)
        Ramesh Meyyappan ($30688.000000)
        Stephanie Conroy ($44464.000000)
        Karen Berg ($30688.000000)
        Peter Connelly ($36400.000000)
         */

        em.getTransaction().commit();
        entityManagerFactory.close();
        em.close();
    }
}

import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class _04_EmployeesWith_a_SalaryOver_50000 {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("PU_Name");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();

        List<Employee> employeeList = em.createQuery("SELECT e FROM Employee e " +
                "WHERE salary > :salary", Employee.class).setParameter("salary", new BigDecimal(50000))
                        .getResultList();

        for (Employee employee : employeeList) {
            System.out.println(employee.getFirstName());
        }
        /*
        Result:
        Terri
        Jean
        Ken
        Laura
        James
        Dylan
        Brian
         */
        em.getTransaction().commit();

        entityManagerFactory.close();
        em.close();
    }
}

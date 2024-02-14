import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class _07_Addresses_with_EmployeeCount {
    public static void main(String[] args) {
        /*
        Find all addresses, ordered by the number of employees who live there (descending).
        Take only the first 10 addresses and print their address text, town name and employee count.
         */
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("PU_Name");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();

        List<Address> addressList = em.createQuery("SELECT a FROM Address a " +
                "ORDER BY a.employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList();

        for (Address addr : addressList) {
            System.out.printf("%s, %s - %d employees%n", addr.getText(), addr.getTown().getName(), addr.getEmployees().size());
        //Дава Exception - Exception in thread "main" java.lang.NullPointerException: Cannot invoke "entities.Town.getName()" because the return value of "entities.Address.getTown()" is null
        }

        em.getTransaction().commit();

        entityManagerFactory.close();
        em.close();
    }
}

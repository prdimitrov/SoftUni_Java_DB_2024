import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class _13_RemoveTowns {
    /*
    Write a program that deletes a town,
    which name is given as an input.
    The program should delete all addresses that are in the given town.
    Print on the console the number of addresses that were deleted.
    Check the example for the output format.
    Example
    Input	    Output
    Sofia	    1 address in Sofia deleted
    Seattle	    44 addresses in Seattle deleted

     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String inputTownName = sc.nextLine();
        sc.close();

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("PU_Name");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();

        Town town = em.createQuery(
                        "SELECT t FROM Town t" +
                                " WHERE t.name = :townName", Town.class)
                .setParameter("townName", inputTownName)
                .getSingleResult();

        List<Address> addresses = em.createQuery(
                        "SELECT a FROM Address a" +
                                " WHERE a.town.name = :townName", Address.class)
                .setParameter("townName", inputTownName)
                .getResultList();

        String result = String.format("%d address%s in %s deleted%n",
                addresses.size(),
                (addresses.size() != 1) ? "es" : "",
                town.getName());

        for (Address a : addresses) {
            for (Employee employee : a.getEmployees()) {
                employee.setAddress(null);
            }
            a.setTown(null);
            em.remove(a);
        }
        em.remove(town);

        em.getTransaction().commit();
        System.out.println(result);
        entityManagerFactory.close();
        em.close();
    }
}

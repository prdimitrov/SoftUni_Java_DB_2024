import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class _02_ChangeCasing {
    public static void main(String[] args) {

        /*
        Use the soft_uni database. Persist all towns from the database.
        Detach those whose name length is more than 5 symbols.
        Then transform the names of all attached towns to uppercase
        and save them to the database.
         */

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("PU_Name");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();

        List<Town> townsList = em.createQuery("SELECT t FROM Town t", Town.class)
                        .getResultList();

        for (Town town : townsList) {
            if (town.getName().length() > 5) {
                em.detach(town);
            } else {
                town.setName(town.getName().toUpperCase());
                em.persist(town);
                System.out.println(town.getName());
            }
        }
        /*
        Result must be:
        INDEX
        KENT
        SOFIA
         */
        em.getTransaction().commit();

        entityManagerFactory.close();
        em.close();
    }
}

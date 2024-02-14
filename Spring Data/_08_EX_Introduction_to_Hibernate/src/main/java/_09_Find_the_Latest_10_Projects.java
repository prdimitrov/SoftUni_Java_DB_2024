import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class _09_Find_the_Latest_10_Projects {
   /*
   Write a program that prints the last 10 started projects.
   Print their name, description, start and end date and sort them by name lexicographically.
   For the output, check the format from the example.
    */
    /*
    Output
Project name: All-Purpose Bike Stand
 	Project Description: Research, design and development of …
 	Project Start Date:2005-09-01 00:00:00.0
 	Project End Date: null
Project name: Bike Wash
 	Project Description: Research, design and development of …
 	Project Start Date:2005-08-01 00:00:00.0
 	Project End Date: null
Project name: HL Touring Frame
 	Project Description: Research, design and development of …
 	Project Start Date:2005-05-16 16:34:00.0
 	Project End Date: null
…
// "\t -> Tab"
     */
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("PU_Name");
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();

        List<Project> projectList = em.createQuery("SELECT p FROM Project p " +
                "ORDER BY p.startDate", Project.class)
                .setMaxResults(10)
                .getResultList();

        for (Project project : projectList) {
            StringBuilder sb = new StringBuilder();
            sb.append("Project name: ").append(project.getName())
                    .append(System.lineSeparator())
                    .append("\t").append("Project description: ").append(project.getDescription())
                    .append(System.lineSeparator())
                    .append("\t").append("Project Start Date:").append(getCurrentTimeStamp(project.getStartDate()))
                    .append(System.lineSeparator())
                    .append("\t").append("Project End Date: ").append(getCurrentTimeStamp(project.getEndDate()));
            System.out.println(sb);
            //В ЛАБ'а след Project Start Date:НЯМА РАЗСТОЯНИЕ!!!!! МОЖЕ ДА ДАДЕ ГРЕШКА В JUDGE!
            //АКО ДАДЕ ГРЕШКА СЛОЖИ РАЗСТОЯНИЕ
            //.append("\t").append("Project Start Date: ").append(getCurrentTimeStamp(project.getStartDate()))
        }

        em.getTransaction().commit();

        entityManagerFactory.close();
        em.close();
    }
    public static String getCurrentTimeStamp(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
    }
}

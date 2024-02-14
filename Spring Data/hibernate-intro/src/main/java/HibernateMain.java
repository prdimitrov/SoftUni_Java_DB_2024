import entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateMain {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        //Begin of the transaction
        session.beginTransaction();
//
//        Student example = new Student();
//        example.setName("Tosho");
//        session.persist(example);
//        //Взимане на студент от базата
//        Student fromDb = session.get(Student.class, 1);
//        System.out.println(fromDb.getId() + " " + fromDb.getName());

        /*Тук, ако кажем FROM student, вместо FROM Student ще изгърми, защото
        работим с HQL (Hibernate Query Language).
        Работим с нещата, които се намират в Java домейна, а НЕ с тези които са в базата данни.
        */
        List<Student> students = session.createQuery("FROM Student AS s WHERE s.name = 'Tosho'", Student.class).list();


        for (Student student : students) {
            System.out.println(student.getId() + " " + student.getName());
        }
        //End of the transaction
        session.getTransaction().commit();
        session.close();
    }
}

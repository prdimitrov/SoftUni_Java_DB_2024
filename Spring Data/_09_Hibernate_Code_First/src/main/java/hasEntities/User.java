package hasEntities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "author", targetEntity = Article.class,
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //Банкин каза, че когато FetchType е Lazy нещата не се взимат от базата, а
    //когато е EAGER се взимат от базата
    //fetch отговаря дали информацията за тази колекция, за тази връзка
    //да се взима автоматично всеки път, когато requestнем User
    //cascade дали тази операция да се изпълни върху всички свързани обекти
        /*
    хубаво е винаги списъците да бъдат инициализирани, без значение дали имаме
    стойности в тях или нямаме.
        */
    private List<Article> articles;

    public User() {
        //^^Всеки път когато направя нов User^^, да имам празен списък с articles.
        this.articles = new ArrayList<>();
    }

    public User(String name) {
        //Викаме празния конструктор защото никога не сме викали public User(){}.
        //Иначе ще хвърли NullPointerException
        this();

        this.name = name;
    }

    public void addArticle(Article article) {
        this.articles.add(article);
    }

}

package hasEntities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String text;
    @ManyToOne()
    //Може да се повтаря, т.е. да не е unique
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    //Банкин каза:
    //Една статия може да има много категории,
    //една категория може да принадлежи на много статии
//    @ManyToMany
    //Това по себе си ще създаде таблица articles_categories!!
   @ManyToMany(fetch = FetchType.EAGER)

   //Принципно може да минем и без това :D
//   @JoinTable(
//           name = "articles_categories",
//           joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
//           inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "id")
//   )
    private Set<Category> categories;

    public Article() {
        this.categories = new HashSet<>();
    }

    public Article(String text) {
        this(); //Тук отново извикваме базовия конструктор ^^
        //за да можем да инициализираме

        this.text = text;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}

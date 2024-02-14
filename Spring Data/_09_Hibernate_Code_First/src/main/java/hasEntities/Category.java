package hasEntities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "categoies")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToMany(targetEntity = Article.class, mappedBy = "categories")
    private Set<Article> articles;

    @ManyToOne //Банкин каза: Ето така става SelfReference връзка
    //в базата ще получим parent_id
    private Category parent;


    public Category() {

    }

    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

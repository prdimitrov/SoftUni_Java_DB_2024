package bg.softuni._18_EX_JSON_Processing.productshop.repositories;

import bg.softuni._18_EX_JSON_Processing.productshop.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u" +
            " JOIN u.sellingItems p" +
            " WHERE p.buyer IS NOT NULL")
    List<User> findAllWithSoldProducts();

    @Query("SELECT u FROM User u" +
            " JOIN u.sellingItems p" +
            " WHERE p.buyer IS NOT NULL" +
            " ORDER BY size(u.sellingItems) DESC," +
            " u.lastName ASC")
    List<User> findAllWithSoldProductsOrderByCount();
}

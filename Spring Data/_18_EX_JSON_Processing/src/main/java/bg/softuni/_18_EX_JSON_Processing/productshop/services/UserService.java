package bg.softuni._18_EX_JSON_Processing.productshop.services;

import bg.softuni._18_EX_JSON_Processing.productshop.entities.users.User;
import bg.softuni._18_EX_JSON_Processing.productshop.entities.users.UserWithSoldProductsDTO;

import java.util.List;

public interface UserService {
List<UserWithSoldProductsDTO> getUsersWithSoldProducts();

    List<User> getUsersWithSoldProductsOrderByCount();
}

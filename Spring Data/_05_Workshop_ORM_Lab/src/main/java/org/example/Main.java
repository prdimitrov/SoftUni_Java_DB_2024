package org.example;

import entities.User;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static orm.MyConnector.closeConnection;
import static orm.MyConnector.getConnection;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {

        Connection connection = getConnection("rootuser", "12341234", "custom-orm");

        EntityManager<User> userEntityManager = new EntityManager<>(connection);

//         CREATE_TABLE(userEntityManager);

         INSERT_USER(userEntityManager);

         INSERT_UPDATE(userEntityManager);

         UPDATE_USER(userEntityManager);

         DELETE_USER(userEntityManager);

         ADD_NEW_FIELD(userEntityManager);

         FIND_FIRST_USER(userEntityManager); // Find first user

         FIND_FIRST_USER_WHERE(userEntityManager); // Find first user where age = 25

         FIND_USER_WHERE(userEntityManager); // Find user with id = 1

         FIND_USER_WHERE_2(userEntityManager);

         FIND_USER(userEntityManager);

        closeConnection();
    }

    private static void FIND_USER(EntityManager<User> userEntityManager) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        userEntityManager.find(User.class).forEach(System.out::println);
    }

    private static void FIND_USER_WHERE_2(EntityManager<User> userEntityManager) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        userEntityManager.find(User.class, "id < 5")
                .stream()
                .forEach(System.out::println); // Print users with id < 5
    }

    private static void UPDATE_USER(EntityManager<User> userEntityManager) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<User> users = userEntityManager.find(User.class, "id = 10");
        if (!users.isEmpty()) {
            User userToUpdate = users.get(0);
            userToUpdate.setUsername("Updated name"); // Set the new username
            try {
                userEntityManager.update(userToUpdate, "new_username");
                System.out.println("Update successful");
            } catch (Exception e) {
                System.out.println("Failed to update user: " + e.getMessage());
            }
        } else {
            System.out.println("No user found with ID 10");
        }
    }

    private static void FIND_USER_WHERE(EntityManager<User> userEntityManager) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        System.out.println(userEntityManager.find(User.class, "id = 8").get(0));
    }

    private static void FIND_FIRST_USER_WHERE(EntityManager<User> userEntityManager) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        System.out.println(userEntityManager.findFirst(User.class, "age = 25"));
    }

    private static void FIND_FIRST_USER(EntityManager<User> userEntityManager) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.println(userEntityManager.findFirst(User.class));
    }

    private static void ADD_NEW_FIELD(EntityManager<User> userEntityManager) throws SQLException {
        // If we add new column to a class, we use this method to add it to the DB
        userEntityManager.alterTable(User.class);
    }

    private static void INSERT_UPDATE(EntityManager<User> userEntityManager) throws IllegalAccessException, SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        List<User> users = userEntityManager.find(User.class, "id = 10");
        if (!users.isEmpty()) {
            User user = users.get(0);
            user.setUsername("No name");
            try {
                userEntityManager.persist(user);
                System.out.println("Update successful");
            } catch (Exception e) {
                System.out.println("Failed to update user: " + e.getMessage());
            }
        } else {
            System.out.println("No user found with ID 10");
        }
    }

    private static void DELETE_USER(EntityManager<User> userEntityManager) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<User> users = userEntityManager.find(User.class, "id = 10");
        if (!users.isEmpty()) {
            User userToDelete = users.get(0);
            try {
                userEntityManager.delete(userToDelete);
                System.out.println("Deletion successful");
            } catch (Exception e) {
                System.out.println("Failed to delete user: " + e.getMessage());
            }
        } else {
            System.out.println("No user found with ID 10");
        }
    }

    private static void INSERT_USER(EntityManager<User> userEntityManager) throws IllegalAccessException, SQLException {
        for (int i = 0; i < 10; i++) {
            User user = new User("User - " + i, 20 + i, LocalDate.of(2022, 5, 1 + i));
            userEntityManager.persist(user);
        }
    }

    private static void CREATE_TABLE(EntityManager<User> userEntityManager) throws SQLException {
        userEntityManager.createTable(User.class);
    }
}
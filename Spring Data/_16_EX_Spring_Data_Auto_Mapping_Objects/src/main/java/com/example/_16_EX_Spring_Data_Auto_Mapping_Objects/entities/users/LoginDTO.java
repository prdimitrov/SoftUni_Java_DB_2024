package com.example._16_EX_Spring_Data_Auto_Mapping_Objects.entities.users;

public class LoginDTO {
    //TODO: Банкин каза Validate email, че трябва да се направи.
    //TODO: Принципно в RegisterDTO имаме някаква валидация.
    private String email;
    private String password;

    public LoginDTO(String[] commandLineParts) {
        this.email = commandLineParts[1];
        this.password = commandLineParts[2];
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

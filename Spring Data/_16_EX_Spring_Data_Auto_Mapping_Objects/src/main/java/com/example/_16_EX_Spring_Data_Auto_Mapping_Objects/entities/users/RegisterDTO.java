package com.example._16_EX_Spring_Data_Auto_Mapping_Objects.entities.users;

public class RegisterDTO {
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    public RegisterDTO(String[] commandParts) {


    }
}

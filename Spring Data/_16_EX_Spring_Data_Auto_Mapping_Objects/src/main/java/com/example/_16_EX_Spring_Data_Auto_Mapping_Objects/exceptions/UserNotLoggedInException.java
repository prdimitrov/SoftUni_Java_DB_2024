package com.example._16_EX_Spring_Data_Auto_Mapping_Objects.exceptions;

public class UserNotLoggedInException extends RuntimeException {


    public UserNotLoggedInException() {
    super("Execute Login command first!");
}
}

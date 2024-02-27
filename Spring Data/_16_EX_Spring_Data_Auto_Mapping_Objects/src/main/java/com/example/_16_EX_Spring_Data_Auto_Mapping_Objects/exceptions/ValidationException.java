package com.example._16_EX_Spring_Data_Auto_Mapping_Objects.exceptions;

public class ValidationException extends RuntimeException {

    public ValidationException(String reason) {
        super(reason);
    }
}

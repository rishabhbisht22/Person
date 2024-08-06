package com.person.exception;

public class PersonNotFound extends RuntimeException{

    public PersonNotFound(String message) {
        super(message);
    }
}

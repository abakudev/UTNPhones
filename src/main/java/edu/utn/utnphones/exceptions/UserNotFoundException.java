package edu.utn.utnphones.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
    }

    public UserNotFoundException(Exception e) {
        super(e);
    }

}

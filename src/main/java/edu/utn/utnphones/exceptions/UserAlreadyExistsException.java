package edu.utn.utnphones.exceptions;

public class UserAlreadyExistsException extends RecordExistsException {

    public UserAlreadyExistsException() {
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public String getMessage() {
        return "User Already Exist!";
    }
}

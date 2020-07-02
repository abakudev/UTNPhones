package edu.utn.utnphones.exceptions;

public class CallAlreadyExistsException extends RecordExistsException {

    public CallAlreadyExistsException() {
    }

    public CallAlreadyExistsException(String message) {
        super(message);
    }

    public String getMessage() {
        return "Call Already Exist!";
    }
}


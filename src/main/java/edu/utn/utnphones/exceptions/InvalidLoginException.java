package edu.utn.utnphones.exceptions;

public class InvalidLoginException extends Throwable {

    public InvalidLoginException(Throwable cause) {
        super(cause);
    }

    public String getMessage() {
        return "dni and/or password is incorrect.";
    }

}

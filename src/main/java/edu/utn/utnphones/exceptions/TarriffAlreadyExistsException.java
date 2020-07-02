package edu.utn.utnphones.exceptions;

public class TarriffAlreadyExistsException extends RecordExistsException {

    public TarriffAlreadyExistsException() {
    }

    public TarriffAlreadyExistsException(String message) {
        super(message);
    }

    public String getMessage() {
        return "Tariff Already Exist!";
    }
}

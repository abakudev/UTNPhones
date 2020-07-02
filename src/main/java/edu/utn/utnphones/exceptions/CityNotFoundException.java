package edu.utn.utnphones.exceptions;

public class CityNotFoundException extends Exception{
    public CityNotFoundException() {
    }

    public CityNotFoundException(Exception e) {
        super(e);
    }
}

package edu.utn.utnphones.controller.advice;


import edu.utn.utnphones.dto.ErrorResponseDto;
import edu.utn.utnphones.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.ParseException;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidLoginException.class)
    public ErrorResponseDto handleLoginException(InvalidLoginException exc) {
        return new ErrorResponseDto(1, exc.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponseDto handleValidationException(ValidationException exc) {
        return new ErrorResponseDto(2, exc.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponseDto handleUserNotFoundException() {
        return new ErrorResponseDto(3, "User not exists");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CityNotFoundException.class)
    public ErrorResponseDto handleCityNotFoundException() {
        return new ErrorResponseDto(4, "City not exists");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PhoneLineNotFoundException.class)
    public ErrorResponseDto handlePhoneLineNotFoundException(PhoneLineNotFoundException ex) {
        return new ErrorResponseDto(5, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParseException.class)
    public ErrorResponseDto handleParseException() {
        return new ErrorResponseDto(6, "Not valid dates");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorResponseDto handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return new ErrorResponseDto(7, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TarriffAlreadyExistsException.class)
    public ErrorResponseDto handleTariffAlreadyExistsException(TarriffAlreadyExistsException ex) {
        return new ErrorResponseDto(8, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CallAlreadyExistsException.class)
    public ErrorResponseDto handleCallAlreadyExistsException(CallAlreadyExistsException ex) {
        return new ErrorResponseDto(8, ex.getMessage());
    }


}

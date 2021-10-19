package com.astontech.rest.controllers.advice;


import com.astontech.rest.exceptions.FieldNotFoundException;
import com.astontech.rest.exceptions.ProductNotFoundException;
import com.astontech.rest.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalControllerAdvice {


    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String productNotFoundHandler(ProductNotFoundException pEx) {
        return pEx.getLocalizedMessage();
    }

    @ExceptionHandler(FieldNotFoundException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String fieldNotFoundHandler(FieldNotFoundException fEx) {
        return fEx.getLocalizedMessage();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String unAuthorizedHandler(UnauthorizedException uEx) {
        return uEx.getLocalizedMessage();
    }

    // General Exception Catch
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String generalExceptionHandler(Exception ex) {
        return "General Server Error";
    }

}

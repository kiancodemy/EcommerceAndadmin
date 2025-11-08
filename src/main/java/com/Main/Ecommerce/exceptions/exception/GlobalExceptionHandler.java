package com.Main.Ecommerce.exceptions.exception;


import com.Main.Ecommerce.auth.dto.response.Response;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Get the first field error message
        String firstMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Invalid input");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(firstMessage, null));
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<Response> handleException(UserAlreadyExist ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response(ex.getMessage(), null));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(ex.getMessage(), null));
    }


}

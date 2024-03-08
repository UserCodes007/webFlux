package com.example.Db.Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        String trace="";
        for(StackTraceElement e: exception.getStackTrace())trace+=e.toString()+"\n";
        logger.trace(trace);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND).build();
    }
}
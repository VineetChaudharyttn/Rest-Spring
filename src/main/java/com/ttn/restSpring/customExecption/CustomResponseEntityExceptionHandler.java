package com.ttn.restSpring.customExecption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExecption(Exception ex, WebRequest webRequest){
        ExceptionCustomResponse exceptionCustomResponse =new ExceptionCustomResponse(new Date(),ex.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionCustomResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundExecption(Exception ex,WebRequest webRequest){
        ExceptionCustomResponse exceptionCustomResponse =new ExceptionCustomResponse(new Date(),ex.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(exceptionCustomResponse,HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorMessageList=ex.getBindingResult().getAllErrors().stream().filter(e->e instanceof FieldError).map((e)->{

            FieldError fieldError = (FieldError) e;
            return   messageSource.getMessage(fieldError, null);
        }).collect(Collectors.toList());

        ExceptionCustomResponse exceptionResponse = new ExceptionCustomResponse(new Date(),errorMessageList.toString(),request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}

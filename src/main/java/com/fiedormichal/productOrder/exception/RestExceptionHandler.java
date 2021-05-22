package com.fiedormichal.productOrder.exception;


import com.fasterxml.jackson.core.JsonParseException;
import com.fiedormichal.productOrder.apierror.ApiError;
import com.networknt.schema.ValidationMessage;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fiedormichal.productOrder.apierror.ApiErrorMsg.*;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers,
                                                        HttpStatus status,
                                                        WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        return buildResponseEntity(getApiError(errors, HttpStatus.BAD_REQUEST, MISMATCH_TYPE.getValue()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> errors;
        errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getObjectName() + " : " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return buildResponseEntity(getApiError(errors, HttpStatus.BAD_REQUEST, VALIDATION_ERRORS.getValue()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        return buildResponseEntity(getApiError(errors, HttpStatus.BAD_REQUEST, MALFORMED_JSON_REQUEST.getValue()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {

        List<String> errors = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(type -> builder.append(type).append(", "));
        errors.add(builder.toString());
        return buildResponseEntity(getApiError(errors, HttpStatus.UNSUPPORTED_MEDIA_TYPE, UNSUPPORTED_MEDIA_TYPE.getValue()));
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(String.format("Could not find the %s method for URL %s",
                ex.getHttpMethod(), ex.getRequestURL()));
        return buildResponseEntity(getApiError(errors, HttpStatus.NOT_FOUND, METHOD_NOT_FOUND.getValue()));
    }

    @ExceptionHandler(JsonParseException.class)
    protected ResponseEntity<Object> handleJsonParseException(JsonParseException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
//        log.info("Occurred " + ex.getClass());
        return buildResponseEntity(getApiError(errors, HttpStatus.BAD_REQUEST, MALFORMED_JSON_REQUEST.getValue()));
    }


    @ExceptionHandler(JsonValidationFailedException.class)
    protected ResponseEntity<Object> handleJsonSchemaValidation(JsonValidationFailedException ex){
        List<String> errors =ex.getValidationMessages().stream()
                .map(ValidationMessage::getMessage)
                .collect(Collectors.toList());
        Map<String, Object> validationFailedDetails = new HashMap<>();
        validationFailedDetails.put("errors", errors);
        validationFailedDetails.put("message", "Json validation failed");
//        log.info("Occurred " + ex.getClass());
        return buildResponseEntity(getApiError(errors, HttpStatus.BAD_REQUEST, VALIDATION_ERROR.getValue()));
    }




    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        return buildResponseEntity(getApiError(errors, HttpStatus.BAD_REQUEST, METHOD_ARGUMENT_MISMATCH.getValue()));
    }

    @ExceptionHandler(IncorrectDateException.class)
    protected ResponseEntity<Object> handleDateTimeParse(IncorrectDateException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        return buildResponseEntity(getApiError(errors, HttpStatus.BAD_REQUEST, INVALID_DATE_FORMAT.getValue()));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    protected ResponseEntity<?> handleOrderNotFound(OrderNotFoundException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        return buildResponseEntity(getApiError(errors, HttpStatus.BAD_REQUEST, ORDER_NOT_FOUND.getValue()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    protected ResponseEntity<?> handleProductNotFound(ProductNotFoundException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        return buildResponseEntity(getApiError(errors, HttpStatus.BAD_REQUEST, PRODUCT_NOT_FOUND.getValue()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleAll(Exception ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getLocalizedMessage());

        return buildResponseEntity(getApiError(errors, HttpStatus.BAD_REQUEST, ERROR_OCCURRED.getValue()));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private ApiError getApiError(List<String> errors, HttpStatus status, String message) {
        return new ApiError(
                LocalDateTime.now(),
                status,
                message,
                errors);
    }

}

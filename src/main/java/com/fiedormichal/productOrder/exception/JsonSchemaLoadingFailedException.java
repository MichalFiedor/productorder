package com.fiedormichal.productOrder.exception;

public class JsonSchemaLoadingFailedException extends RuntimeException {
    public JsonSchemaLoadingFailedException(String message) {
        super(message);
    }
}

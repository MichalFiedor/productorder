package com.fiedormichal.productOrder.apierror;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiErrorMsg {
    MISMATCH_TYPE("Wrong method parameter in URL"),
    VALIDATION_ERRORS("Occurred some validation errors. Check if JSON contains correct values." +
            " Check errors list for details"),
    MALFORMED_JSON_REQUEST("Request body is invalid. Check if fields in JSON is correct"),
    UNSUPPORTED_MEDIA_TYPE("Specified request media type (Content type) is not supported. Check errors list for details"),
    METHOD_NOT_FOUND("Method with this URL not found"),
    PRODUCT_NOT_FOUND("Product not found. Check errors list for details"),
    ORDER_NOT_FOUND("Order not found. Check errors list for details"),
    METHOD_ARGUMENT_MISMATCH("Given method argument is not correct. Check errors list for details"),
    INVALID_DATE_FORMAT("Date is not correct. Check errors list for details"),
    VALIDATION_ERROR("Json validation failed."),
    ERROR_OCCURRED("Some exceptions occurred. Check errors list");

    private String value;
}

package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs{

    OK(200, 200, "SUCCESS"),

    BAD_REQUEST(400, 400, "Bad Request"),

    SERVER_ERROR(500, 500, "Internal Server Error"),
    NULL_POINT(500, 512, "Null point")

    ;

    private final Integer httpStatusCode;

    private final Integer errorCode;

    private final String description;

}

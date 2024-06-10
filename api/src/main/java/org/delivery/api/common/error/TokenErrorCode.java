package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenErrorCode implements ErrorCodeIfs{

    INVALID_TOKEN(400, 2000, "Invalid Token"),
    EXPIRED_TOKEN(400, 2001, "Expired Token"),
    TOKEN_EXCEPTION(400, 2002, "Unknown Error Occured"),
    AUTORIZATION_TOKEN_NOT_FOUND(400, 2003, "Autorization token not found")
    ;

    private final Integer httpStatusCode;

    private final Integer errorCode;

    private final String description;

}

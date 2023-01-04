package com.project.bookmarkboard.dto.response;

import com.project.bookmarkboard.dto.basic.BasicResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse extends BasicResponse {
    private String errorMessage;
    private String errorCode;

    public ErrorResponse(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
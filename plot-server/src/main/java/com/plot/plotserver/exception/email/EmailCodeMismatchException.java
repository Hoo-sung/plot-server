package com.plot.plotserver.exception.email;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailCodeMismatchException extends RuntimeException{
    private String message;

    public EmailCodeMismatchException(String message) {
        super(message);
        this.message = message;
    }
}

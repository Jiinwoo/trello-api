package me.jung.jwt.demo.advice.exception;

import lombok.Getter;
import me.jung.jwt.demo.domain.ErrorResponse;

import java.util.List;

@Getter
public class EmailDuplicationException extends RuntimeException{

    private String email;

    public EmailDuplicationException(String email) {
        this.email = email;
    }

}

package com.back.p13202.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "이미 존재하는 아이디다이")
public class DuplicateUserName extends RuntimeException {
    public DuplicateUserName(String message) {
        super(message);
    }
}

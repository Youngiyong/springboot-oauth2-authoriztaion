package com.example.cms.exception;

import com.example.cms.constants.CustomExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final CustomExceptionCode exceptionCode;
}

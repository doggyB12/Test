package com.project.vaccine.exception;


import lombok.Getter;

import java.util.List;

@Getter
public class DuplicateException extends RuntimeException {

    private final List<ErrorDetail> errors;

    public DuplicateException(List<ErrorDetail> errors) {
        super("Duplicate error");
        this.errors = errors;
    }
}

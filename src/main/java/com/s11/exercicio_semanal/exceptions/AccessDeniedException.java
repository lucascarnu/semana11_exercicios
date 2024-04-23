package com.s11.exercicio_semanal.exceptions;

public class AccessDeniedException extends RuntimeException {
    private final Long id;

    public AccessDeniedException(String message, Long id) {
        super(message);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}



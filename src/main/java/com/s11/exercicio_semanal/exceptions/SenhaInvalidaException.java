package com.s11.exercicio_semanal.exceptions;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
    }

    public SenhaInvalidaException(String message) {
        super(message);
    }
}

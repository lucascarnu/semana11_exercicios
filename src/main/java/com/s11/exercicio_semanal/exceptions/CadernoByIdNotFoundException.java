package com.s11.exercicio_semanal.exceptions;

public class CadernoByIdNotFoundException extends RuntimeException {
    private final Long cadernoId;

    public CadernoByIdNotFoundException(Long id) {
        super("Caderno não encontrado com id" + id);
        this.cadernoId = id;
    }

    public Long getCadernoId() {
        return cadernoId;
    }
}

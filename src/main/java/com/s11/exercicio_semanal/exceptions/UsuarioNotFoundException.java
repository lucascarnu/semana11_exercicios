package com.s11.exercicio_semanal.exceptions;

public class UsuarioNotFoundException extends RuntimeException {
    private final Long usuarioId;

    public UsuarioNotFoundException(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }
}


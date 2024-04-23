package com.s11.exercicio_semanal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handler(NotFoundException e) {
        Erro erro = Erro.builder()
                .codigo("404")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(404).body(erro);
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<?> handler(SenhaInvalidaException e) {
        Erro erro = Erro.builder()
                .codigo("404")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(404).body(erro);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handler(ForbiddenException e) {
        Erro erro = Erro.builder()
                .codigo("403")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(403).body(erro);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handler(AccessDeniedException e) {
        String mensagem = e.getMessage();
        Erro erro = Erro.builder()
                .codigo("403")
                .mensagem(mensagem)
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
    }

    @ExceptionHandler(CadernoComNotasException.class)
    public ResponseEntity<?> handler(CadernoComNotasException e) {
        String mensagem = "Não pode excluir um caderno que contém notas";
        Erro erro = Erro.builder()
                .codigo("409")
                .mensagem(mensagem)
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(CadernoByIdNotFoundException.class)
    public ResponseEntity<?> handler(CadernoByIdNotFoundException e) {
        String mensagem = "Caderno não encontrado com id: " + e.getCadernoId();
        Erro erro = Erro.builder()
                .codigo("404")
                .mensagem(mensagem)
                .build();
        return ResponseEntity.status(404).body(erro);
    }

    @ExceptionHandler(NotaByIdNotFoundException.class)
    public ResponseEntity<?> handler(NotaByIdNotFoundException e) {
        String mensagem = "Nota não encontrada com id: " + e.getNotaId();
        Erro erro = Erro.builder()
                .codigo("404")
                .mensagem(mensagem)
                .build();
        return ResponseEntity.status(404).body(erro);
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<?> handler(UsuarioNotFoundException e) {
        String mensagem = "Usuário não encontrado pelo id: " + e.getUsuarioId();
        Erro erro = Erro.builder()
                .codigo("404")
                .mensagem(mensagem)
                .build();
        return ResponseEntity.status(404).body(erro);
    }

}

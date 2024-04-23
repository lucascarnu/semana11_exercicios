package com.s11.exercicio_semanal.controllers;

import com.s11.exercicio_semanal.entities.CadernoEntity;
import com.s11.exercicio_semanal.entities.NotaEntity;
import com.s11.exercicio_semanal.entities.UsuarioEntity;
import com.s11.exercicio_semanal.exceptions.NotFoundException;
import com.s11.exercicio_semanal.exceptions.UsuarioNotFoundException;
import com.s11.exercicio_semanal.repositories.CadernoRepository;
import com.s11.exercicio_semanal.repositories.UsuarioRepository;
import com.s11.exercicio_semanal.services.AuthService;
import com.s11.exercicio_semanal.services.NotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/notas")
public class NotaController {

    private final NotaService service;
    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;
    private final CadernoRepository cadernoRepository;

    public NotaController(NotaService service, AuthService authService, UsuarioRepository usuarioRepository, CadernoRepository cadernoRepository) {
        this.service = service;
        this.authService = authService;
        this.usuarioRepository = usuarioRepository;
        this.cadernoRepository = cadernoRepository;
    }

    @GetMapping
    public ResponseEntity<List<NotaEntity>> buscarTodos(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        List<NotaEntity> notas = service.buscarTodosPorUsuarioId(userId);
        return ResponseEntity.ok().body(notas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaEntity> buscarPorId(@PathVariable Long id, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        NotaEntity nota = service.buscarPorId(id);
        if (!nota.getCaderno().getUsuario().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(nota);
    }

    @PostMapping
    public ResponseEntity<NotaEntity> criar(@RequestBody NotaEntity nota, Principal principal) {
        Long userId = Long.parseLong(principal.getName());

        CadernoEntity caderno = cadernoRepository.findById(nota.getCaderno().getId())
                .orElseThrow(() -> new NotFoundException("Caderno não encontrado pelo ID: " + nota.getCaderno().getId()));

        if (!caderno.getUsuario().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        nota.setCaderno(caderno);
        NotaEntity novaNota = service.criar(nota);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaNota);
    }


    @PutMapping("/{id}")
    public ResponseEntity<NotaEntity> atualizar(@PathVariable Long id, @RequestBody NotaEntity nota, Principal principal) {
        Long userId = Long.parseLong(principal.getName());

        NotaEntity existingNota = service.buscarPorId(id);
        if (!existingNota.getCaderno().getUsuario().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        CadernoEntity caderno = cadernoRepository.findById(nota.getCaderno().getId())
                .orElseThrow(() -> new NotFoundException("Caderno não encontrado pelo ID: " + nota.getCaderno().getId()));

        if (!caderno.getUsuario().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        existingNota.setTitulo(nota.getTitulo());
        existingNota.setConteudo(nota.getConteudo());
        existingNota.setCaderno(caderno);

        NotaEntity updatedNota = service.alterar(id, existingNota);
        return ResponseEntity.ok(updatedNota);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        NotaEntity nota = service.buscarPorId(id);
        if (!nota.getCaderno().getUsuario().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

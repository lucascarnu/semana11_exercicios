package com.s11.exercicio_semanal.controllers;

import com.s11.exercicio_semanal.entities.CadernoEntity;
import com.s11.exercicio_semanal.entities.UsuarioEntity;
import com.s11.exercicio_semanal.exceptions.CadernoComNotasException;
import com.s11.exercicio_semanal.exceptions.ForbiddenException;
import com.s11.exercicio_semanal.exceptions.NotFoundException;
import com.s11.exercicio_semanal.repositories.UsuarioRepository;
import com.s11.exercicio_semanal.services.CadernoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cadernos")
public class CadernoController {

    private final CadernoService service;
    private final UsuarioRepository usuarioRepository;

    public CadernoController(CadernoService service, UsuarioRepository usuarioRepository) {
        this.service = service;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<List<CadernoEntity>> buscarTodos(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        List<CadernoEntity> cadernos = service.buscarTodosPorUsuarioId(userId);
        return ResponseEntity.ok().body(cadernos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CadernoEntity> buscarPorId(@PathVariable Long id, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        CadernoEntity caderno = service.buscarPorId(id);
        if (!caderno.getUsuario().getId().equals(userId)) {
            throw new ForbiddenException("Acesso negado para o caderno com ID: " + id);
        }
        return ResponseEntity.ok().body(caderno);
    }

    @PostMapping
    public ResponseEntity<CadernoEntity> criar(@RequestBody CadernoEntity cadernoRequest, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        UsuarioEntity usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado pelo id: " + userId));

        cadernoRequest.setUsuario(usuario);
        CadernoEntity novoCaderno = service.criar(cadernoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCaderno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CadernoEntity> alterar(@PathVariable Long id, @RequestBody CadernoEntity cadernoRequest, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        CadernoEntity caderno = service.buscarPorId(id);
        if (!caderno.getUsuario().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        cadernoRequest.setId(id);
        cadernoRequest.setUsuario(caderno.getUsuario());
        CadernoEntity cadernoAlterado = service.alterar(id, cadernoRequest);
        return ResponseEntity.ok().body(cadernoAlterado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        CadernoEntity caderno = service.buscarPorId(id);
        if (!caderno.getUsuario().getId().equals(userId)) {
            throw new AccessDeniedException("Não autorizado a excluir o caderno com ID: " + id);
        }

        if (service.countNotasByCadernoId(id) > 0) {
            throw new CadernoComNotasException();
        }

        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

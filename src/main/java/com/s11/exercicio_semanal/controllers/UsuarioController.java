package com.s11.exercicio_semanal.controllers;

import com.s11.exercicio_semanal.dtos.request.InserirUsuarioRequest;
import com.s11.exercicio_semanal.entities.UsuarioEntity;
import com.s11.exercicio_semanal.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final BCryptPasswordEncoder bCryptEncoder;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/cadastros")
    public ResponseEntity<?> novoUsuario(@RequestBody InserirUsuarioRequest inserirUsuarioRequest) {
        try {
            boolean usuarioExistente = usuarioRepository.findByNomeUsuario(inserirUsuarioRequest.nomeUsuario())
                    .isPresent();

            if (usuarioExistente){
                throw new Exception("Usuario já existe");
            }

            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setNomeUsuario(inserirUsuarioRequest.nomeUsuario());
            usuario.setSenha(bCryptEncoder.encode(inserirUsuarioRequest.senha()).toString());

            usuarioRepository.save(usuario);

            return new ResponseEntity<>("Criado", HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/cadastros")
    public ResponseEntity<List<UsuarioEntity>> getAllUsuarios() {
        List<UsuarioEntity> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);  // Return list of users
    }

    @GetMapping("cadastros/{id}")
    public ResponseEntity<UsuarioEntity> getUsuarioById(@PathVariable Long id) {
        Optional<UsuarioEntity> usuario = usuarioRepository.findById(id);
        return usuario
                .map(value -> ResponseEntity.ok(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}


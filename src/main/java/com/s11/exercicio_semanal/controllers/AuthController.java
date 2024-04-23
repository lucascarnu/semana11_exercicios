package com.s11.exercicio_semanal.controllers;

import com.s11.exercicio_semanal.dtos.request.LoginRequest;
import com.s11.exercicio_semanal.dtos.response.LoginResponse;
import com.s11.exercicio_semanal.entities.UsuarioEntity;
import com.s11.exercicio_semanal.exceptions.NotFoundException;
import com.s11.exercicio_semanal.exceptions.SenhaInvalidaException;
import com.s11.exercicio_semanal.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final BCryptPasswordEncoder bCryptEncoder;
    private final JwtEncoder jwtEncoder;
    private final UsuarioRepository usuarioRepository;

    private final long TEMPO_EXPIRACAO = 36000L;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> token(@RequestBody LoginRequest loginRequest) {
        UsuarioEntity usuario = usuarioRepository.findByNomeUsuario(loginRequest.nomeUsuario())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        if (!bCryptEncoder.matches(loginRequest.senha(), usuario.getSenha())) {
            throw new SenhaInvalidaException("Senha inválida.");
        }

        String token = gerarToken(usuario);
        return ResponseEntity.ok(new LoginResponse(token, TEMPO_EXPIRACAO));
    }

    private String gerarToken(UsuarioEntity usuario) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("aplicacaosecurity")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(TEMPO_EXPIRACAO))
                .subject(usuario.getId().toString())
                .claim("username", usuario.getNomeUsuario())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}

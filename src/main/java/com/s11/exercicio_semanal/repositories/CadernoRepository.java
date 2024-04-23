package com.s11.exercicio_semanal.repositories;

import com.s11.exercicio_semanal.entities.CadernoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CadernoRepository extends JpaRepository<CadernoEntity, Long> {

    List<CadernoEntity> findByUsuarioId(Long usuarioId);
}

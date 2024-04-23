package com.s11.exercicio_semanal.repositories;

import com.s11.exercicio_semanal.entities.NotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaRepository extends JpaRepository<NotaEntity, Long> {
    List<NotaEntity> findByCadernoUsuarioId(Long usuarioId);

    int countByCadernoId(Long cadernoId);

}


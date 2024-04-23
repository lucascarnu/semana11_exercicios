package com.s11.exercicio_semanal.services;

import com.s11.exercicio_semanal.entities.CadernoEntity;

import java.util.List;

public interface CadernoService {
    CadernoEntity criar(CadernoEntity entity);

    List<CadernoEntity> buscarTodos();

    CadernoEntity buscarPorId(Long id);

    CadernoEntity alterar(Long id, CadernoEntity entity);

    void excluir(Long id);

    List<CadernoEntity> buscarTodosPorUsuarioId(Long usuarioId);

    int countNotasByCadernoId(Long cadernoId);
}

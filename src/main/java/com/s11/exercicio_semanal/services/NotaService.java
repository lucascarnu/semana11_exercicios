package com.s11.exercicio_semanal.services;

import com.s11.exercicio_semanal.entities.NotaEntity;
import java.util.List;

public interface NotaService {

    NotaEntity criar(NotaEntity entity);
    List<NotaEntity> buscarTodos();
    NotaEntity buscarPorId(Long id);
    NotaEntity alterar(Long id, NotaEntity entity);
    void excluir(Long id);
    List<NotaEntity> buscarTodosPorUsuarioId(Long usuarioId);
}

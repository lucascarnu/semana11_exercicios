package com.s11.exercicio_semanal.services;

import com.s11.exercicio_semanal.entities.NotaEntity;
import com.s11.exercicio_semanal.exceptions.NotaByIdNotFoundException;
import com.s11.exercicio_semanal.repositories.NotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaServiceImpl implements NotaService {

    private final NotaRepository repository;

    public NotaServiceImpl(NotaRepository repository) {
        this.repository = repository;
    }

    @Override
    public NotaEntity criar(NotaEntity entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    @Override
    public List<NotaEntity> buscarTodos() {
        return repository.findAll();
    }

    @Override
    public NotaEntity buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotaByIdNotFoundException(id));
    }

    @Override
    public NotaEntity alterar(Long id, NotaEntity entity) {
        buscarPorId(id);
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void excluir(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

    @Override
    public List<NotaEntity> buscarTodosPorUsuarioId(Long usuarioId) {
        return repository.findByCadernoUsuarioId(usuarioId);
    }

}

package com.s11.exercicio_semanal.services;

import com.s11.exercicio_semanal.entities.CadernoEntity;
import com.s11.exercicio_semanal.exceptions.CadernoByIdNotFoundException;
import com.s11.exercicio_semanal.repositories.CadernoRepository;
import com.s11.exercicio_semanal.repositories.NotaRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadernoServiceImpl implements CadernoService {

    private final CadernoRepository repository;
    private NotaRepository notaRepository;

    public CadernoServiceImpl(CadernoRepository repository, NotaRepository notaRepository) {
        this.repository = repository;
        this.notaRepository = notaRepository;
    }

    @Override
    public CadernoEntity criar(CadernoEntity entity) {
        entity.setId(null);
        return repository.save(entity);
    }

    @Override
    public List<CadernoEntity> buscarTodos() {
        return repository.findAll();
    }

    @Override
    public CadernoEntity buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CadernoByIdNotFoundException(id));
    }

    @Override
    public CadernoEntity alterar(Long id, CadernoEntity entity) {
        buscarPorId(id); // Verifica se existe antes de alterar
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void excluir(Long id) {
        buscarPorId(id); // Verifica se existe antes de excluir
        repository.deleteById(id);
    }

    @Override
    public List<CadernoEntity> buscarTodosPorUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    @Override
    public int countNotasByCadernoId(Long cadernoId) {
        return notaRepository.countByCadernoId(cadernoId);
    }


}

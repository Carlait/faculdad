package com.example.universidade.service;

import com.example.universidade.model.Curso;
import com.example.universidade.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Curso adicionar(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Optional<Curso> atualizar(Long id, Curso cursoAtualizado) {
        return cursoRepository.findById(id)
            .map(curso -> {
                curso.setNome(cursoAtualizado.getNome());
                curso.setCargaHoraria(cursoAtualizado.getCargaHoraria());
                return cursoRepository.save(curso);
            });
    }

    public boolean remover(Long id) {
        if (cursoRepository.existsById(id)) {
            cursoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
package com.example.universidade.service;

import com.example.universidade.model.Aluno;
import com.example.universidade.model.Curso;
import com.example.universidade.repository.AlunoRepository;
import com.example.universidade.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    public Optional<Aluno> adicionar(Aluno aluno) {
        if (aluno.getCurso() == null || aluno.getCurso().getId() == null) {
            return Optional.empty();
        }

        Optional<Curso> curso = cursoRepository.findById(aluno.getCurso().getId());
        if (curso.isEmpty()) {
            return Optional.empty();
        }

        aluno.setCurso(curso.get());
        return Optional.of(alunoRepository.save(aluno));
    }

    public Optional<Aluno> atualizar(Long id, Aluno alunoAtualizado) {
        return alunoRepository.findById(id)
            .flatMap(aluno -> {
                aluno.setNome(alunoAtualizado.getNome());
                aluno.setEmail(alunoAtualizado.getEmail());
                aluno.setDataNascimento(alunoAtualizado.getDataNascimento());

                if (alunoAtualizado.getCurso() != null && alunoAtualizado.getCurso().getId() != null) {
                    Optional<Curso> curso = cursoRepository.findById(alunoAtualizado.getCurso().getId());
                    if (curso.isEmpty()) {
                        return Optional.empty(); 
                    }
                    aluno.setCurso(curso.get());
                }
                
                return Optional.of(alunoRepository.save(aluno));
            });
    }

    public boolean remover(Long id) {
        if (alunoRepository.existsById(id)) {
            alunoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
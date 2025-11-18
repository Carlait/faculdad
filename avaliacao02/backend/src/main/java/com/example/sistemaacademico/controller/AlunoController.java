package com.example.sistemaacademico.controller;

import com.example.sistemaacademico.model.Aluno;
import com.example.sistemaacademico.model.Curso;
import com.example.sistemaacademico.repository.AlunoRepository;
import com.example.sistemaacademico.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }

    @PostMapping
    public Aluno cadastrarAluno(@RequestBody Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        return alunoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> editarAluno(@PathVariable Long id, @RequestBody Aluno alunoDetails) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    aluno.setNome(alunoDetails.getNome());
                    aluno.setEmail(alunoDetails.getEmail());
                    aluno.setMatricula(alunoDetails.getMatricula());
                    Aluno updatedAluno = alunoRepository.save(aluno);
                    return ResponseEntity.ok(updatedAluno);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    alunoRepository.delete(aluno);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{alunoId}/matricular/{cursoId}")
    public ResponseEntity<Aluno> matricularAlunoEmCurso(
            @PathVariable Long alunoId,
            @PathVariable Long cursoId) {

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + alunoId));

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com id: " + cursoId));

        aluno.getCursos().add(curso);

        Aluno alunoAtualizado = alunoRepository.save(aluno);
        
        return ResponseEntity.ok(alunoAtualizado);
    }
}
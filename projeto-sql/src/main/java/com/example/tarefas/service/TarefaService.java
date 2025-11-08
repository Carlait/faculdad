package com.example.tarefas.service;

import com.example.tarefas.model.StatusTarefa;
import com.example.tarefas.model.Tarefa;
import com.example.tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    public Optional<Tarefa> buscarPorId(String id) {
        return tarefaRepository.findById(id);
    }

    public Tarefa adicionar(Tarefa tarefa) {
        tarefa.setDataCriacao(LocalDateTime.now());
        tarefa.setStatus(StatusTarefa.PENDENTE);
        return tarefaRepository.save(tarefa);
    }

    public Optional<Tarefa> atualizar(String id, Tarefa tarefaAtualizada) {
        Optional<Tarefa> tarefaExistente = tarefaRepository.findById(id);

        if (tarefaExistente.isPresent()) {
            Tarefa t = tarefaExistente.get();
            t.setTitulo(tarefaAtualizada.getTitulo());
            t.setDescricao(tarefaAtualizada.getDescricao());
            t.setDataConclusao(tarefaAtualizada.getDataConclusao());
            t.setStatus(tarefaAtualizada.getStatus());
            
            return Optional.of(tarefaRepository.save(t));
        } else {
            return Optional.empty();
        }
    }

    public boolean remover(String id) {
        if (tarefaRepository.existsById(id)) {
            tarefaRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
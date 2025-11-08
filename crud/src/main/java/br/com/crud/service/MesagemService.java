package br.com.crud.service;

import br.com.crud.model.Mensagem;
import br.com.crud.repository.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    public List<Mensagem> listarTodas() {
        return mensagemRepository.findAll();
    }

    public Optional<Mensagem> buscarPorId(int id) {
        return mensagemRepository.findById(id);
    }

    public Mensagem adicionar(Mensagem mensagem) {
        return mensagemRepository.save(mensagem);
    }

    public Optional<Mensagem> atualizar(int id, Mensagem mensagemAtualizada) {
        Optional<Mensagem> mensagemExistente = mensagemRepository.findById(id);
        
        if (mensagemExistente.isPresent()) {
            Mensagem m = mensagemExistente.get();
            m.setTexto(mensagemAtualizada.getTexto());
            return Optional.of(mensagemRepository.save(m)); 
        } else {
            return Optional.empty();
        }
    }

    public boolean remover(int id) {
        if (mensagemRepository.existsById(id)) {
            mensagemRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
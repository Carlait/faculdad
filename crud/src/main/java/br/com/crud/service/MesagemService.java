package br.com.crud.service;

import br.com.crud.model.Mensagem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MensagemService {

    private final List<Mensagem> mensagens = new ArrayList<>();
    private final AtomicInteger contadorId = new AtomicInteger();

    public List<Mensagem> listarTodas() {
        return mensagens;
    }

    public Optional<Mensagem> buscarPorId(int id) {
        return mensagens.stream()
                .filter(m -> m.getId() == id)
                .findFirst();
    }

    public Mensagem adicionar(Mensagem mensagem) {
        mensagem.setId(contadorId.incrementAndGet());
        mensagens.add(mensagem);
        return mensagem;
    }

    public Optional<Mensagem> atualizar(int id, Mensagem mensagemAtualizada) {
        Optional<Mensagem> mensagemExistente = buscarPorId(id);
        
        mensagemExistente.ifPresent(m -> {
            m.setTexto(mensagemAtualizada.getTexto());
        });
        
        return mensagemExistente;
    }

    public boolean remover(int id) {
        return mensagens.removeIf(m -> m.getId() == id);
    }
}
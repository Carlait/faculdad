package br.com.crud.controller;

import br.com.crud.model.Mensagem;
import br.com.crud.service.MensagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

    @Autowired
    private MensagemService mensagemService;

    @PostMapping
    public Mensagem adicionarMensagem(@RequestBody Mensagem mensagem) {
        return mensagemService.adicionar(mensagem);
    }

    @GetMapping
    public List<Mensagem> listarMensagens() {
        return mensagemService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mensagem> buscarMensagemPorId(@PathVariable int id) {
        Optional<Mensagem> mensagem = mensagemService.buscarPorId(id);

        if (mensagem.isPresent()) {
            return ResponseEntity.ok(mensagem.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mensagem> atualizarMensagem(@PathVariable int id, @RequestBody Mensagem mensagem) {
        Optional<Mensagem> mensagemAtualizada = mensagemService.atualizar(id, mensagem);
        
        if (mensagemAtualizada.isPresent()) {
            return ResponseEntity.ok(mensagemAtualizada.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerMensagem(@PathVariable int id) {
        boolean removido = mensagemService.remover(id);

        if (removido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
package com.example.docapi.service;

import com.example.docapi.model.Documento;
import com.example.docapi.repository.CategoriaRepository;
import com.example.docapi.repository.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Optional<Documento> salvar(Documento documento) {
        if (documento.getCategoria() == null || documento.getCategoria().getId() == null) {
            return Optional.empty();
        }
        
        return categoriaRepository.findById(documento.getCategoria().getId())
            .map(categoria -> {
                documento.setCategoria(categoria);
                return documentoRepository.save(documento);
            });
    }

    public List<Documento> listarTodos() {
        return documentoRepository.findAll();
    }

    public Optional<Documento> buscarPorId(Long id) {
        return documentoRepository.findById(id);
    }

    public Optional<Documento> atualizar(Long id, Documento documentoAtualizado) {
        return documentoRepository.findById(id)
            .flatMap(documento -> {
                documento.setTitulo(documentoAtualizado.getTitulo());
                documento.setConteudo(documentoAtualizado.getConteudo());

                if (documentoAtualizado.getCategoria() != null && documentoAtualizado.getCategoria().getId() != null) {
                    return categoriaRepository.findById(documentoAtualizado.getCategoria().getId())
                        .map(categoria -> {
                            documento.setCategoria(categoria);
                            return documentoRepository.save(documento);
                        });
                }
                
                return Optional.of(documentoRepository.save(documento));
            });
    }

    public boolean deletar(Long id) {
        if (documentoRepository.existsById(id)) {
            documentoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
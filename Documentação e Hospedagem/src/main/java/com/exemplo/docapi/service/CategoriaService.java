package com.example.docapi.service;

import com.example.docapi.model.Categoria;
import com.example.docapi.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public Optional<Categoria> atualizar(Long id, Categoria categoriaAtualizada) {
        return categoriaRepository.findById(id)
            .map(categoria -> {
                categoria.setNome(categoriaAtualizada.getNome());
                return categoriaRepository.save(categoria);
            });
    }

    public boolean deletar(Long id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
package com.example.docapi.controller;

import com.example.docapi.model.Categoria;
import com.example.docapi.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "Endpoints para gerenciamento de categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    @Operation(summary = "Cria uma nova categoria")
    public Categoria criarCategoria(@RequestBody Categoria categoria) {
        return categoriaService.salvar(categoria);
    }

    @GetMapping
    @Operation(summary = "Lista todas as categorias")
    public List<Categoria> listarCategorias() {
        return categoriaService.listarTodas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma categoria por ID")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma categoria existente")
    public ResponseEntity<Categoria> atualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        return categoriaService.atualizar(id, categoria)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma categoria")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        if (categoriaService.deletar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
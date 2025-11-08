package com.example.docapi.controller;

import com.example.docapi.model.Documento;
import com.example.docapi.service.DocumentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documentos")
@Tag(name = "Documentos", description = "Endpoints para gerenciamento de documentos")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @PostMapping
    @Operation(summary = "Cria um novo documento")
    public ResponseEntity<Documento> criarDocumento(@RequestBody Documento documento) {
        return documentoService.salvar(documento)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping
    @Operation(summary = "Lista todos os documentos")
    public List<Documento> listarDocumentos() {
        return documentoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um documento por ID")
    public ResponseEntity<Documento> buscarPorId(@PathVariable Long id) {
        return documentoService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um documento existente")
    public ResponseEntity<Documento> atualizarDocumento(@PathVariable Long id, @RequestBody Documento documento) {
        return documentoService.atualizar(id, documento)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um documento")
    public ResponseEntity<Void> deletarDocumento(@PathVariable Long id) {
        if (documentoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
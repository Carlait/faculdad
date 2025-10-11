package br.com.empresa.gerenciamentofuncionarios.controller;

import br.com.empresa.gerenciamentofuncionarios.model.Departamento;
import br.com.empresa.gerenciamentofuncionarios.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @PostMapping
    public Departamento cadastrarDepartamento(@RequestBody Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    @GetMapping
    public List<Departamento> listarDepartamentos() {
        return departamentoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Departamento buscarDepartamentoPorId(@PathVariable Long id) {
        return departamentoRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Departamento atualizarDepartamento(@PathVariable Long id, @RequestBody Departamento departamentoDetails) {
        Departamento departamento = departamentoRepository.findById(id).orElse(null);
        if (departamento != null) {
            departamento.setNome(departamentoDetails.getNome());
            departamento.setLocalizacao(departamentoDetails.getLocalizacao());
            return departamentoRepository.save(departamento);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void excluirDepartamento(@PathVariable Long id) {
        departamentoRepository.deleteById(id);
    }
}
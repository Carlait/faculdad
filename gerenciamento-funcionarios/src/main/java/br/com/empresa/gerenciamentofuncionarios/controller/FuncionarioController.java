package br.com.empresa.gerenciamentofuncionarios.controller;

import br.com.empresa.gerenciamentofuncionarios.model.Funcionario;
import br.com.empresa.gerenciamentofuncionarios.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @PostMapping 
    public Funcionario cadastrarFuncionario(@RequestBody Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    @GetMapping 
    public List<Funcionario> listarFuncionarios() {
        return funcionarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Funcionario buscarFuncionarioPorId(@PathVariable Long id) {
        return funcionarioRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}") 
    public Funcionario atualizarFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionarioDetails) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElse(null);
        if (funcionario != null) {
            funcionario.setNome(funcionarioDetails.getNome());
            funcionario.setEmail(funcionarioDetails.getEmail());
            funcionario.setDataAdmissao(funcionarioDetails.getDataAdmissao());
            funcionario.setDepartamento(funcionarioDetails.getDepartamento());
            return funcionarioRepository.save(funcionario);
        }
        return null;
    }

    @DeleteMapping("/{id}") 
    public void excluirFuncionario(@PathVariable Long id) {
        funcionarioRepository.deleteById(id);
    }
}
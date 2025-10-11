package br.com.empresa.gerenciamentofuncionarios.controller;

import br.com.empresa.gerenciamentofuncionarios.model.Departamento;
import br.com.empresa.gerenciamentofuncionarios.model.Funcionario;
import br.com.empresa.gerenciamentofuncionarios.repository.DepartamentoRepository;
import br.com.empresa.gerenciamentofuncionarios.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/departamentos")
    public String listarDepartamentos(Model model) {
        model.addAttribute("departamentos", departamentoRepository.findAll());
        return "departamentos";
    }

    @GetMapping("/departamentos/novo")
    public String novoDepartamentoForm(Model model) {
        model.addAttribute("departamento", new Departamento());
        return "formDepartamento";
    }

    @PostMapping("/departamentos/salvar")
    public String salvarDepartamento(@ModelAttribute Departamento departamento) {
        departamentoRepository.save(departamento);
        return "redirect:/departamentos";
    }

    @GetMapping("/departamentos/editar/{id}")
    public String editarDepartamentoForm(@PathVariable Long id, Model model) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de Departamento inválido:" + id));
        model.addAttribute("departamento", departamento);
        return "formDepartamento";
    }

    @GetMapping("/departamentos/excluir/{id}")
    public String excluirDepartamento(@PathVariable Long id) {
        departamentoRepository.deleteById(id);
        return "redirect:/departamentos";
    }

    @GetMapping("/funcionarios")
    public String listarFuncionarios(Model model) {
        model.addAttribute("funcionarios", funcionarioRepository.findAll());
        return "funcionarios";
    }

    @GetMapping("/funcionarios/novo")
    public String novoFuncionarioForm(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        model.addAttribute("departamentos", departamentoRepository.findAll());
        return "formFuncionario";
    }

    @PostMapping("/funcionarios/salvar")
    public String salvarFuncionario(@ModelAttribute Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
        return "redirect:/funcionarios";
    }

    @GetMapping("/funcionarios/editar/{id}")
    public String editarFuncionarioForm(@PathVariable Long id, Model model) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de Funcionário inválido:" + id));
        model.addAttribute("funcionario", funcionario);
        model.addAttribute("departamentos", departamentoRepository.findAll());
        return "formFuncionario";
    }

    @GetMapping("/funcionarios/excluir/{id}")
    public String excluirFuncionario(@PathVariable Long id) {
        funcionarioRepository.deleteById(id);
        return "redirect:/funcionarios";
    }

    @GetMapping("/departamentos/detalhes/{id}")
    public String detalhesDepartamento(@PathVariable Long id, Model model) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de Departamento inválido:" + id));
        model.addAttribute("departamento", departamento);
        return "detalhesDepartamento";
    }
}
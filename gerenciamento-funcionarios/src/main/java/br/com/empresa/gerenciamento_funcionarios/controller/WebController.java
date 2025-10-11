package br.com.empresa.gerenciamento-funcionarios.controller;

@Controller
public class WebController {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

}
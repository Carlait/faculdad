package com.example.sistemaacademico.controller;

import com.example.sistemaacademico.model.Aluno;
import com.example.sistemaacademico.repository.AlunoRepository;
import com.example.sistemaacademico.repository.CursoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlunoController.class)
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlunoRepository alunoRepository;

    @MockBean
    private CursoRepository cursoRepository;

    @Test
    @WithMockUser(username="user")
    public void testListarAlunos() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Aluno Teste");

        given(alunoRepository.findAll()).willReturn(List.of(aluno));

        mockMvc.perform(get("/api/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome", is("Aluno Teste")));
    }

    @Test
    @WithMockUser(username="user")
    public void testCadastrarAluno() throws Exception {
        Aluno alunoNovo = new Aluno();
        alunoNovo.setId(1L);
        alunoNovo.setNome("Novo Aluno");
        alunoNovo.setEmail("novo@email.com");
        alunoNovo.setMatricula("98765");

        given(alunoRepository.save(any(Aluno.class))).willReturn(alunoNovo);

        String jsonBody = "{\"nome\": \"Novo Aluno\", \"email\": \"novo@email.com\", \"matricula\": \"98765\"}";

        mockMvc.perform(post("/api/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Novo Aluno")))
                .andExpect(jsonPath("$.matricula", is("98765")));
    }

    @Test
    @WithMockUser(username="user")
    public void testBuscarPorId() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Aluno Unico");

        given(alunoRepository.findById(1L)).willReturn(Optional.of(aluno));

        mockMvc.perform(get("/api/alunos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Aluno Unico")));
    }

    @Test
    @WithMockUser(username="user")
    public void testBuscarPorIdNaoEncontrado() throws Exception {
        given(alunoRepository.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/alunos/99"))
                .andExpect(status().isNotFound());
    }
}
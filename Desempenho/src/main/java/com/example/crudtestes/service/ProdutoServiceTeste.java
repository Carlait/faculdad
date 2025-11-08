package com.example.crudtestes.service;

import com.example.crudtestes.model.Produto;
import com.example.crudtestes.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = Produto.builder()
            .id(1L)
            .nome("Notebook")
            .descricao("Notebook de alta performance")
            .preco(5000.00)
            .build();
    }

    @Test
    void deveSalvarProdutoComSucesso() {
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto produtoSalvo = produtoService.salvar(produto);

        assertNotNull(produtoSalvo);
        assertEquals("Notebook", produtoSalvo.getNome());
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    void naoDeveSalvarProdutoSemNome() {
        produto.setNome(null);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            produtoService.salvar(produto);
        });

        assertEquals("O nome do produto é obrigatório.", exception.getMessage());
        verify(produtoRepository, never()).save(any());
    }

    @Test
    void naoDeveSalvarProdutoComPrecoZero() {
        produto.setPreco(0.0);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            produtoService.salvar(produto);
        });

        assertEquals("O preço deve ser positivo.", exception.getMessage());
        verify(produtoRepository, never()).save(any());
    }

    @Test
    void deveListarTodosProdutos() {
        when(produtoRepository.findAll()).thenReturn(List.of(produto));

        List<Produto> produtos = produtoService.listarTodos();

        assertFalse(produtos.isEmpty());
        assertEquals(1, produtos.size());
        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarProdutoPorId() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Optional<Produto> produtoEncontrado = produtoService.buscarPorId(1L);

        assertTrue(produtoEncontrado.isPresent());
        assertEquals(produto.getId(), produtoEncontrado.get().getId());
        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    void deveAtualizarProduto() {
        Produto produtoAtualizado = Produto.builder().nome("Notebook Gamer").descricao("Nova Descrição").preco(5500.00).build();

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Optional<Produto> resultado = produtoService.atualizar(1L, produtoAtualizado);

        assertTrue(resultado.isPresent());
        assertEquals("Notebook Gamer", resultado.get().getNome());
        assertEquals(5500.00, resultado.get().getPreco());
        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void deveDeletarProduto() {
        when(produtoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(produtoRepository).deleteById(1L);

        boolean deletado = produtoService.deletar(1L);

        assertTrue(deletado);
        verify(produtoRepository, times(1)).existsById(1L);
        verify(produtoRepository, times(1)).deleteById(1L);
    }

    @Test
    void naoDeveDeletarProdutoInexistente() {
        when(produtoRepository.existsById(1L)).thenReturn(false);

        boolean deletado = produtoService.deletar(1L);

        assertFalse(deletado);
        verify(produtoRepository, times(1)).existsById(1L);
        verify(produtoRepository, never()).deleteById(1L);
    }
}
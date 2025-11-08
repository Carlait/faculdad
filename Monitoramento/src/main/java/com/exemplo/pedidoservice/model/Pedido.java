package com.example.pedidoservice.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Pedido {
    private Long id;
    private String descricao;
    private BigDecimal valor;
}
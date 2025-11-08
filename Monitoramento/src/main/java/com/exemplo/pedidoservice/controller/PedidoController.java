package com.example.pedidoservice.controller;

import com.example.pedidoservice.model.Pedido;
import com.example.pedidoservice.service.MonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);

    @Autowired
    private MonitorService monitorService;

    @PostMapping
    public ResponseEntity<String> criarPedido(@RequestBody Pedido pedido) {
        logger.info("Recebendo novo pedido. ID: {}, Valor: {}", pedido.getId(), pedido.getValor());

        try {
            monitorService.simularProcessamento();

            if (monitorService.simularTaxaFalha()) {
                logger.error("Falha simulada no processamento do pedido {}", pedido.getId());
                throw HttpServerErrorException.InternalServerError.create(null, null, null, null, null);
            }

            monitorService.registrarValorPedido(pedido.getValor());
            monitorService.incrementarPedidosCriados();
            
            logger.info("Pedido {} processado com sucesso.", pedido.getId());
            return ResponseEntity.ok("Pedido processado");

        } catch (InterruptedException e) {
            logger.error("Processamento interrompido", e);
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500).body("Erro interno");
        }
    }
    
    @GetMapping
    public String healthCheck() {
        return "PedidoService está UP!";
    }
}
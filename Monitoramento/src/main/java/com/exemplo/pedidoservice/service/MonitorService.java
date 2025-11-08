package com.example.pedidoservice.service;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MonitorService {

    @Autowired
    private MeterRegistry registry;
    
    private final AtomicInteger pedidosCriados;
    private final Random random = new Random();

    public MonitorService(MeterRegistry registry) {
        this.registry = registry;
        this.pedidosCriados = registry.gauge("pedidos.criados", new AtomicInteger(0));
    }

    public void registrarValorPedido(BigDecimal valor) {
        registry.summary("pedidos.valor").record(valor.doubleValue());
    }

    public void incrementarPedidosCriados() {
        if (pedidosCriados != null) {
            pedidosCriados.incrementAndGet();
        }
    }
    
    public void simularProcessamento() throws InterruptedException {
        Thread.sleep(random.nextInt(1200));
    }
    
    public boolean simularTaxaFalha() {
        return random.nextInt(100) < 5;
    }
}
package com.example.docapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API de Documentos e Categorias", version = "1.0", description = "API para gerenciar documentos e suas respectivas categorias"))
public class DocApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocApiApplication.class, args);
    }
}
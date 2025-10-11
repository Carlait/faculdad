package br.com.empresa.gerenciamentofuncionarios.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Departamento {

    @Id
    @GeneratedValue(Strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String localizacao;
}
package com.example.sistemaacademico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // [cite: 78]

    private String nome; // [cite: 78]
    private Integer cargaHoraria; // [cite: 78]

    @ManyToMany(mappedBy = "cursos") // [cite: 79]
    @JsonIgnore
    private Set<Aluno> alunos = new HashSet<>();

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Integer getCargaHoraria() {
        return cargaHoraria;
    }
    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
    public Set<Aluno> getAlunos() {
        return alunos;
    }
    public void setAlunos(Set<Aluno> alunos) {
        this.alunos = alunos;
    }
}
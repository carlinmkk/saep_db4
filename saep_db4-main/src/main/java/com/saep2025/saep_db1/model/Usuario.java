package com.saep2025.saep_db1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;

    // Novo campo para armazenar a função/perfil do usuário
    private String role = "Usuário"; // valor padrão

    // Opcional: construtor para conveniência
    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, String role) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.role = role;
    }
}

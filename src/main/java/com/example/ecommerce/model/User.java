package com.example.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // Indica que essa classe é uma tabela no banco de dados
public class User {

    @Id // Indica que esse campo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera IDs automaticamente
    private Long id;

    private String name; // Nome do usuário
    private String email; // Email do usuário
    private String password; // Senha
    private Double creditBalance = 0.0; // Saldo do cartão de crédito



    // Construtores
    public User() {}

    public User(String name, String email, String password, Double creditBalance) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.creditBalance = creditBalance;

    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(Double creditBalance) {
        this.creditBalance = creditBalance;
    }


}
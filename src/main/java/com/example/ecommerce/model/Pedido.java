package com.example.ecommerce.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;

@Entity // Indica que essa classe é uma tabela no banco de dados
public class Pedido {

    @Id // Indica que esse campo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera IDs automaticamente
    private Long id;

    private LocalDateTime dataPedido; // Data e hora do pedido
    private String status; // Status atual do pedido (ex: "pendente", "processando", "enviado")
    private Double total; // Valor total do pedido

    @ManyToOne // Indica que muitos pedidos podem pertencer a um único usuário
    @JoinColumn(name = "user_id") // Coluna na tabela Pedido que faz referência à chave primária de User
    private User user; // O usuário que fez o pedido

    // Construtores
    public Pedido() {}

    public Pedido(LocalDateTime dataPedido, String status, Double total, User user) {
        this.dataPedido = dataPedido;
        this.status = status;
        this.total = total;
        this.user = user;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}



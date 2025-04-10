package com.example.ecommerce.model;

public class Produto {

    private String id; // ID único para identificar o produto
    private String nome; // Nome do produto
    private Double preco; // Preço do produto
    private Integer estoque; // Quantidade disponível no estoque
    private String descricao; // Descrição do produto
    private String categoria; // Categoria do produto

    // Construtores
    public Produto() {}

    public Produto(String id, String nome, Double preco, Integer estoque, String descricao, String categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
package com.example.ecommerce.controller;

import com.example.ecommerce.model.Produto;
import com.azure.cosmos.*;
import com.azure.cosmos.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final CosmosContainer cosmosContainer;

    // Configurar Cosmos Client e Container
    public ProdutoController() {
        String endpoint = "https://cloud-ecommerce02.documents.azure.com:443/";
        String key ="dt58KS9H9NRHZmMKcAD0Vo6hpj1J5KvAl4Kif0K7OYlQx5kIluWXXTCnT6QWBldQVPYDdgB1xxlXACDbZcJR9w==";
        String databaseName = "produtos";
        String containerName = "produtos";

        CosmosClient cosmosClient = new CosmosClientBuilder()
                .endpoint(endpoint)
                .key(key)
                .buildClient();

        // Certifique-se de que o container e o banco de dados existem!
        cosmosContainer = cosmosClient.getDatabase(databaseName).getContainer(containerName);
    }

    // Adicionar Produto
    @PostMapping
    public ResponseEntity<Produto> adicionarProduto(@RequestBody Produto produto) {
        try {
            cosmosContainer.createItem(produto); // Insere diretamente no Cosmos DB
            return new ResponseEntity<>(produto, HttpStatus.CREATED);
        } catch (CosmosException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Listar todos os produtos
    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        try {
            List<Produto> produtos = new ArrayList<>();
            cosmosContainer.readAllItems(new CosmosQueryRequestOptions().getPartitionKey(), Produto.class)
                    .forEach(produtos::add);

            if (produtos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(produtos, HttpStatus.OK);
        } catch (CosmosException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar Produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable String id, @RequestParam String categoria) {
        try {
            Produto produto = cosmosContainer.readItem(id, new PartitionKey(categoria), Produto.class).getItem();
            return new ResponseEntity<>(produto, HttpStatus.OK);
        } catch (CosmosException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Atualizar Produto
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable String id, @RequestParam String categoria, @RequestBody Produto produtoAtualizado) {
        try {
            Produto produtoExistente = cosmosContainer.readItem(id, new PartitionKey(categoria), Produto.class).getItem();

            // Atualizar valores do produto
            produtoExistente.setNome(produtoAtualizado.getNome());
            produtoExistente.setPreco(produtoAtualizado.getPreco());
            produtoExistente.setEstoque(produtoAtualizado.getEstoque());
            produtoExistente.setDescricao(produtoAtualizado.getDescricao());
            produtoExistente.setCategoria(produtoAtualizado.getCategoria());

            cosmosContainer.upsertItem(produtoExistente); // Atualiza ou insere o item
            return new ResponseEntity<>(produtoExistente, HttpStatus.OK);
        } catch (CosmosException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
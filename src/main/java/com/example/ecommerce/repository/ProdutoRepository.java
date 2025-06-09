package com.example.ecommerce.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.example.ecommerce.model.Produto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // Importar Optional

@Repository
public interface ProdutoRepository extends CosmosRepository<Produto, String> {

    List<Produto> findByCategoria(String categoria);

    List<Produto> findByNomeContainingIgnoreCase(String nome);

    List<Produto> findByPrecoBetween(Double minPreco, Double maxPreco);

    Integer countByCategoria(String categoria);

    /**
     * **RECOMENDADO**: Encontra um produto pelo seu ID E pela sua categoria (chave de partição).
     * Essa abordagem otimiza a consulta no Azure Cosmos DB.
     * @param id O ID único do produto.
     * @param categoria A categoria do produto (sua PartitionKey).
     * @return Um Optional contendo o produto, se encontrado.
     */
    Optional<Produto> findByIdAndCategoria(String id, String categoria);
}
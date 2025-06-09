package com.example.ecommerce.repository;

import com.example.ecommerce.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indica que essa interface é um componente de repositório do Spring
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Métodos CRUD básicos para a entidade Pedido já são fornecidos pelo JpaRepository.
    // Você pode adicionar métodos de consulta personalizados aqui, se precisar.
}


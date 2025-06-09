package com.example.ecommerce.controller;


import com.example.ecommerce.model.Pedido;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.PedidoRepository;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UserRepository userRepository;

    // Metodo para listar todos os pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> getAllPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        if (pedidos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(pedidos);
    }

    // Metodo para criar um novo pedido
    @PostMapping
    public ResponseEntity<Pedido> createPedido(@RequestBody Pedido pedido) {
        try {
            if (pedido.getDataPedido() == null) {
                pedido.setDataPedido(LocalDateTime.now());
            }
            if (pedido.getStatus() == null || pedido.getStatus().isEmpty()) {
                pedido.setStatus("pendente");
            }

            if (pedido.getUser() != null && pedido.getUser().getId() != null) {
                Optional<User> existingUser = userRepository.findById(pedido.getUser().getId());
                if (existingUser.isPresent()) {
                    pedido.setUser(existingUser.get());
                } else {
                    return ResponseEntity.badRequest().body(null);
                }
            } else {
                return ResponseEntity.badRequest().body(null);
            }

            Pedido newPedido = pedidoRepository.save(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPedido);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Metodo para buscar um pedido pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            return ResponseEntity.ok(pedido.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Metodo para atualizar um pedido
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable Long id, @RequestBody Pedido pedidoDetails) {
        Optional<Pedido> existingPedidoOptional = pedidoRepository.findById(id);

        if (existingPedidoOptional.isPresent()) {
            Pedido pedido = existingPedidoOptional.get();

            if (pedidoDetails.getDataPedido() != null) {
                pedido.setDataPedido(pedidoDetails.getDataPedido());
            }
            if (pedidoDetails.getStatus() != null && !pedidoDetails.getStatus().isEmpty()) {
                pedido.setStatus(pedidoDetails.getStatus());
            }
            if (pedidoDetails.getTotal() != null) {
                pedido.setTotal(pedidoDetails.getTotal());
            }

            if (pedidoDetails.getUser() != null && pedidoDetails.getUser().getId() != null) {
                Optional<User> existingUser = userRepository.findById(pedidoDetails.getUser().getId());
                if (existingUser.isPresent()) {
                    pedido.setUser(existingUser.get());
                } else {
                    return ResponseEntity.badRequest().body(null);
                }
            } else {
                // Se o usuário não for fornecido ou não tiver ID, manter o usuário existente ou tratar como erro
                // Por agora, assumimos que manter o existente e o comportamento padrão se nenhum novo for dado
                // ou que ele já foi validado na criação. Se precisar de uma validação mais forte, ajustar aqui.
            }

            Pedido updatedPedido = pedidoRepository.save(pedido);
            return ResponseEntity.ok(updatedPedido);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Metodo para deletar um pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePedido(@PathVariable Long id) {
        try {
            pedidoRepository.deleteById(id);
            return ResponseEntity.ok("Pedido deletado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar o pedido.");
        }
    }
}

package br.com.fiap.techchallenge.quickserveapi.Refatorar.interfaces;

import br.com.fiap.techchallenge.quickserveapi.Refatorar.entities.OrderEntity;

import java.util.List;


public interface OrderRepository {
    OrderEntity findById(Long id);

    OrderEntity save(OrderEntity order);

    OrderEntity updateStatus(OrderEntity order);

    List<OrderEntity> findAll();
}

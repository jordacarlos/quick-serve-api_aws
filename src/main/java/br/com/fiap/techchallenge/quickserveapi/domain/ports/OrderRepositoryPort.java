package br.com.fiap.techchallenge.quickserveapi.domain.ports;

import br.com.fiap.techchallenge.quickserveapi.domain.Order;

public interface OrderRepositoryPort {
    Order findById(Long id);

    Order save(Order order);

    Order updateStatus(Order order);
}

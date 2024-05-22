package br.com.fiap.techchallenge.quickserveapi.domain.ports;

import br.com.fiap.techchallenge.quickserveapi.domain.Orders;

public interface OrderRepositoryPort {
    Orders findById(Long id);

    Orders save(Orders order);
}

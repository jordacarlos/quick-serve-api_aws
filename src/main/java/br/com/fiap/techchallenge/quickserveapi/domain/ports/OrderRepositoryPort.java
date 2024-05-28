package br.com.fiap.techchallenge.quickserveapi.domain.ports;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.OrderModelOutput;
import br.com.fiap.techchallenge.quickserveapi.domain.Order;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface OrderRepositoryPort {
    Order findById(Long id);

    Order findByIdToUpdate(Long id);

    Order save(Order order);

    Order updateStatus(Order order);

    Page<OrderModelOutput> findAll(Pageable paginacao);
}

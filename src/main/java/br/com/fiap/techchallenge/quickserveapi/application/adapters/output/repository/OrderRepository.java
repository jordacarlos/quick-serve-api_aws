package br.com.fiap.techchallenge.quickserveapi.application.adapters.output.repository;

import br.com.fiap.techchallenge.quickserveapi.domain.Orders;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.OrderRepositoryPort;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.OrderEntity;
import br.com.fiap.techchallenge.quickserveapi.infra.repositories.OrderJPARepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class OrderRepository implements OrderRepositoryPort {
    private final OrderJPARepository orderJPARepository;


    public OrderRepository(OrderJPARepository orderJPARepository) {
        this.orderJPARepository = orderJPARepository;
    }

    @Override
    public Orders save(Orders orders) {
        try {
            OrderEntity orderEntity = new OrderEntity(orders);
            return this.orderJPARepository.save(orderEntity).toOrder();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "%s", ex);
        }
    }

    @Override
    public Orders findById(Long id) {
        try {
            Optional<OrderEntity> order = this.orderJPARepository.findById(id);
            return order.orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Pedido [%d] não encontrado", id))
            ).toOrder();
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro ao buscar o pedido", ex);
        }
    }
}

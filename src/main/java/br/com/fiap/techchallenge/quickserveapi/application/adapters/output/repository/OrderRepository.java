package br.com.fiap.techchallenge.quickserveapi.application.adapters.output.repository;

import br.com.fiap.techchallenge.quickserveapi.domain.Order;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.OrderRepositoryPort;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.OrderEntity;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.OrderProductsEntity;
import br.com.fiap.techchallenge.quickserveapi.infra.repositories.OrderJPARepository;
import br.com.fiap.techchallenge.quickserveapi.infra.repositories.OrderProductsJPARepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class OrderRepository implements OrderRepositoryPort {
    private final OrderJPARepository orderJPARepository;

    private final OrderProductsJPARepository orderProductsJPARepository;


    public OrderRepository(OrderJPARepository orderJPARepository, OrderProductsJPARepository orderProductsJPARepository) {
        this.orderJPARepository = orderJPARepository;
        this.orderProductsJPARepository = orderProductsJPARepository;
    }

    @Override
    @Transactional
    public Order save(Order orders) {
        try {
            OrderEntity orderEntity = new OrderEntity(orders);
            OrderEntity savedOrder = this.orderJPARepository.save(orderEntity);
            savedOrder.getOrderItems().forEach( orderItem ->{
                OrderProductsEntity orderProducts = new OrderProductsEntity(savedOrder, orderItem.getProduct(), orderItem.getProductQuantity());
                orderProductsJPARepository.save(orderProducts);
            });
            return new Order(savedOrder.getId());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    public Order updateStatus(Order orders) {
        try {
            OrderEntity orderEntity = new OrderEntity(orders);
            OrderEntity savedOrder = this.orderJPARepository.save(orderEntity);
            return new Order(savedOrder.getId());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    public Order findById(Long id) {
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

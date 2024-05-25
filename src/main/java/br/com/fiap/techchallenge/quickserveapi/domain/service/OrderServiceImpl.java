package br.com.fiap.techchallenge.quickserveapi.domain.service;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.OrderInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.OrderModel;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.ProductModel;
import br.com.fiap.techchallenge.quickserveapi.domain.Order;
import br.com.fiap.techchallenge.quickserveapi.domain.enums.OrderStatusEnum;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.OrderRepositoryPort;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.OrderServicePort;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.OrderEntity;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Objects;

public class OrderServiceImpl implements OrderServicePort {

    private final OrderRepositoryPort orderRepositoryPort;

    public OrderServiceImpl(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    public OrderModel placeOrder(OrderInput orderInput) {
        Order order = new Order(orderInput);
        try {
            return orderRepositoryPort.save(order).toOrderModel();
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(String.format("Integridade de dados violada -> %s", e.getMessage()));
        }
    }

    public OrderModel updateOrderStatus(Long id, OrderStatusEnum status) {
        OrderEntity order = toDomainObject(id, status);
        System.out.println(order);
        OrderModel orderModel = toOrderModel(order);
        orderRepositoryPort.save(order.toOrder());
        return orderModel;
    }

    private OrderEntity toDomainObject(Long id, OrderStatusEnum status) {
        Order order = orderRepositoryPort.findById(id);
        if (Objects.nonNull(order)) {
            OrderEntity orderEntity = new OrderEntity(order);
            orderEntity.setStatus(status);
            return orderEntity;
        }else {
            throw new RuntimeException("Order not found");
        }
    }

    private OrderModel toOrderModel(OrderEntity order) {

        return new OrderModel(
                order.getId(),
                order.getStatus(),
                order.getCustomerID(),
                order.getOrderItems().stream().map(product -> new ProductModel(
                        product.getName(),
                        product.getCategory().getDescricao(),
                        product.getPrice(),
                        product.getDescription(),
                        product.getImagePath()
                )).toList(),
                order.getTotalOrderValue());
    }
}

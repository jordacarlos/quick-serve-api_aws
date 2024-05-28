package br.com.fiap.techchallenge.quickserveapi.domain.service;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.OrderInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.OrderModel;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.OrderModelOutput;
import br.com.fiap.techchallenge.quickserveapi.domain.Order;
import br.com.fiap.techchallenge.quickserveapi.domain.OrderProducts;
import br.com.fiap.techchallenge.quickserveapi.domain.enums.OrderStatusEnum;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.OrderRepositoryPort;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.OrderServicePort;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.ProductRepositoryPort;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.OrderEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderServiceImpl implements OrderServicePort {

    private final OrderRepositoryPort orderRepositoryPort;

    private final ProductRepositoryPort productRepositoryPort;

    public OrderServiceImpl(OrderRepositoryPort orderRepositoryPort, ProductRepositoryPort productRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.productRepositoryPort = productRepositoryPort;
    }

    public Page<OrderModelOutput> findAllWithId(Pageable paginacao) {
        return orderRepositoryPort.findAll(paginacao);
    }

    public OrderModel placeOrder(OrderInput orderInput) {
        try {
            List<OrderProducts> itemProducts =
                    orderInput.orderItems()
                            .stream()
                            .map(ordermItemInput ->
                                    new OrderProducts(
                                            null,
                                            productRepositoryPort.findById(ordermItemInput.porductId()),
                                            ordermItemInput.quantity())).toList();

            Order order = new Order(orderInput, itemProducts);

            Order savedOrder = orderRepositoryPort.save(order);
            return savedOrder.toOrderModel();
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(String.format("Integridade de dados violada -> %s", e.getMessage()));
        }
    }

    public OrderModel updateOrderStatus(Long id, OrderStatusEnum status) {
        OrderEntity order = null;
        try {
            order = toDomainObject(id, status);
            System.out.println(order);
            orderRepositoryPort.updateStatus(order.toOrder());
        } catch (Exception e) {
            throw new RuntimeException(String.format("Integridade de dados violada -> %s", e.getMessage()));
        }
        return toOrderModel(order);
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
        return new OrderModel(order.getId());
    }
}

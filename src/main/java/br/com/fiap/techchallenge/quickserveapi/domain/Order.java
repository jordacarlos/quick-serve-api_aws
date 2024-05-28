package br.com.fiap.techchallenge.quickserveapi.domain;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.OrderInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.OrderModel;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.OrderProductModel;
import br.com.fiap.techchallenge.quickserveapi.domain.enums.OrderStatusEnum;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.OrderEntity;

import java.util.List;

public class Order {
    private Long id;
    private Long customerID;
    private OrderStatusEnum status;
    private List<OrderProducts> orderItems;
    private Double totalOrderValue;


    public Order(Long id, Long customerID, OrderStatusEnum status, List<OrderProducts> orderItems, Double totalOrderValue) {
        this.id = id;
        this.customerID = customerID;
        this.status = status;
        this.orderItems = orderItems;
        this.totalOrderValue = totalOrderValue;
    }

    public Order(OrderEntity entity) {
        this.id = entity.getId();
        this.customerID = entity.getCustomerID();
        this.status = entity.getStatus();
        this.orderItems = entity.toOrder().getOrderItems();
        this.totalOrderValue = entity.getTotalOrderValue();
    }

    public Order(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public Order(OrderInput input, List<OrderProducts> orderItems) {
        double totalAmount = orderItems.stream().mapToDouble(product -> product.getProductQuantity() * product.getProduct().getPrice()).sum();
        this.customerID = input.customerID();
        this.status = OrderStatusEnum.RECEBIDO;
        this.orderItems = orderItems;
        this.totalOrderValue = totalAmount;
    }

    public OrderModel toOrderModel() {
        return new OrderModel(this.id);
    }

    public Long getCustomerID() {
        return customerID;
    }

    public Double getTotalOrderValue() {
        return totalOrderValue;
    }

    public List<OrderProducts> getOrderItems() {
        return orderItems;
    }
}

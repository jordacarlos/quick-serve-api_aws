package br.com.fiap.techchallenge.quickserveapi.domain;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.OrderInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.OrderModel;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.ProductModel;
import br.com.fiap.techchallenge.quickserveapi.domain.enums.OrderStatusEnum;

import java.util.List;
import java.util.stream.Collectors;

public class Order {
    private Long id;
    private Long customerID;
    private OrderStatusEnum status;
    private List<Product> orderItems;
    private Double totalOrderValue;


    public Order(Long id, Long customerID, OrderStatusEnum status, Double totalOrderValue) {
        this.id = id;
        this.customerID = customerID;
        this.status = status;
        this.totalOrderValue = totalOrderValue;
    }

    public Long getId() {
        return id;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public Order(OrderInput input) {
        this.customerID = input.customerID();
        this.status = OrderStatusEnum.RECEBIDO;
        this.orderItems = input.orderItems();
        this.totalOrderValue = input.totalOrderValue();
    }

    public OrderModel toOrderModel() {
        return new OrderModel(
                this.id,
                this.status,
                this.customerID,
                this.orderItems.stream().map(product -> new ProductModel(
                        product.getName(),
                        product.getCategoryEnum().getDescricao(),
                        product.getPrice(),
                        product.getDescription(),
                        product.getImagePath())).toList(),
                this.totalOrderValue);
    }

    public Long getCustomerID() {
        return customerID;
    }

    public Double getTotalOrderValue() {
        return totalOrderValue;
    }
}

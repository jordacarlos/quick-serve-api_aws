package br.com.fiap.techchallenge.quickserveapi.application.handler.entities;

import java.util.List;

public class OrderEntity {

    private Long id;
    private String customerID;
    private OrderStatusEnum status;
    private OrderPaymentStatusEnum paymentStatus;
    private List<ProductEntity> orderItems;
    private Double totalOrderValue;

    public OrderEntity() {
    }

    public OrderEntity(Long id, String customerID, OrderStatusEnum status, OrderPaymentStatusEnum paymentStatus, List<ProductEntity> orderItems, Double totalOrderValue) {
        this.id = id;
        this.customerID = customerID;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.orderItems = orderItems;
        this.totalOrderValue = totalOrderValue;
    }

    public OrderEntity(Long id, String customerID, OrderPaymentStatusEnum paymentStatus, List<ProductEntity> orderItems, Double totalOrderValue) {
        this.id = id;
        this.customerID = customerID;
        this.paymentStatus = paymentStatus;
        this.orderItems = orderItems;
        this.totalOrderValue = totalOrderValue;
    }

    public OrderEntity(String customerID, OrderStatusEnum status, OrderPaymentStatusEnum paymentStatus, List<ProductEntity> orderItems) {
        this.customerID = customerID;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.orderItems = orderItems;
        this.totalOrderValue = orderItems.stream().mapToDouble(ProductEntity::getPrice).sum();
    }

    public OrderEntity(String customerID, OrderStatusEnum status) {
        this.customerID = customerID;
        this.status = status;
    }

    public OrderEntity(Long id, OrderPaymentStatusEnum paymentStatus) {
        this.id = id;
        this.paymentStatus = paymentStatus;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerID() {
        return this.customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public OrderStatusEnum getStatus() {
        return this.status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public OrderPaymentStatusEnum getPaymentStatus() {
        return this.paymentStatus;
    }

    public void setPaymentStatus(OrderPaymentStatusEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<ProductEntity> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<ProductEntity> orderItems) {
        this.orderItems = orderItems;
    }

    public Double getTotalOrderValue() {
        if (this.totalOrderValue == null) {
            return orderItems.stream().mapToDouble(ProductEntity::getPrice).sum();
        }
        return this.totalOrderValue;
    }

    public void setTotalOrderValue(Double totalOrderValue) {
        this.totalOrderValue = totalOrderValue;
    }
}

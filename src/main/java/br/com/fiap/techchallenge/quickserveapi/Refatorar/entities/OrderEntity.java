package br.com.fiap.techchallenge.quickserveapi.Refatorar.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OrderEntity {

    private Long id;
    private String customerID;
    private OrderStatusEnum status;
    private List<ProductEntity> orderItems;
    private Double totalOrderValue;

    public OrderEntity(){

    }

    public OrderEntity(Long id, String customerID,OrderStatusEnum status, List<ProductEntity> orderItems, Double totalOrderValue ){
        this.id = id;
        this.customerID = customerID;
        this.status = status;
        this.orderItems = orderItems;
        this.totalOrderValue = totalOrderValue;
    }


    public OrderEntity(Long id, String customerID, List<ProductEntity> orderItems, Double totalOrderValue ){

        this.id = id;
        this.customerID = customerID;
        this.orderItems = orderItems;
        this.totalOrderValue = totalOrderValue;
    }

    public OrderEntity(String customerID,OrderStatusEnum status,  List<ProductEntity> orderItems ){
        double totalAmount = orderItems.stream().mapToDouble(ProductEntity::getPrice).sum();
        this.customerID = customerID;
        this.status = status;
        this.orderItems = orderItems;
        this.totalOrderValue = totalAmount;
    }
    public OrderEntity(String customerID, OrderStatusEnum status){
        this.customerID = customerID;
        this.status = status;
    }


    public  Long getId(){
        return  this.id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getCustomerID(){
        return  this.customerID;
    }
    public void setCustomerID(String customerID){
        this.customerID = customerID;
    }

    public void setStatus(OrderStatusEnum orderStatusEnum){
        this.status = orderStatusEnum;
    }
    public  OrderStatusEnum getStatus(){
        return  this.status;
    }

    public void setOrderItems(List<ProductEntity> orderItems){
        this.orderItems = orderItems;
    }
    public List<ProductEntity> getOrderItems() {
        return orderItems;
    }

    public Double getTotalOrderValue() {
        if(this.totalOrderValue == null)
            return orderItems.stream().mapToDouble(ProductEntity::getPrice).sum();
        return this.totalOrderValue;
    }

    public void setTotalOrderValue(Double totalOrderValue) {
        this.totalOrderValue = totalOrderValue;
    }

}

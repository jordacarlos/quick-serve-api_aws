package br.com.fiap.techchallenge.quickserveapi.application.handler.controllers;

import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.OrderEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.usecases.OrderCase;

import java.util.List;

public class OrderController {

    private final OrderCase orderCase;

    public OrderController(OrderCase orderCase) {
        this.orderCase = orderCase;
    }

    public OrderEntity save( OrderEntity orderInput){
       return orderCase.save(orderInput);
    }
    public OrderEntity updateStatus( OrderEntity orderInput){
        return orderCase.updateStatus(orderInput);
    }
    public List<OrderEntity> findAll(){
        return orderCase.findAll();
    }
    public OrderEntity findById(Long id){
        return orderCase.findById(id);
    }

    public String checkPaymentStatus(Long id){
        return orderCase.checkPaymentStatus(id);
    }
    public List<OrderEntity> listByFilters(){
        return orderCase.listByFilters();
    }
}

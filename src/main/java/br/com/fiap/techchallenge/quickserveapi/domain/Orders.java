package br.com.fiap.techchallenge.quickserveapi.domain;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.OrderInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.OrderModel;
import br.com.fiap.techchallenge.quickserveapi.domain.enuns.OrderStatusEnum;

public class Orders {
    private Long id;
    private String name;
    private OrderStatusEnum status;

    public Orders(Long id, String name,OrderStatusEnum status){
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public Orders(OrderInput input) {
        this.status  = input.status();
    }

    public OrderModel toOrderModel(){
        return new OrderModel(this.id,this.status);
    }
}

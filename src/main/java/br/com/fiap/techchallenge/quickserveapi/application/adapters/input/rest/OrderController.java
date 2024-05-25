package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.rest;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.OrderInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.OrderModel;
import br.com.fiap.techchallenge.quickserveapi.domain.enums.OrderStatusEnum;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.OrderServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/quick_service/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderServicePort orderServicePort;

    public OrderController(OrderServicePort orderServicePort) {
        this.orderServicePort = orderServicePort;
    }


    @PutMapping("/{id}/{status}")
    public OrderModel updateOrderStatus(@PathVariable Long id, @PathVariable OrderStatusEnum status){
        return this.orderServicePort.updateOrderStatus(id,status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderModel placeOrder(@RequestBody OrderInput orderInput){
        return this.orderServicePort.placeOrder(orderInput);
    }


}
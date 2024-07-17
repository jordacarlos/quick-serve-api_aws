package br.com.fiap.techchallenge.quickserveapi.Refatorar.api;

import br.com.fiap.techchallenge.quickserveapi.Refatorar.entities.OrderEntity;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.entities.OrderStatusEnum;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.usecases.OrderUseCases;
import ch.qos.logback.core.CoreConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

@RestController
@RequestMapping(path = "/quick_service/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class Order {

    private final OrderUseCases orderUseCases;
    @Autowired
    public Order(OrderUseCases orderUseCases) {
        this.orderUseCases = orderUseCases;
    }

    @PutMapping("/{id}/{status}")
    public OrderEntity updateOrderEntityStatus(@PathVariable Long id, @PathVariable OrderStatusEnum status){
        OrderEntity order = this.orderUseCases.findById(id);
        order.setStatus((OrderStatusEnum) status);
        return this.orderUseCases.updateStatus(order);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderEntity placeOrder(@RequestBody  OrderEntity orderInput){
         return this.orderUseCases.save(orderInput);
    }

    @GetMapping
    public List<OrderEntity> list() {
        return this.orderUseCases.findAll();
    }

}

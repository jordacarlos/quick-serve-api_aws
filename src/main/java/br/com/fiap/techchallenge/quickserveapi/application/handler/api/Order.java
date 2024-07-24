package br.com.fiap.techchallenge.quickserveapi.application.handler.api;

import br.com.fiap.techchallenge.quickserveapi.application.handler.controllers.OrderController;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.OrderEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.OrderPaymentStatusEnum;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/quick_service/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class Order {

    private final OrderController orderController;
    @Autowired
    public Order(OrderController orderController) {
        this.orderController = orderController;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderEntity placeOrder(@RequestBody  OrderEntity orderInput){
        return this.orderController.save(orderInput);
    }

    @GetMapping
    public List<OrderEntity> list() {
        return this.orderController.findAll();
    }

    @PutMapping("/{id}/{status}")
    public OrderEntity updateOrderEntityStatus(@PathVariable Long id, @PathVariable OrderStatusEnum status){
        OrderEntity order = this.orderController.findById(id);
        order.setStatus((OrderStatusEnum) status);
        return this.orderController.updateStatus(order);
    }

    @GetMapping("/payment/{id}")
    public String checkPaymentStatus(@PathVariable Long id) {
        return this.orderController.checkPaymentStatus(id);
    }

    @GetMapping("/list")
    public List<OrderEntity> listByFilters() {
        return this.orderController.listByFilters();
    }

    @PutMapping("/payment-approver/{id}/{status}")
    public OrderEntity paymentApprover(@PathVariable Long id, @PathVariable  OrderPaymentStatusEnum status) {
        return this.orderController.paymentApprover(id,status);
    }
}

package br.com.fiap.techchallenge.quickserveapi.domain.ports;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.OrderInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.OrderModel;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.OrderModelOutput;
import br.com.fiap.techchallenge.quickserveapi.domain.enums.OrderStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderServicePort {
    OrderModel updateOrderStatus(Long id, OrderStatusEnum status);

    OrderModel placeOrder(OrderInput orderInput);

    Page<OrderModelOutput> findAllWithId(Pageable pageable);
}

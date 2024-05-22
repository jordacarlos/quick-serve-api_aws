package br.com.fiap.techchallenge.quickserveapi.domain.ports;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.OrderModel;
import br.com.fiap.techchallenge.quickserveapi.domain.enuns.OrderStatusEnum;

public interface OrderServicePort {
    OrderModel updateOrderStatus(Long id, OrderStatusEnum status);
}

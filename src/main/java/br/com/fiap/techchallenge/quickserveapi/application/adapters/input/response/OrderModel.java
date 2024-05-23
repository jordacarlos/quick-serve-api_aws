package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response;

import br.com.fiap.techchallenge.quickserveapi.domain.enums.OrderStatusEnum;
import jakarta.validation.constraints.NotBlank;

public record OrderModel(
        @NotBlank
        Long id,
        @NotBlank
        OrderStatusEnum status) {
}

package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request;

import br.com.fiap.techchallenge.quickserveapi.domain.enums.OrderStatusEnum;
import jakarta.validation.constraints.NotBlank;

public record OrderInput(
        @NotBlank
        OrderStatusEnum status
) {
}

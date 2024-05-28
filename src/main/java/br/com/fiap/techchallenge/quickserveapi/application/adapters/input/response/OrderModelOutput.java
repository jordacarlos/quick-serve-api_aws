package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response;

import br.com.fiap.techchallenge.quickserveapi.domain.enums.OrderStatusEnum;
import jakarta.validation.constraints.NotBlank;

public record OrderModelOutput(
        Long id,
        OrderStatusEnum status
){}

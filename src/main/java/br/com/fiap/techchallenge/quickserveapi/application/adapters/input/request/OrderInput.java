package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request;

import br.com.fiap.techchallenge.quickserveapi.domain.Product;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderInput(
        Long customerID,
        @NotEmpty
        List<Product> orderItems,
        @NotNull
        Double totalOrderValue
) {
}

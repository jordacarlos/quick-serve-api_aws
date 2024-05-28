package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.rest.openapi;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.OrderInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.OrderModel;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.OrderModelOutput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.rest.openapi.model.PageableParameter;
import br.com.fiap.techchallenge.quickserveapi.domain.enums.OrderStatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "Pedidos", description = "Gerencia fila de pedidos")
public interface OrderControllerOpenApi {

    @Operation(summary = "Atualiza status do pedido")
    public OrderModel updateOrderStatus(
        @Parameter(description = "ID do pedido", required = true)
        Long id,
        @Parameter(description = "Status do pedido", required = true)
        OrderStatusEnum status
    );

    @Operation(summary = "Inclusão de pedido")
    public OrderModel placeOrder(
            @Parameter(description = "Informações para inclusão de pedido", required = true)
            OrderInput orderInput);

    @Operation(summary = "Lista pedidos")
    @PageableParameter
    public Page<OrderModelOutput> list(@Parameter(hidden = true) Pageable pageable);

}


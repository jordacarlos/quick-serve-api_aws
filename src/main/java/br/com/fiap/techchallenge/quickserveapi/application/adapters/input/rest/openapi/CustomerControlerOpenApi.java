package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.rest.openapi;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.CustomerInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.CustomerUpdate;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.CustomerModel;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.CustomerModelOutput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.rest.openapi.model.PageableParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Tag(name = "Clientes", description = "Gerencia cadastro de Clientes")
public interface CustomerControlerOpenApi {

    @Operation(summary = "Lista clientes")
    @PageableParameter
    public Page<CustomerModelOutput> list(@Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Encontra cliente por ID")
    public CustomerModel findById(
            @Parameter(description = "ID do cliente", required = true)
            Long id);

    @Operation(summary = "Encontra cliente por CPF")
    public Optional<CustomerModelOutput> findByCpf(
            @Parameter(description = "CPF do cliente", required = true)
            String cpf);

    @Operation(summary = "Inclui clientes")
    public CustomerModel add(
            @RequestBody(description = "Informações para inclusão de clientes", required = true)
            CustomerInput customerInput);

    @Operation(summary = "Exclui cliente")
    public void delete(
            @Parameter(description = "ID para exclusão de Cliente", required = true)
            Long id);

    @Operation(summary = "Edita cliente")
    public CustomerModel update(
            @Parameter(description = "ID do cliente", required = true)
            Long id,
            @RequestBody(description = "Informações para edição do cliente", required = true)
            CustomerUpdate customerUpdate);

}


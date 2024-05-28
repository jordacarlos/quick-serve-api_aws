package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.rest.openapi;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.ProductInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.ProductUpdate;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.ProductModel;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.ProductModelOutput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.rest.openapi.model.PageableParameter;
import br.com.fiap.techchallenge.quickserveapi.domain.enums.CategoryEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Tag(name = "Produtos", description = "Gerencia cadastro de Produtos")
public interface ProductControllerOpenApi {

    @Operation(summary = "Lista produtos")
    @PageableParameter
    public Page<ProductModelOutput> list(@Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Encontra produto por ID")
    public ProductModel findById(
            @Parameter(description = "ID do produto", required = true)
            Long id);

    @Operation(summary = "Encontra produto por Categoria")
    public List<ProductModel> findByCategory(
            @Parameter(description = "Categoria do produto", required = true)
            CategoryEnum category);

    @Operation(summary = "Inclui produtos")
    public ProductModel add(
            @RequestBody(description = "Informações para inclusão de produtos", required = true)
            ProductInput productInput);

    @Operation(summary = "Exclui produto")
    public void delete(
            @Parameter(description = "ID para exclusão de Produto", required = true)
            Long id);

    @Operation(summary = "Edita produto")
    public ProductModel update(
            @Parameter(description = "ID do produto", required = true)
            Long id,
            @RequestBody(description = "Informações para edição do produto", required = true)
            ProductUpdate productUpdate);

}


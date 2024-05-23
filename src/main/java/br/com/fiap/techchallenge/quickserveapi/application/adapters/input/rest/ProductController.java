package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.rest;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.ProductInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.ProductUpdate;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.ProductModel;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.ProductModelOutput;
import br.com.fiap.techchallenge.quickserveapi.application.handler.exception.CategoryNotFoundException;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.ProductServicePort;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/quick_service/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductServicePort productServicePort;

    public ProductController(ProductServicePort productServicePort) {
        this.productServicePort = productServicePort;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductModel add(@RequestBody @Valid ProductInput productInput) {
        return this.productServicePort.save(productInput);
    }

    @GetMapping
    public Page<ProductModelOutput> list(Pageable pageable) {
        return this.productServicePort.findAllWithId(pageable);
    }

    @GetMapping("/{category}")
    public List<ProductModel> findByCategory(@PathVariable String category) throws CategoryNotFoundException {
        return this.productServicePort.findByCategory(category);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.productServicePort.delete(id);
    }

    @PutMapping("/{id}")
    public ProductModel update(@PathVariable Long id, @RequestBody @Valid ProductUpdate productUpdate) {
        return this.productServicePort.update(id, productUpdate);
    }

}

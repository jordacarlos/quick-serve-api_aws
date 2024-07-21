package br.com.fiap.techchallenge.quickserveapi.application.handler.api;

import br.com.fiap.techchallenge.quickserveapi.application.handler.controllers.ProductController;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.CategoryEnum;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.ProductEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.gateway.ProductGateway;
import br.com.fiap.techchallenge.quickserveapi.application.handler.usecases.ProductUseCases;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/quick_service/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class Product {

    private final ProductController productController;

    @Autowired
    public Product( ProductController productController) {
        this.productController = productController;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductEntity add(@RequestBody @Valid ProductEntity productInput) {
        return this.productController.save(productInput);
    }

    @GetMapping("/{id}")
    public ProductEntity findById(@PathVariable Long id) {
        return productController.findById(id);
    }

    @GetMapping("/by_category/{category}")
    public List<ProductEntity> findByCategory(@PathVariable CategoryEnum category) {
        return productController.findByCategory(category);
    }

    @GetMapping()
    public List<ProductEntity> findAll() {
        return productController.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable Long id) {
        return this.productController.delete(id);
    }

    @PutMapping("/{id}")
    public ProductEntity update(@PathVariable Long id, @RequestBody @Valid ProductEntity productInput) {
        return this.productController.update(id, productInput);
    }
}

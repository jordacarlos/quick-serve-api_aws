package br.com.fiap.techchallenge.quickserveapi.Refatorar.api;

import br.com.fiap.techchallenge.quickserveapi.Refatorar.entities.CategoryEnum;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.entities.ProductEntity;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.usecases.ProductUseCases;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/quick_service/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class Product {
    private final ProductUseCases productUseCases;

    @Autowired
    public Product(ProductUseCases productUseCases) {
        this.productUseCases = productUseCases;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductEntity add(@RequestBody @Valid ProductEntity productInput) {
        return this.productUseCases.save(productInput);
    }


    @GetMapping("/{id}")
    public ProductEntity findById(@PathVariable Long id) {
        return productUseCases.findById(id);
    }

    @GetMapping("/by_category/{category}")
    public List<ProductEntity> findByCategory(@PathVariable CategoryEnum category) {
        return productUseCases.findByCategory(category);
    }

    @GetMapping()
    public List<ProductEntity> findAll() {
        return productUseCases.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable Long id) {
        return this.productUseCases.delete(id);
    }

    @PutMapping("/{id}")
    public ProductEntity update(@PathVariable Long id, @RequestBody @Valid ProductEntity productInput) {
        return this.productUseCases.update(id, productInput);
    }
}

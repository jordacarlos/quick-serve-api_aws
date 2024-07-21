package br.com.fiap.techchallenge.quickserveapi.application.handler.api;

import br.com.fiap.techchallenge.quickserveapi.application.handler.controllers.CustomerController;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.CustomerEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.gateway.CustomerGateway;
import br.com.fiap.techchallenge.quickserveapi.application.handler.usecases.CustomerCase;
import br.com.fiap.techchallenge.quickserveapi.application.handler.usecases.CustomerUseCases;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/quick_service/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class Customer{

    private final CustomerController customerController;

    @Autowired
    public Customer(CustomerController customerController) {
        this.customerController = customerController;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerEntity add(@RequestBody @Valid CustomerEntity customerInput) {
        return this.customerController.save(customerInput);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable Long id) {
        return this.customerController.delete(id);
    }

    @GetMapping("/auth/{cpf}")
    public CustomerEntity findByCpf(@PathVariable String cpf) {
        return customerController.findByCpf(cpf);
    }

    @GetMapping("/{id}")
    public CustomerEntity findById(@PathVariable Long id) {
        return customerController.findById(id);
    }

    @GetMapping()
    public List<CustomerEntity> findAll() {
        return customerController.findAll();
    }

    @PutMapping("/{id}")
    public CustomerEntity update(@PathVariable Long id, @RequestBody @Valid CustomerEntity customerInput) {
        customerInput.setId(id);
        return this.customerController.update(id, customerInput);
    }
}

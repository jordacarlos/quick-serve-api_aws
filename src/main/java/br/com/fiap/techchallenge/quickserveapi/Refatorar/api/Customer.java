package br.com.fiap.techchallenge.quickserveapi.Refatorar.api;

import br.com.fiap.techchallenge.quickserveapi.Refatorar.entities.CustomerEntity;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.usecases.CustomerUseCases;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.CustomerUpdate;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.CustomerModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/quick_service/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class Customer{
    private final CustomerUseCases customerUseCases;

    @Autowired
    public Customer(CustomerUseCases customerUseCases) {
        this.customerUseCases = customerUseCases;
    }

    @GetMapping("/{id}")
    public CustomerEntity findById(@PathVariable Long id) {
        return customerUseCases.findById(id);
    }

    @GetMapping("/auth/{cpf}")
    public CustomerEntity findByCpf(@PathVariable String cpf) {
        return customerUseCases.findByCpf(cpf);
    }

    @GetMapping()
    public List<CustomerEntity> findAll() {
        return customerUseCases.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerEntity add(@RequestBody @Valid CustomerEntity customerInput) {
        return this.customerUseCases.save(customerInput);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable Long id) {
        return this.customerUseCases.delete(id);
    }

    @PutMapping("/{id}")
    public CustomerEntity update(@PathVariable Long id, @RequestBody @Valid CustomerEntity customerInput) {
        return this.customerUseCases.update(id, customerInput);
    }
}

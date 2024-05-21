package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.rest;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.CustomerModel;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.CustomerModelOutput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.CustomerInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.CustomerUpdate;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.CustomerServicePort;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/quick_service/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private final CustomerServicePort customerServicePort;

    public CustomerController(CustomerServicePort customerServicePort) {
        this.customerServicePort = customerServicePort;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerModel add(@RequestBody @Valid CustomerInput customerInput) {
        return this.customerServicePort.save(customerInput);
    }

    @GetMapping
    public Page<CustomerModelOutput> list(Pageable pageable) {
        return this.customerServicePort.findAllWithId(pageable);
    }

    @GetMapping("/{id}")
    public CustomerModel findByAccessKey(@PathVariable Long id) {
        return this.customerServicePort.findOrElseById(id);
    }

    @GetMapping("/auth/{cpf}")
    public Optional<CustomerModelOutput> findByCpf(@PathVariable String cpf) {
        return this.customerServicePort.findByCpf(cpf);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.customerServicePort.delete(id);
    }

    @PutMapping("/{id}")
    public CustomerModel update(@PathVariable Long id, @RequestBody @Valid CustomerUpdate customerUpdate) {
        return this.customerServicePort.update(id, customerUpdate);
    }
}

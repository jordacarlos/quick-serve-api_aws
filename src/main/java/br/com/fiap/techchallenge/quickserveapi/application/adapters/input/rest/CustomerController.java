package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.rest;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.CustomerModel;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.CustomerModelOutput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.CustomerInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.CustomerUpdate;
import br.com.fiap.techchallenge.quickserveapi.domain.service.CustomerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/quick_service/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    ///Cadastro de usuario
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerModel add(@RequestBody @Valid CustomerInput customerInput) {
        return customerService.save(customerInput);
    }

    /*
    @GetMapping
    public Page<CustomerModel> list(Pageable pageable) {
        return customerService.findAll(pageable);
    }

     */

    @GetMapping
    public Page<CustomerModelOutput> list(Pageable pageable) {
        return customerService.findAllWithId(pageable);
    }

    @GetMapping("/{id}")
    public CustomerModel findByAccessKey(@PathVariable Long id) {
        return customerService.findOrElseById(id);
    }

    @GetMapping("/auth/{cpf}")
    public Optional<CustomerModel> findByCpf(@PathVariable String cpf) {
        return customerService.findByCpf(cpf);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        customerService.remove(id);
    }

    @PutMapping("/{id}")
    public CustomerModel update(@PathVariable Long id, @RequestBody @Valid CustomerUpdate customerUpdate) {
        return customerService.update(id, customerUpdate);
    }
}

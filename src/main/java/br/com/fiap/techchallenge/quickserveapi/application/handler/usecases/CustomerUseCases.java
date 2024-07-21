package br.com.fiap.techchallenge.quickserveapi.application.handler.usecases;

import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.CustomerEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces.CustomerRepository;

import java.util.List;

public class CustomerUseCases implements CustomerRepository {

    private final CustomerRepository customerRepository;

    public CustomerUseCases(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerEntity findById(Long id) {
        return customerRepository.findById(id);
    }

    public CustomerEntity findByCpf(String cpf) {
        return customerRepository.findByCpf(cpf);
    }
    public List<CustomerEntity> findAll() {
        return customerRepository.findAll();
    }

    public CustomerEntity save(CustomerEntity customer) {
        return customerRepository.save(customer);
    }

    public CustomerEntity update(Long id,CustomerEntity customer) {
        return customerRepository.update(id,customer);
    }

    public String delete(Long id) {
       return  customerRepository.delete(id);
    }
}

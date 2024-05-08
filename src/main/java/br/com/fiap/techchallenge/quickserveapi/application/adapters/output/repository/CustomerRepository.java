package br.com.fiap.techchallenge.quickserveapi.application.adapters.output.repository;

import br.com.fiap.techchallenge.quickserveapi.domain.Customer;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.CustomerRepositoryPort;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.CustomerEntity;
import br.com.fiap.techchallenge.quickserveapi.infra.repositories.CustomerJPARepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepository implements CustomerRepositoryPort {

    private final CustomerJPARepository customerJPARepository;

    public CustomerRepository(CustomerJPARepository customerJPARepository) {
        this.customerJPARepository = customerJPARepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity(customer);
        return this.customerJPARepository.save(customerEntity).toCustomer();
    }

    @Override
    public Customer finbdById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}

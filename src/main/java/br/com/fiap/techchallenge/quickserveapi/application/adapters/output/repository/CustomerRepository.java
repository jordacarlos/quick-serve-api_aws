package br.com.fiap.techchallenge.quickserveapi.application.adapters.output.repository;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.CustomerModelOutput;
import br.com.fiap.techchallenge.quickserveapi.domain.Customer;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.CustomerRepositoryPort;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.CustomerEntity;
import br.com.fiap.techchallenge.quickserveapi.infra.repositories.CustomerJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
    public Customer findById(Long id) {
        Optional<CustomerEntity> customer = this.customerJPARepository.findById(id);
        if(customer.isPresent()) return customer.get().toCustomer();
        throw new RuntimeException(String.format("Cliente [%d] não encontrado", id));
    }

    @Override
    public Customer findByCpf(String cpf) {
        Optional<CustomerEntity> customer = customerJPARepository.findByCpf(cpf);
        if(customer.isPresent()) return customer.get().toCustomer();
        throw new RuntimeException(String.format("CPF [%s] não foi encontrado", cpf));
    }

    @Override
    public void deleteById(Long id) {
        this.customerJPARepository.deleteById(id);
    }

    public Page<CustomerModelOutput> findAll(Pageable pageable) {
        Page<CustomerEntity> customersPage = this.customerJPARepository.findAll(pageable);
        return customersPage.map(this::toCustomerModelOutput);
    }

    private CustomerModelOutput toCustomerModelOutput(CustomerEntity customer) {
        return new CustomerModelOutput(customer.getId(), customer.getName(), customer.getEmail(), customer.getCpf());
    }
}

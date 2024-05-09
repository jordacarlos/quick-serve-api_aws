package br.com.fiap.techchallenge.quickserveapi.domain.service;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.CustomerInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.CustomerUpdate;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.CustomerModel;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.CustomerModelOutput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.output.repository.CustomerRepository;
import br.com.fiap.techchallenge.quickserveapi.domain.Customer;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.CustomerServicePort;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.CustomerEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerServicePort {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerModel save(CustomerInput customerInput) {
        Customer customer = new Customer(customerInput);
        try {
            return customerRepository.save(customer).toCustomerModel();
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(String.format("Integridade de dados violada -> %s", e.getMessage()));
        }
    }

    public void delete(Long customerId) {
        try {
            customerRepository.deleteById(customerId);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Customer not found -> "+customerId);

        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(
                    String.format("Código %d não pode ser removida, pois está em uso", customerId));
        }
    }

    public Page<CustomerModelOutput> findAllWithId(Pageable paginacao) {
        return customerRepository.findAll(paginacao);
    }

    public Optional<CustomerModel> findById(Long id) {
        Customer customer = customerRepository.findById(id);
        return Optional.of(customer.toCustomerModel());
    }

    public Optional<CustomerModel> findByCpf(String cpf) {
        Customer customer = customerRepository.findByCpf(cpf);
        return Optional.of(customer.toCustomerModel());
    }

    public void remove(Long id) {
        Customer customer = customerRepository.findById(id);
        if (Objects.isNull(customer)) throw new RuntimeException(String.format("ID de acesso [%d] não encontrado", id));
        customerRepository.deleteById(customer.getId());
    }

    public CustomerModel findOrElseById(Long id) {

        Optional<CustomerModel> optionalCustomerModel = findById(id);

        if (optionalCustomerModel.isEmpty())
            throw new RuntimeException("Customer not found");

        return optionalCustomerModel.get();

    }

    public CustomerModel update(Long id, CustomerUpdate customerUpdate) {
        CustomerEntity customer = toDomainObject(id, customerUpdate);
        CustomerModel customerModel = toCustomerModel(customer);
        customerRepository.save(customer.toCustomer());
        return customerModel;
    }

    private CustomerEntity toDomainObject(Long id, CustomerUpdate customerUpdate) {
        Customer customer = customerRepository.findById(id);

        if (Objects.nonNull(customer)) {

            CustomerEntity customerEntity = new CustomerEntity(customer);

            if (!customerUpdate.name().isBlank()) {
                customerEntity.setName(customerUpdate.name());
            }

            if (!customerUpdate.email().isBlank()) {
                customerEntity.setEmail(customerUpdate.email());
            }
            return customerEntity;
        }else {

            throw new RuntimeException("Customer not found");

        }

    }

    private CustomerModel toCustomerModel(CustomerEntity customer) {
        return new CustomerModel(customer.getName(), customer.getEmail(), customer.getCpf());
    }
}

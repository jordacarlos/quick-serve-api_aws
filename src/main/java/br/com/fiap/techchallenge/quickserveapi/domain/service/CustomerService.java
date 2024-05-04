package br.com.fiap.techchallenge.quickserveapi.domain.service;

import br.com.fiap.techchallenge.quickserveapi.api.model.CustomerModel;
import br.com.fiap.techchallenge.quickserveapi.api.model.input.CustomerInput;
import br.com.fiap.techchallenge.quickserveapi.api.model.input.CustomerUpdate;
import br.com.fiap.techchallenge.quickserveapi.domain.model.Customer;
import br.com.fiap.techchallenge.quickserveapi.domain.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer save(Customer customer) {

        try {
            return customerRepository.save(customer);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(String.format("Integridade de dados violada -> %s", e.getMessage()));
        }
    }

    @Transactional
    public void delete(Long customerId) {
        try {
            customerRepository.deleteById(customerId);
            customerRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Customer not found -> " + customerId);

        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(
                    String.format("Código %d não pode ser removida, pois está em uso", customerId));
        }
    }

    public Page<CustomerModel> findAll(Pageable pageable) {

        Page<Customer> customersPage = customerRepository.findAll(pageable);
        List<CustomerModel> customerModelList = toCustomerModels(customersPage);
        Page<CustomerModel> customerModelPageable = new PageImpl<>(customerModelList, pageable,customersPage.getTotalElements());

        return customerModelPageable;
    }

    private List<CustomerModel> toCustomerModels(Page<Customer> customersPage) {
        List<CustomerModel> customerModelList = new ArrayList<>();

        for ( Customer customer : customersPage.getContent() ) {
            customerModelList.add(toCustomerModel(customer));
        }
        return customerModelList;
    }

    private CustomerModel toCustomerModel(Customer customer) {
        return new CustomerModel(customer.getName(), customer.getEmail(), customer.getCpf());
    }

    public Optional<CustomerModel> findById(Long id) {

        Optional<CustomerModel> optionalCustomerModel = Optional.empty();

        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if ( optionalCustomer.isPresent() ) {
            CustomerModel customerModel = toCustomerModel(optionalCustomer.get());
            optionalCustomerModel = Optional.of(customerModel);
        }

        return optionalCustomerModel;
    }

    public void remove(Long id) {

        Optional<Customer> customer = customerRepository.findById(id);

        if ( !customer.isPresent() )
            throw new RuntimeException(String.format("ID de acesso [%d] não encontrado", id));

        customerRepository.deleteById( customer.get().getId() );
    }

    public CustomerModel findOrElseById(Long id) {

        Optional<CustomerModel> optionalCustomerModel = findById(id);

        if ( optionalCustomerModel.isEmpty() )
            throw new RuntimeException("Customer not found");

        return optionalCustomerModel.get();

    }

    private Customer toDomainObject(CustomerInput customerInput) {
        return new Customer(
            customerInput.name(),
            customerInput.email(),
            customerInput.cpf()
        );
    }

    private Customer toDomainObject(Long id, CustomerUpdate customerUpdate) {

        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if ( optionalCustomer.isPresent() ) {

            Customer customer = optionalCustomer.get();

            if ( !customerUpdate.name().isBlank() ) {
                customer.setName(customerUpdate.name());
            }

            if ( !customerUpdate.email().isBlank() ) {
                customer.setEmail(customerUpdate.email());
            }

            if ( !customerUpdate.cpf().isBlank() ) {
                customer.setCpf(customerUpdate.cpf());
            }

            return customer;

        }
        else {

            throw new RuntimeException("Customer not found");

        }

    }

    public CustomerModel save(CustomerInput customerInput) {
        Customer customer = toDomainObject(customerInput);
        CustomerModel customerModel = toCustomerModel(customer);
        save(customer);
        return customerModel;
    }

    public CustomerModel update(Long id, CustomerUpdate customerUpdate) {
        Customer customer = toDomainObject(id, customerUpdate);
        CustomerModel customerModel = toCustomerModel(customer);
        save(customer);
        return customerModel;
    }
}

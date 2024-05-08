package br.com.fiap.techchallenge.quickserveapi.domain.service;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.CustomerModel;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.CustomerModelOutput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.CustomerInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.CustomerUpdate;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.CustomerServicePort;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.CustomerEntity;
import br.com.fiap.techchallenge.quickserveapi.infra.repositories.CustomerJPARepository;
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
public class CustomerServiceImpl implements CustomerServicePort {

    @Autowired
    private CustomerJPARepository customerRepository;

    @Transactional
    public CustomerEntity save(CustomerEntity customer) {
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
            throw new RuntimeException("Customer not found -> "+customerId);

        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(
                    String.format("Código %d não pode ser removida, pois está em uso", customerId));
        }
    }

    public Page<CustomerModelOutput> findAllWithId(Pageable paginacao) {
        Page<CustomerEntity> customersPage = customerRepository.findAll(paginacao);
        Page<CustomerModelOutput> customerModelPage = customersPage.map(this::toCustomerModelOutput);
        return customerModelPage;
    }

    private CustomerModelOutput toCustomerModelOutput(CustomerEntity customer) {
        return new CustomerModelOutput(customer.getId(), customer.getName(), customer.getEmail(), customer.getCpf());
    }

    public Page<CustomerModel> findAll(Pageable pageable) {

        Page<CustomerEntity> customersPage = customerRepository.findAll(pageable);
        List<CustomerModel> customerModelList = toCustomerModels(customersPage);
        Page<CustomerModel> customerModelPageable = new PageImpl<>(customerModelList, pageable, customersPage.getTotalElements());

        return customerModelPageable;
    }

    private List<CustomerModel> toCustomerModels(Page<CustomerEntity> customersPage) {
        List<CustomerModel> customerModelList = new ArrayList<>();

        for (CustomerEntity customer : customersPage.getContent()) {
            customerModelList.add(toCustomerModel(customer));
        }
        return customerModelList;
    }

    private CustomerModel toCustomerModel(CustomerEntity customer) {
        return new CustomerModel(customer.getName(), customer.getEmail(), customer.getCpf());
    }

    public Optional<CustomerModel> findById(Long id) {

        Optional<CustomerModel> optionalCustomerModel = Optional.empty();

        Optional<CustomerEntity> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()) {
            CustomerModel customerModel = toCustomerModel(optionalCustomer.get());
            optionalCustomerModel = Optional.of(customerModel);
        }

        return optionalCustomerModel;
    }

    public Optional<CustomerModel> findByCpf(String cpf) {
        Optional<CustomerEntity> optionalCustomer = customerRepository.findByCpf(cpf);

        if (optionalCustomer.isPresent()) {
            CustomerModel customerModel = toCustomerModel(optionalCustomer.get());
            return Optional.of(customerModel);
        }else {
            throw new RuntimeException(String.format("CPF [%s] não foi encontrado", cpf));
        }
    }

    public void remove(Long id) {

        Optional<CustomerEntity> customer = customerRepository.findById(id);

        if (!customer.isPresent())
            throw new RuntimeException(String.format("ID de acesso [%d] não encontrado", id));

        customerRepository.deleteById(customer.get().getId());
    }

    public CustomerModel findOrElseById(Long id) {

        Optional<CustomerModel> optionalCustomerModel = findById(id);

        if (optionalCustomerModel.isEmpty())
            throw new RuntimeException("Customer not found");

        return optionalCustomerModel.get();

    }

    private CustomerEntity toDomainObject(CustomerInput customerInput) {
        return new CustomerEntity(
                null,
                customerInput.name(),
                customerInput.email(),
                customerInput.cpf()
        );
    }

    private CustomerEntity toDomainObject(Long id, CustomerUpdate customerUpdate) {

        Optional<CustomerEntity> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()) {

            CustomerEntity customer = optionalCustomer.get();

            if (!customerUpdate.name().isBlank()) {
                customer.setName(customerUpdate.name());
            }

            if (!customerUpdate.email().isBlank()) {
                customer.setEmail(customerUpdate.email());
            }
            return customer;
        }else {

            throw new RuntimeException("Customer not found");

        }

    }

    public CustomerModel save(CustomerInput customerInput) {
        CustomerEntity customer = toDomainObject(customerInput);
        CustomerModel customerModel = toCustomerModel(customer);
        save(customer);
        return customerModel;
    }

    public CustomerModel update(Long id, CustomerUpdate customerUpdate) {
        CustomerEntity customer = toDomainObject(id, customerUpdate);
        CustomerModel customerModel = toCustomerModel(customer);
        save(customer);
        return customerModel;
    }
}

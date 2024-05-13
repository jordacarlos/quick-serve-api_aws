package br.com.fiap.techchallenge.quickserveapi.application.adapters.output.repository;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.CustomerModelOutput;
import br.com.fiap.techchallenge.quickserveapi.domain.Customer;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.CustomerRepositoryPort;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.CustomerEntity;
import br.com.fiap.techchallenge.quickserveapi.infra.repositories.CustomerJPARepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerRepository implements CustomerRepositoryPort {
    private final CustomerJPARepository customerJPARepository;

    public CustomerRepository(CustomerJPARepository customerJPARepository) {
        this.customerJPARepository = customerJPARepository;
    }

    @Override
    public Customer save(Customer customer) {
        try {
            CustomerEntity customerEntity = new CustomerEntity(customer);
            return this.customerJPARepository.save(customerEntity).toCustomer();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "%s", ex);
        }
    }

    @Override
    public Customer findById(Long id) {
        try {
            Optional<CustomerEntity> customer = this.customerJPARepository.findById(id);
            return customer.orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Cliente [%d] não encontrado", id))
            ).toCustomer();
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro ao buscar o cliente", ex);
        }
    }


    @Override
    public Customer findByCpf(String cpf) {
        try {
            return customerJPARepository.findByCpf(cpf)
                    .map(CustomerEntity::toCustomer)
                    .orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Cliente com CPF [%s] não encontrado", cpf))
                    );
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro ao buscar o cliente");
        }
    }

    @Override
    public void deleteById(Long id) {
        try{
            this.customerJPARepository.deleteById(id);
        }catch (ResponseStatusException ex){
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro ao apagar o cliente");
        }
    }

    public Page<CustomerModelOutput> findAll(Pageable pageable) {
        try{
            Page<CustomerEntity> customersPage = this.customerJPARepository.findAll(pageable);
            return customersPage.map(this::toCustomerModelOutput);
        } catch (ResponseStatusException ex){
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro ao listar os clientes");
        }
    }

    private CustomerModelOutput toCustomerModelOutput(CustomerEntity customer) {
        return new CustomerModelOutput(customer.getId(), customer.getName(), customer.getEmail(), customer.getCpf());
    }
}

package br.com.fiap.techchallenge.quickserveapi.application.handler.controllers;

import br.com.fiap.techchallenge.quickserveapi.application.handler.adapters.CustomerAdapter;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.CustomerEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces.CustomerRepository;
import br.com.fiap.techchallenge.quickserveapi.application.handler.usecases.CustomerCase;

import java.util.List;
import java.util.Map;

public class CustomerController implements CustomerRepository {

    private final CustomerCase customerCase;

    public CustomerController(CustomerCase customerCase) {
        this.customerCase = customerCase;
    }

    public CustomerEntity save(CustomerEntity customer) {
        return customerCase.save(customer);
    }

    public String delete(Long id) {
        List<Map<String, Object>> deletedCustomer = customerCase.delete(id);
        return CustomerAdapter.mapToMensage(deletedCustomer);
    }

    public CustomerEntity findByCpf(String cpf){
        List<Map<String, Object>> findedCustomer = customerCase.findByCpf(cpf);
        return CustomerAdapter.mapToCustomerEntity(findedCustomer);
    }

    public CustomerEntity findById(Long id){
        List<Map<String, Object>> findedCustomer = customerCase.findById(id);
        return CustomerAdapter.mapToCustomerEntity(findedCustomer);
    }

    public List<CustomerEntity> findAll(){
        List<Map<String, Object>> findedCustomers = customerCase.findAll();
        return CustomerAdapter.mapToCustomerEntityList(findedCustomers);
    }
    public CustomerEntity update(Long id, CustomerEntity customerInput){
        return customerCase.update(id,customerInput);
    }
}

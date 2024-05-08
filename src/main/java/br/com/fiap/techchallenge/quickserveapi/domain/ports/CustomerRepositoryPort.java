package br.com.fiap.techchallenge.quickserveapi.domain.ports;

import br.com.fiap.techchallenge.quickserveapi.domain.Customer;

public interface CustomerRepositoryPort {
    Customer save(Customer customer);

    Customer finbdById(Long id);

    void deleteById(Long id);

}

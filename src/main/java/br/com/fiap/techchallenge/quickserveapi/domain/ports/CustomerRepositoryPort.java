package br.com.fiap.techchallenge.quickserveapi.domain.ports;

import br.com.fiap.techchallenge.quickserveapi.domain.Customer;

public interface CustomerRepositoryPort {
    Customer save(Customer customer);

    Customer findById(Long id);

    Customer findByCpf(String cpf);

    void deleteById(Long id);

}

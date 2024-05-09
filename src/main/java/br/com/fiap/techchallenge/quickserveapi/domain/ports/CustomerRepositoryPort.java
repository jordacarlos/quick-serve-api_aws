package br.com.fiap.techchallenge.quickserveapi.domain.ports;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.CustomerModelOutput;
import br.com.fiap.techchallenge.quickserveapi.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerRepositoryPort {
    Customer save(Customer customer);

    Page<CustomerModelOutput> findAll(Pageable pageable);

    Customer findById(Long id);

    Customer findByCpf(String cpf);

    void deleteById(Long id);

}

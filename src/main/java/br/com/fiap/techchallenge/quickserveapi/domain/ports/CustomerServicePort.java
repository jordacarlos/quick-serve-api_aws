package br.com.fiap.techchallenge.quickserveapi.domain.ports;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.CustomerInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.CustomerUpdate;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.CustomerModel;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.CustomerModelOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerServicePort {
    CustomerModel save(CustomerInput customerInput);

    void delete(Long customerId);

    Page<CustomerModelOutput> findAllWithId(Pageable pageable);

    Optional<CustomerModel> findById(Long id);

    Optional<CustomerModel> findByCpf(String cpf);

    void remove(Long id);

    CustomerModel findOrElseById(Long id);

    CustomerModel update(Long id, CustomerUpdate customerUpdate);
}

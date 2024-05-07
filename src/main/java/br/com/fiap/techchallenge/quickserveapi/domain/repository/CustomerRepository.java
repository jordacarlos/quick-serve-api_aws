package br.com.fiap.techchallenge.quickserveapi.domain.repository;

import br.com.fiap.techchallenge.quickserveapi.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCpf(String cpf);
}

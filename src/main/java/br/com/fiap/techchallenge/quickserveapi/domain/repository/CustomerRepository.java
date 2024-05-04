package br.com.fiap.techchallenge.quickserveapi.domain.repository;

import br.com.fiap.techchallenge.quickserveapi.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

package br.com.fiap.techchallenge.quickserveapi.infra.repositories;

import br.com.fiap.techchallenge.quickserveapi.infra.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJPARepository extends JpaRepository<OrderEntity, Long> {
}

package br.com.fiap.techchallenge.quickserveapi.infra.repositories;

import br.com.fiap.techchallenge.quickserveapi.infra.entities.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // Correct import


import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJPARepository extends JpaRepository<OrderEntity, Long> {
    Page<OrderEntity> findAll(Pageable pageable);
}

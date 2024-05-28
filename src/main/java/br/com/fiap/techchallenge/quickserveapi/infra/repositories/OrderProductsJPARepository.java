package br.com.fiap.techchallenge.quickserveapi.infra.repositories;

import br.com.fiap.techchallenge.quickserveapi.infra.entities.OrderProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductsJPARepository extends JpaRepository<OrderProductsEntity, Long>  {
}

package br.com.fiap.techchallenge.quickserveapi.infra.repositories;

import br.com.fiap.techchallenge.quickserveapi.infra.entities.OrderProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
public interface OrderProductsJPARepository extends JpaRepository<OrderProductsEntity, Long>  {
}

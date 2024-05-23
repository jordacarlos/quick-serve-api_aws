package br.com.fiap.techchallenge.quickserveapi.infra.repositories;

import br.com.fiap.techchallenge.quickserveapi.domain.enums.CategoryEnum;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJPARepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCategory(CategoryEnum category);

}

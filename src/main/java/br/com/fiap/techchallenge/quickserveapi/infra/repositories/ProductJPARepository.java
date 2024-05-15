package br.com.fiap.techchallenge.quickserveapi.infra.repositories;

import br.com.fiap.techchallenge.quickserveapi.domain.Product;
import br.com.fiap.techchallenge.quickserveapi.domain.enuns.CategoryEnum;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

public interface ProductJPARepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findAllByCategory(CategoryEnum category);

    FileChannel findByCategory(CategoryEnum category);
}

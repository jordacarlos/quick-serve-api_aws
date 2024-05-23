package br.com.fiap.techchallenge.quickserveapi.domain.ports;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.ProductModel;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.ProductModelOutput;
import br.com.fiap.techchallenge.quickserveapi.domain.Product;
import br.com.fiap.techchallenge.quickserveapi.domain.enums.CategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryPort {
    Product save(Product product);
    Page<ProductModelOutput> findAll(Pageable pageable);
    List<ProductModel> findByCategory(CategoryEnum category);
    void deleteById(Long id);
    Product findById(Long id);
}

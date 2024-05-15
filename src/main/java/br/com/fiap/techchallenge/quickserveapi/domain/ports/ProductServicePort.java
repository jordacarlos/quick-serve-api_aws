package br.com.fiap.techchallenge.quickserveapi.domain.ports;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.ProductInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.ProductUpdate;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.ProductModel;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.ProductModelOutput;
import br.com.fiap.techchallenge.quickserveapi.domain.enuns.CategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Optional;

public interface ProductServicePort {
    ProductModel save(ProductInput productInput);
    void delete(Long productId);
    Optional<ProductModel> findById(Long id);
    ProductModel update(Long id, ProductUpdate productUpdate);
    Page<ProductModelOutput> findAllWithId(Pageable pageable);
}





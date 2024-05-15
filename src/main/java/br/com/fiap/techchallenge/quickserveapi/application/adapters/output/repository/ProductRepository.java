package br.com.fiap.techchallenge.quickserveapi.application.adapters.output.repository;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.ProductModelOutput;
import br.com.fiap.techchallenge.quickserveapi.domain.Product;
import br.com.fiap.techchallenge.quickserveapi.domain.enuns.CategoryEnum;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.ProductRepositoryPort;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.CustomerEntity;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.ProductEntity;
import br.com.fiap.techchallenge.quickserveapi.infra.repositories.ProductJPARepository;
import org.hibernate.mapping.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductRepository implements ProductRepositoryPort {
    private final ProductJPARepository productJPARepository;

    public ProductRepository(ProductJPARepository productJPARepository) {
        this.productJPARepository = productJPARepository;
    }

    @Override
    public Product save(Product product) {
        try {
            ProductEntity ProductEntity = new ProductEntity(product);
            return this.productJPARepository.save(ProductEntity).toProduct();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "%s", ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        try{
            this.productJPARepository.deleteById(id);
        }catch (ResponseStatusException ex){
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro ao apagar o produto");
        }
    }

    @Override
    public Product findById(Long id) {
        try {
            Optional<ProductEntity> product = this.productJPARepository.findById(id);
            return product.orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Produto [%s] não encontrado", id))
            ).toProduct();
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro ao buscar o produto", ex);
        }
    }

    public Page<ProductModelOutput> findAll(Pageable pageable) {
        try{
            Page<ProductEntity> productsPage = this.productJPARepository.findAll(pageable);
            return productsPage.map(this::toProductModelOutput);
        } catch (ResponseStatusException ex){
            throw ex;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro ao listar os produtos");
        }
    }






    private ProductModelOutput toProductModelOutput(ProductEntity Product) {
        return new ProductModelOutput(Product.getId(), Product.getName(), Product.getCategory(),
                Product.getDescription(), Product.getImagePath());

    }
}

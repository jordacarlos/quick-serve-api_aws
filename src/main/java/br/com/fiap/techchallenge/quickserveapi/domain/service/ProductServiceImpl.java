package br.com.fiap.techchallenge.quickserveapi.domain.service;


import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.ProductInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.ProductModel;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.ProductModelOutput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.ProductUpdate;
import br.com.fiap.techchallenge.quickserveapi.domain.Product;
import br.com.fiap.techchallenge.quickserveapi.domain.enuns.CategoryEnum;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.ProductRepositoryPort;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.ProductServicePort;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.ProductEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Objects;
import java.util.Optional;

public class ProductServiceImpl implements ProductServicePort {

    private final ProductRepositoryPort productRepositoryPort;

    public ProductServiceImpl(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    public ProductModel save(ProductInput productInput) {
        Product product = new Product(productInput);
        try {
            return productRepositoryPort.save(product).toProductModel();
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(String.format("Integridade de dados violada -> %s", e.getMessage()));
        }
    }

    public Optional<ProductModel> findById(Long id) {
        Product product = productRepositoryPort.findById(id);
        return Optional.of(product.toProductModel());
    }

    public Page<ProductModelOutput> findAllWithId(Pageable paginacao) {
        return productRepositoryPort.findAll(paginacao);
    }

    public ProductModel findOrElseById(Long id) {
        Optional<ProductModel> optionalProductModel = findById(id);
        if (optionalProductModel.isEmpty())
            throw new RuntimeException("Product not found");
        return optionalProductModel.get();
    }

    public void delete(Long productId) {
        try {
            productRepositoryPort.deleteById(productId);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Product not found -> "+productId);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(
                    String.format("Código %d não pode ser removida, pois está em uso", productId));
        }
    }

    public ProductModel update(Long id, ProductUpdate productUpdate) {
        ProductEntity product = toDomainObject(id, productUpdate);
        ProductModel productModel = toProductModel(product);
        productRepositoryPort.save(product.toProduct());
        return productModel;
    }

    private ProductEntity toDomainObject(Long id, ProductUpdate productUpdate) {
        Product product = productRepositoryPort.findById(id);

        if (Objects.nonNull(product)) {
            ProductEntity productEntity = new ProductEntity(product);
            if (productUpdate.name() != null && !productUpdate.name().isBlank()){
                productEntity.setName(productUpdate.name());
            }
            if (!Double.isNaN(productUpdate.price())){
                productEntity.setPrice(productUpdate.price());
            }
            if (productUpdate.category() != null ){
                productEntity.setCategory(productUpdate.category());
            }
            if (productUpdate.description() != null && !productUpdate.description().isBlank() ){
                productEntity.setDescription(productUpdate.description());
            }
            if (productUpdate.imagePath() != null && !productUpdate.imagePath().isBlank() ){
                productEntity.setImagePath(productUpdate.imagePath());
            }
            return productEntity;
        }else {
            throw new RuntimeException("Product not found");
        }
    }

    private ProductModel toProductModel(ProductEntity product) {
        return new ProductModel(product.getName(), product.getCategory(),product.getPrice(),product.getDescription(),product.getImagePath());
    }
}

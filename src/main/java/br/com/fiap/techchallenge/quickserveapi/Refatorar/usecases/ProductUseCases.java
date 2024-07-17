package br.com.fiap.techchallenge.quickserveapi.Refatorar.usecases;

import br.com.fiap.techchallenge.quickserveapi.Refatorar.entities.CategoryEnum;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.entities.OrderEntity;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.entities.ProductEntity;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.interfaces.ProductRepository;

import java.util.List;

public class ProductUseCases implements ProductRepository {

    private final ProductRepository productRepository;

    public ProductUseCases(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductEntity save(ProductEntity product) {
        return productRepository.save(product);
    }


    public ProductEntity findById(Long id) {
        return productRepository.findById(id);
    }

    public List<ProductEntity> findByCategory(CategoryEnum category) {
        return productRepository.findByCategory(category);
    }
    public List<ProductEntity> findByOrder(OrderEntity order){
        return productRepository.findByOrder(order);
    }

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }
    public String delete(Long id) {
        return  productRepository.delete(id);
    }
    public ProductEntity update(Long id,ProductEntity customer) {
        return productRepository.update(id,customer);
    }
}

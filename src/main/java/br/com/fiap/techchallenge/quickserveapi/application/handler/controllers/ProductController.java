package br.com.fiap.techchallenge.quickserveapi.application.handler.controllers;

import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.CategoryEnum;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.OrderEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.ProductEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces.ProductRepository;
import br.com.fiap.techchallenge.quickserveapi.application.handler.usecases.ProductCase;

import java.util.List;

public class ProductController implements ProductRepository {

    private final ProductCase productCase;

    public ProductController(ProductCase productCase) {
        this.productCase = productCase;
    }

    public ProductEntity save(ProductEntity product){
        return productCase.save(product);
    }

    public ProductEntity findById(Long id){
        return productCase.findById(id);
    }

    public List<ProductEntity> findByCategory(CategoryEnum category){
        return productCase.findByCategory(category);
    }

    public List<ProductEntity> findAll(){
        return productCase.findAll();
    }

    @Override
    public List<ProductEntity> findByOrder(OrderEntity order) {
        return productCase.findByOrder(order);
    }

    public String delete(Long id){
        return productCase.delete(id);
    }

    public ProductEntity update(Long id,ProductEntity productInput){
        return productCase.update(id,productInput);
    }
}

package br.com.fiap.techchallenge.quickserveapi.domain;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.ProductInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.ProductModel;
import br.com.fiap.techchallenge.quickserveapi.application.handler.exception.CategoryNotFoundException;
import br.com.fiap.techchallenge.quickserveapi.domain.enums.CategoryEnum;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.OrderEntity;
import br.com.fiap.techchallenge.quickserveapi.infra.entities.ProductEntity;

import java.util.Objects;

public class Product {

    private Long id;
    private String name;
    private CategoryEnum category;
    private double price;
    private String description;
    private String imagePath;

    public Product(Long id, String name, CategoryEnum categoryEnum, double price, String description, String imagePath) {
        this.id = id;
        this.name = name;
        this.category = categoryEnum;
        this.price =price;
        this.description = description;
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryEnum getCategoryEnum(){
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription(){
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }


    public Product(ProductInput input) {

        CategoryEnum categoryEnum = CategoryEnum.getValidCategory(input.category().toString().toUpperCase());
        if (Objects.isNull(categoryEnum)){
            throw new CategoryNotFoundException(input.category() + " Não é uma categoria válida");
        }

        this.name = input.name();
        this.category = categoryEnum;
        this.price = input.price();
        this.description = input.description();
        this.imagePath = input.imagePath();
    }

    public Product(ProductEntity entity){
        this.name = entity.getName();
        this.category = entity.getCategory();
        this.price = entity.getPrice();
        this.description = entity.getDescription();
        this.imagePath = entity.getImagePath();
    }

    public ProductModel toProductModel() {
        return new ProductModel(this.name,this.category.getDescricao(), this.price, this.description, this.imagePath);
    }
}

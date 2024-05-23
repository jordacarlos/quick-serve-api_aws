package br.com.fiap.techchallenge.quickserveapi.domain;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.ProductInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.ProductModel;
import br.com.fiap.techchallenge.quickserveapi.application.handler.exception.CategoryNotFoundException;
import br.com.fiap.techchallenge.quickserveapi.domain.enums.CategoryEnum;

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

        CategoryEnum categoryEnum = CategoryEnum.getValidCategory(input.category().toUpperCase());
        if (Objects.isNull(categoryEnum)){
            throw new CategoryNotFoundException(input.category() + " Não é uma categoria válida");
        }

        this.name = input.name();
        this.category = categoryEnum;
        this.price = input.price();
        this.description = input.description();
        this.imagePath = input.imagePath();
    }

    public ProductModel toProductModel() {
        return new ProductModel(this.name,this.category.getDescricao(), this.price, this.description, this.imagePath);
    }
}

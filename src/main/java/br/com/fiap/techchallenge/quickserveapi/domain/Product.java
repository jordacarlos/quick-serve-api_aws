package br.com.fiap.techchallenge.quickserveapi.domain;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.ProductInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.ProductModel;
import br.com.fiap.techchallenge.quickserveapi.domain.enuns.CategoryEnum;

import java.util.Locale;

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
        this.name = input.name();
        this.category = input.category();
        this.price = input.price();
        this.description = input.description();
        this.imagePath = input.imagePath();
    }

    public ProductModel toProductModel() {
        return new ProductModel(this.name,this.category, this.price, this.description, this.imagePath);
    }
}

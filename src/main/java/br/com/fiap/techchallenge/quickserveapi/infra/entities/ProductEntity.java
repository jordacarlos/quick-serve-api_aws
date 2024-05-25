package br.com.fiap.techchallenge.quickserveapi.infra.entities;

import br.com.fiap.techchallenge.quickserveapi.domain.Product;
import br.com.fiap.techchallenge.quickserveapi.domain.enums.CategoryEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper=false)
@Entity(name = "products")
public class ProductEntity {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imagePath;

    public ProductEntity() {
    }

    public ProductEntity(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.category = product.getCategoryEnum();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.imagePath = product.getImagePath();
    }

    public Product  toProduct(){
        return new Product(this.id, this.name,this.category,this.price,this.description,this.imagePath);
    }
}
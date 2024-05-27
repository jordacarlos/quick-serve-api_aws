package br.com.fiap.techchallenge.quickserveapi.infra.entities;

import br.com.fiap.techchallenge.quickserveapi.domain.Order;
import br.com.fiap.techchallenge.quickserveapi.domain.OrderProducts;
import br.com.fiap.techchallenge.quickserveapi.domain.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper=false)
@Entity(name = "order_products")
public class OrderProductsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "product_quantity")
    private Integer productQuantity;


    public OrderProductsEntity(OrderEntity order, ProductEntity product, Integer productQuantity) {
        this.order = order;
        this.product = product;
        this.productQuantity = productQuantity;
    }

    public OrderProducts toOrderProducts(){
        return new OrderProducts(new Order(this.order) ,new Product(this.product), this.productQuantity);
    }
}

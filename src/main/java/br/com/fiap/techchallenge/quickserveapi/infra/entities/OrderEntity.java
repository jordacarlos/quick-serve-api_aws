package br.com.fiap.techchallenge.quickserveapi.infra.entities;


import br.com.fiap.techchallenge.quickserveapi.domain.Order;
import br.com.fiap.techchallenge.quickserveapi.domain.Product;
import br.com.fiap.techchallenge.quickserveapi.domain.enums.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper=false)
@Entity(name = "orders")
public class OrderEntity {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @Column(name = "customer_id")
    private Long customerID;

    @OneToMany(targetEntity = ProductEntity.class,cascade = CascadeType.ALL)
    @JoinColumn(name ="order_items",referencedColumnName = "id")
    private List<ProductEntity> orderItems;

    @Column(nullable = false)
    private Double totalOrderValue;

    public OrderEntity(Order order){
        this.id = order.getId();
        this.customerID = order.getCustomerID();
        this.status = order.getStatus();
        this.totalOrderValue = order.getTotalOrderValue();
    }

    public Order toOrder(){
        return new Order(this.id, this.customerID, this.status, this.totalOrderValue);
    }
}

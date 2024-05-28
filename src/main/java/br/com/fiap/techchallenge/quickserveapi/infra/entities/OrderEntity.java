package br.com.fiap.techchallenge.quickserveapi.infra.entities;


import br.com.fiap.techchallenge.quickserveapi.domain.Order;
import br.com.fiap.techchallenge.quickserveapi.domain.enums.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@Entity(name = "orders")
public class OrderEntity {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @Column(name = "customer_id")
    private Long customerID;

    @OneToMany(mappedBy = "order")
    private List<OrderProductsEntity> orderItems;

    @Column(nullable = false)
    private Double totalOrderValue;

    public OrderEntity(Order order) {
        this.id = order.getId();
        this.customerID = order.getCustomerID();
        this.status = order.getStatus();
        this.orderItems = order.getOrderItems().stream().map(product ->
                new OrderProductsEntity(null,new ProductEntity(product.getProduct()), product.getProductQuantity())).toList();
        this.totalOrderValue = order.getTotalOrderValue();
    }

    public Order toOrder() {
        return new Order(this.id, this.customerID, this.status, this.orderItems.stream().map(OrderProductsEntity::toOrderProducts).toList(), this.totalOrderValue);
    }

    public Order toOrderToUpdateStatus() {
        return new Order(this.id, this.customerID, this.status, this.totalOrderValue);
    }
}

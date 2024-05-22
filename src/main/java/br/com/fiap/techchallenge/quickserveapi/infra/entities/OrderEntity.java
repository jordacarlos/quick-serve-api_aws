package br.com.fiap.techchallenge.quickserveapi.infra.entities;


import br.com.fiap.techchallenge.quickserveapi.domain.Orders;
import br.com.fiap.techchallenge.quickserveapi.domain.enuns.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @Column(nullable = false)
    private String name;

    public OrderEntity(){

    }

    public OrderEntity(Orders orders){
        this.id = orders.getId();
        this.name = orders.getName();
        this.status = orders.getStatus();
    }

    public Orders toOrder(){
        return new Orders(this.id, this.name, this.status);
    }
}

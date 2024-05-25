package br.com.fiap.techchallenge.quickserveapi.infra.entities;

import br.com.fiap.techchallenge.quickserveapi.domain.Customer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper=false)
@Entity(name = "customers")
public class CustomerEntity {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String cpf;

    public CustomerEntity() {
    }

    public CustomerEntity(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.cpf = customer.getCpf();
    }

    public Customer  toCustomer(){
        return new Customer(this.id, this.name, this.email, this.cpf);
    }
}
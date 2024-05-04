package br.com.fiap.techchallenge.quickserveapi.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper=false)
@Entity(name = "customer")
public class Customer {

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

    public Customer() {}

    public Customer(String name, String email, String cpf) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
    }
}
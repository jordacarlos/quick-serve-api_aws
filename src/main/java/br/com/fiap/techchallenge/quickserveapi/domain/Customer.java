package br.com.fiap.techchallenge.quickserveapi.domain;

import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request.CustomerInput;
import br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response.CustomerModelOutput;

public class Customer {

    private Long id;
    private String name;
    private String email;
    private String cpf;

    public Customer(Long id, String name, String email, String cpf) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public String getCpf() {
        return cpf;
    }

    public Customer(CustomerInput input) {
        this.name = input.name();
        this.email = input.email();
        this.cpf = input.cpf();
    }

    public CustomerModelOutput toCustomerModelOutput(){
        return new CustomerModelOutput(this.id, this.name, this.email, this.cpf);

    }

}

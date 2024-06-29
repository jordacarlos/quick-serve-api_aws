package br.com.fiap.techchallenge.quickserveapi.Refatorar.entities;

public class CustomerEntity {
    private Long id;
    private String name;
    private String email;
    private String cpf;

    public CustomerEntity(Long id, String name, String email, String cpf) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
    }
    public Long getId() {
        return id;
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

    public void setId(long id) {
        this.id = id;
    }
}

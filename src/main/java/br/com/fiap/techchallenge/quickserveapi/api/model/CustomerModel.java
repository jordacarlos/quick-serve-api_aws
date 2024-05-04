package br.com.fiap.techchallenge.quickserveapi.api.model;

import jakarta.validation.constraints.NotBlank;

public record CustomerModel (
    @NotBlank
    String name,
    @NotBlank
    String email,

    @NotBlank
    String cpf

){}

package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response;

import jakarta.validation.constraints.NotBlank;

public record CustomerModel (
    @NotBlank
    String name,
    @NotBlank
    String email,

    @NotBlank
    String cpf

){}

package br.com.fiap.techchallenge.quickserveapi.api.model.input;

import jakarta.validation.constraints.NotBlank;

public record CustomerInput (
        @NotBlank
        String name,
        @NotBlank
        String email,

        @NotBlank
        String cpf
){}
package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerInput (
        @NotBlank(message = "O Email não pode estar em branco")
        String name,

        @NotBlank(message = "O Email não pode estar em branco")
        @Email
        String email,

        @NotBlank(message = "O CPF não pode estar em branco")
        @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}")
        String cpf
){}
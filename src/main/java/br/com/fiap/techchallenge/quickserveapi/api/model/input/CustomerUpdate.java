package br.com.fiap.techchallenge.quickserveapi.api.model.input;

public record CustomerUpdate(
        String name,
        String email,

        String cpf
){}
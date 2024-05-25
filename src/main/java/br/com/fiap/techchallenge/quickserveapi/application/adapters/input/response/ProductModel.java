package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response;

import jakarta.validation.constraints.NotBlank;

public record ProductModel(
    @NotBlank
    String name,
    @NotBlank
    String categoryCategory,

    @NotBlank
    Double price,

    @NotBlank
    String description,

    @NotBlank
    String imagePath
){}
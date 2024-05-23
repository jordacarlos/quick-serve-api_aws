package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response;

import jakarta.validation.constraints.NotBlank;

public record ProductModelOutput(
    Long id,
    @NotBlank
    String name,
    @NotBlank
    String categoryEnum,
    @NotBlank
    String description,

    @NotBlank
    String imagePath

){}
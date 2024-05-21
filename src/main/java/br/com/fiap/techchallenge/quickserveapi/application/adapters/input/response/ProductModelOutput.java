package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.response;

import br.com.fiap.techchallenge.quickserveapi.domain.enuns.CategoryEnum;
import jakarta.validation.constraints.NotBlank;

public record ProductModelOutput(
    Long id,
    @NotBlank
    String name,
    @NotBlank
    CategoryEnum categoryEnum,
    @NotBlank
    String description,

    @NotBlank
    String imagePath

){}
package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request;

import br.com.fiap.techchallenge.quickserveapi.domain.enuns.CategoryEnum;

public record ProductUpdate(
        String name,
        double price,
        CategoryEnum category,
        String description,
        String imagePath
){}
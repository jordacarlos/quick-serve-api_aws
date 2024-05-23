package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request;

public record ProductUpdate(
        String name,
        double price,
        String category,
        String description,
        String imagePath
){}
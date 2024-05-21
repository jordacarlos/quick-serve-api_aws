package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request;

import br.com.fiap.techchallenge.quickserveapi.domain.enuns.CategoryEnum;

public record ProductInput(String name, CategoryEnum category, double price, String description, String imagePath) {
}

package br.com.fiap.techchallenge.quickserveapi.application.adapters.input.request;

public record ProductInput(String name, String category, double price, String description, String imagePath) {
}

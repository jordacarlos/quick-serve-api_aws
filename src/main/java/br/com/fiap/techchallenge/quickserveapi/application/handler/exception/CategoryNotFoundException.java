package br.com.fiap.techchallenge.quickserveapi.application.handler.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

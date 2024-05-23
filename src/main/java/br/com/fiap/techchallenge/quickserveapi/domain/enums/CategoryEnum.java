package br.com.fiap.techchallenge.quickserveapi.domain.enums;

public enum CategoryEnum {
    LANCHE("Lanche"),
    ACOMPANHAMENTO("Acompanhamento"),
    BEBIDA("Bebida"),
    SOBREMESA("Sobremesa");

    CategoryEnum(String descricao) {
        this.descricao = descricao;
    }

    private String descricao;

    public static CategoryEnum getValidCategory(String category) {
        for (CategoryEnum e : values()) {
            if (e.toString().equals(category)) {
                return e;
            }
        }
        return null;
    }

    public String getDescricao() {
        return descricao;
    }
}

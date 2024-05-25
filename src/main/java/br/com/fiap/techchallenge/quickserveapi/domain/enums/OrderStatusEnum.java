package br.com.fiap.techchallenge.quickserveapi.domain.enums;

public enum OrderStatusEnum {
    RECEBIDO("Recebido"),
    EM_PREPARACAO("Em preparacao"),
    PRONTO("Pronto"),
    FINALIZADO("Finalizado");

    private String descricao;

    OrderStatusEnum(String descricao) {
        this.descricao = descricao;
    }

    public static OrderStatusEnum getValidOrderStatus(String status) {
        for (OrderStatusEnum e : values()) {
            if (e.toString().equals(status)) {
                return e;
            }
        }
        return null;
    }

    public String getDescricao() {
        return descricao;
    }
}

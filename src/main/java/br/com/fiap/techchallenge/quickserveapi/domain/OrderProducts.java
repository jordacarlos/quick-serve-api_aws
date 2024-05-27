package br.com.fiap.techchallenge.quickserveapi.domain;

public class OrderProducts {
    private Order order;
    private Product product;
    private Integer productQuantity;

    public OrderProducts(Order order, Product product, Integer productQuantity) {
        this.order = order;
        this.product = product;
        this.productQuantity = productQuantity;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

}

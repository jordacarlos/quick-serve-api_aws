package br.com.fiap.techchallenge.quickserveapi.Refatorar.adapters;

import br.com.fiap.techchallenge.quickserveapi.Refatorar.entities.OrderEntity;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.entities.OrderStatusEnum;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.entities.ProductEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderAdapter {

    public static OrderEntity mapSingleToOrderEntity(Map<String, Object> row) {
        if (row == null) {
            throw new RuntimeException("Ordem não encontrada");
        }
        OrderStatusEnum status = OrderStatusEnum.valueOf((String) row.get("status"));

        return new  OrderEntity(
                (Long) row.get("order_id"),
                (String) row.get("customer_id"),
                status,
                null,
                (Double) row.get("total_order_value")
        );
    }
    // Long id, Long customerID,OrderStatusEnum status, List<ProductEntity> orderItems, Double totalOrderValue

    public static OrderEntity mapToOrderEntityEntity(List<Map<String, Object>> results) {
        if (results == null || results.isEmpty()) {
            throw new RuntimeException("Ordem não encontrada");
        }
        Map<String, Object> row = results.get(0);
        return mapSingleToOrderEntity(row);
    }

    public static List<OrderEntity> mapToOrderEntityList(List<Map<String, Object>> results) {
        if (results == null || results.isEmpty()) {
            throw new RuntimeException("Ordem não encontrada");
        }
        List<OrderEntity> orders = new ArrayList<>();
        for (Map<String, Object> row : results) {
            orders.add(mapSingleToOrderEntity(row));
        }
        return orders;
    }

    public static String mapToMessage(Map<String, Object> row) {
        if (row == null) {
            throw new RuntimeException("Ordem não encontrada");
        }
        return (String) row.get("Mensagem");
    }

    public static String mapToMessage(List<Map<String, Object>> results) {
        if (results == null || results.isEmpty()) {
            throw new RuntimeException("Ordem não encontrada");
        }
        Map<String, Object> row = results.get(0);
        return mapToMessage(row);
    }
}

package br.com.fiap.techchallenge.quickserveapi.Refatorar.adapters;

import br.com.fiap.techchallenge.quickserveapi.Refatorar.entities.CategoryEnum;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.entities.ProductEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductAdapter {

    public static ProductEntity mapSingleToProductEntity(Map<String, Object> row) {
        if (row == null) {
            throw new RuntimeException("Produto não encontrado");
        }
        CategoryEnum category = CategoryEnum.valueOf((String) row.get("category"));

        return new ProductEntity(
                (Long) row.get("product_id"),
                (String) row.get("name"),
                category,
                (Double) row.get("price"),
                (String) row.get("description"),
                (String) row.get("image_path")
        );
    }

    public static ProductEntity mapToProductEntity(List<Map<String, Object>> results) {
        if (results == null || results.isEmpty()) {
            throw new RuntimeException("Produto não encontrado");
        }
        Map<String, Object> row = results.get(0);
        return mapSingleToProductEntity(row);
    }

    public static List<ProductEntity> mapToProductEntityList(List<Map<String, Object>> results) {
        if (results == null || results.isEmpty()) {
            throw new RuntimeException("Produto não encontrado");
        }
        List<ProductEntity> products = new ArrayList<>();
        for (Map<String, Object> row : results) {
            products.add(mapSingleToProductEntity(row));
        }
        return products;
    }

    public static String mapToMensage(Map<String, Object> row) {
        if (row == null) {
            throw new RuntimeException("Produto não encontrado");
        }
        return (String) row.get("Mensagem");
    }

    public static String mapToMensage(List<Map<String, Object>> results) {
        if (results == null || results.isEmpty()) {
            throw new RuntimeException("Produto não encontrado");
        }
        Map<String, Object> row = results.get(0);
        return mapToMensage(row);
    }
}
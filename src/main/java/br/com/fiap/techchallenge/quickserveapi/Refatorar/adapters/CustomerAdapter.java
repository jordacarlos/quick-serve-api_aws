package br.com.fiap.techchallenge.quickserveapi.Refatorar.adapters;

import br.com.fiap.techchallenge.quickserveapi.Refatorar.entities.CustomerEntity;
import org.flywaydb.core.api.callback.Warning;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerAdapter {

    public static CustomerEntity mapSingleToCustomerEntity(Map<String, Object> row) {
        if (row == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        return new CustomerEntity(
                (Long) row.get("id"),
                (String) row.get("name"),
                (String) row.get("email"),
                (String) row.get("cpf")
        );
    }

    public static CustomerEntity mapToCustomerEntity(List<Map<String, Object>> results) {
        if (results == null || results.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }
        Map<String, Object> row = results.get(0);
        return mapSingleToCustomerEntity(row);
    }

    public static List<CustomerEntity> mapToCustomerEntityList(List<Map<String, Object>> results) {
        if (results == null || results.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }
        List<CustomerEntity> customers = new ArrayList<>();
        for (Map<String, Object> row : results) {
            customers.add(mapSingleToCustomerEntity(row));
        }
        return customers;
    }

    public static String mapToMensage(Map<String, Object> row) {
        if (row == null) {
            throw new RuntimeException("Usuário não encontrado");
        }
        return (String) row.get("Mensagem");
    }

    public static String mapToMensage(List<Map<String, Object>> results) {
        if (results == null || results.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }
        Map<String, Object> row = results.get(0);
        return mapToMensage(row);
    }




}
package br.com.fiap.techchallenge.quickserveapi.application.handler.gateway;

import br.com.fiap.techchallenge.quickserveapi.application.handler.external.DatabaseConnection;
import br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces.ParametroBd;

import java.util.List;
import java.util.Map;

public class ProductGateway {

    final DatabaseConnection database;

    public ProductGateway(DatabaseConnection database) {
        this.database = database;
    }

    public List<Map<String, Object>> insert(String tabela, String[] campos, ParametroBd[] parametros) {
        List<Map<String, Object>> result = database.Inserir(tabela, campos, parametros);
        return result;
    }
    public List<Map<String, Object>> find(String tabela, String[] campos, ParametroBd[] parametros) {
        List<Map<String, Object>> result = database.buscarPorParametros(tabela, campos, parametros);
        return result;
    }
    public List<Map<String, Object>> delete(String tabela, String[] campos, ParametroBd[] parametros) {
        List<Map<String, Object>> result = database.deletar(tabela, campos, parametros);
        return result;
    }

    public List<Map<String, Object>> update(String tabela, String[] campos, ParametroBd[] parametros) {
        List<Map<String, Object>> result = database.Update(tabela, campos, parametros);
        return result;
    }
}

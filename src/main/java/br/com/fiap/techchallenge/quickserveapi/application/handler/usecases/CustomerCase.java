package br.com.fiap.techchallenge.quickserveapi.application.handler.usecases;

import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.CustomerEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.gateway.CustomerGateway;
import br.com.fiap.techchallenge.quickserveapi.application.handler.gateway.Gateway;
import br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces.ParametroBd;

import java.util.List;
import java.util.Map;

public class CustomerCase {

    private final Gateway gateway;

    public CustomerCase(Gateway gateway) {
        this.gateway = gateway;
    }

    public CustomerEntity save(CustomerEntity customer) {
        ParametroBd[] parametros = new ParametroBd[] {
                new ParametroBd("name", customer.getName()),
                new ParametroBd("email", customer.getEmail()),
                new ParametroBd("cpf", customer.getCpf())
        };

        String[] campos = {"name", "email", "cpf"};
        String tabela = "customers";

        List<Map<String, Object>> result = gateway.insert(tabela, campos, parametros);

        if (result != null && !result.isEmpty()) {
            Map<String, Object> row = result.get(0);
            if (row.containsKey("id")) {
                customer.setId(Long.parseLong(row.get("id").toString()));
            }
        }
        return customer;
    }

    public List<Map<String, Object>> delete(Long id){
        ParametroBd[] parametros = new ParametroBd[] {
                new ParametroBd("id", id)
        };

        // Chamada ao método Inserir do database com os parâmetros necessários
        String[] campos = {"id"};
        String tabela = "customers";
        List<Map<String, Object>> mensagem = gateway.delete(tabela, campos, parametros);
        return mensagem;
    }

    public List<Map<String, Object>> findByCpf(String cpf){
        ParametroBd[] parametros = { new ParametroBd("CPF", cpf) };
        List<Map<String, Object>> resultados = gateway.find("customers", new String[]{"id", "name", "email", "cpf"}, parametros);
        return resultados;
    }

    public List<Map<String, Object>> findById(Long id){
        ParametroBd[] parametros = { new ParametroBd("id", id) };
        List<Map<String, Object>> resultados = gateway.find("customers", new String[]{"id", "name", "email", "cpf"}, parametros);
        return resultados;
    }

    public List<Map<String, Object>> findAll(){
        ParametroBd[] parametros = { };
        List<Map<String, Object>> resultados = gateway.find("customers", new String[]{"id", "name", "email", "cpf"}, parametros);

        return resultados;
    }

    public CustomerEntity update(Long id,CustomerEntity customer){
        ParametroBd[] parametros = {
                new ParametroBd("name", customer.getName()),
                new ParametroBd("email", customer.getEmail()),
                new ParametroBd("cpf", customer.getCpf()),
                new ParametroBd("id", id)};

        // Chamada ao método Inserir do database com os parâmetros necessários
        String[] campos = {"name",  "email", "cpf"};

        String tabela = "customers";
        List<Map<String, Object>> mensagem = gateway.update(tabela, campos, parametros);

        return customer;
    }
}

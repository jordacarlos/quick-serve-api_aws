package br.com.fiap.techchallenge.quickserveapi.Refatorar.controllers;

import br.com.fiap.techchallenge.quickserveapi.Refatorar.adapters.CustomerAdapter;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.entities.CustomerEntity;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.interfaces.CustomerRepository;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.external.DatabaseConnection;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.interfaces.ParametroBd;
import org.springframework.boot.configurationprocessor.json.JSONArray;

import java.util.List;
import java.util.Map;

public class CustomerRepositoryImpl implements CustomerRepository {

    private final DatabaseConnection database;

    public CustomerRepositoryImpl(DatabaseConnection database) {
        this.database = database;
    }

    @Override
    public CustomerEntity save(CustomerEntity customer) {
        ParametroBd[] parametros = new ParametroBd[] {
                new ParametroBd("name", customer.getName()),
                new ParametroBd("email", customer.getEmail()),
                new ParametroBd("cpf", customer.getCpf())
        };

        // Chamada ao método Inserir do database com os parâmetros necessários
        String[] campos = {"name", "email", "cpf"};
        String tabela = "customers";
        List<Map<String, Object>> result  = database.Inserir(tabela, campos, parametros);
        // Configurar o ID no CustomerEntity
        if (result != null && !result.isEmpty()) {
            Map<String, Object> row = result.get(0);
            if (row.containsKey("id")) {
                customer.setId(Long.parseLong(row.get("id").toString()));
            }
        }

        return customer;
    }



    @Override
    public CustomerEntity findById(Long id) {
        ParametroBd[] parametros = { new ParametroBd("id", id) };
        List<Map<String, Object>> resultados = database.buscarPorParametros("customers", new String[]{"id", "name", "email", "cpf"}, parametros);

        // Utiliza o adapter para mapear os resultados para CustomerEntity
        return CustomerAdapter.mapToCustomerEntity(resultados);
    }


    @Override
    public CustomerEntity findByCpf(String cpf) {
        ParametroBd[] parametros = { new ParametroBd("CPF", cpf) };
        List<Map<String, Object>> resultados = database.buscarPorParametros("customers", new String[]{"id", "name", "email", "cpf"}, parametros);

        // Utiliza o adapter para mapear os resultados para CustomerEntity
        return CustomerAdapter.mapToCustomerEntity(resultados);
    }

    @Override
    public List<CustomerEntity> findAll() {
        ParametroBd[] parametros = { };
        List<Map<String, Object>> resultados = database.buscarPorParametros("customers", new String[]{"id", "name", "email", "cpf"}, parametros);

        // Utiliza o adapter para mapear os resultados para CustomerEntity
        return CustomerAdapter.mapToCustomerEntityList(resultados);
    }



    @Override
    public CustomerEntity update(Long id,CustomerEntity customer) {
        ParametroBd[] parametros = {
                new ParametroBd("name", customer.getName()),
                new ParametroBd("email", customer.getEmail()),
                new ParametroBd("cpf", customer.getCpf()),
                new ParametroBd("id", id)};

        // Chamada ao método Inserir do database com os parâmetros necessários
        String[] campos = {"name",  "email", "cpf"};

        String tabela = "customers";
        List<Map<String, Object>> mensagem = database.Update(tabela, campos, parametros);

        System.out.println(mensagem);
        return customer;
    }

    @Override
    public String delete(Long id) {
        ParametroBd[] parametros = new ParametroBd[] {
                new ParametroBd("id", id)
        };

        // Chamada ao método Inserir do database com os parâmetros necessários
        String[] campos = {"id"};
        String tabela = "customers";
        List<Map<String, Object>> mensagem = database.deletar(tabela, campos, parametros);
        return CustomerAdapter.mapToMensage(mensagem);
    }

}
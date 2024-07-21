package br.com.fiap.techchallenge.quickserveapi.application.handler.controllers;

import br.com.fiap.techchallenge.quickserveapi.application.handler.adapters.OrderAdapter;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.OrderEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.ProductEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.external.DatabaseConnection;
import br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces.ParametroBd;
import br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces.OrderRepository;


import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class OrderRepositoryImpl implements OrderRepository {

    private final DatabaseConnection database;

    public OrderRepositoryImpl(DatabaseConnection database){
        this.database = database;
    }

    @Override
    public OrderEntity save(OrderEntity orderEntity){
        ParametroBd[] parametros = new ParametroBd[]{
                new ParametroBd("status", orderEntity.getStatus().toString()),
                new ParametroBd("customer_id", orderEntity.getCustomerID()),
                new ParametroBd("total_order_value", orderEntity.getTotalOrderValue())
        };

        // Chamada ao método Inserir do database com os parâmetros necessários
        String[] campos = {"status", "customer_id", "total_order_value"};
        String tabela = "orders";
        List<Map<String, Object>> result  = database.Inserir(tabela, campos, parametros);
        // Configurar o ID no OrderEntity
        if (result != null && !result.isEmpty()) {
            Map<String, Object> row = result.get(0);
            if (row.containsKey("id")) {
                orderEntity.setId(Long.parseLong(row.get("id").toString()));
            }
        }

        // insert da tabela orderproduct
        HashSet<Long> distinctProductId = new HashSet<Long>();
        orderEntity.getOrderItems().forEach(product -> {
            distinctProductId.add(product.getId());
        });



        distinctProductId.forEach(product -> {
            ParametroBd[] parametrosOrderProduct = new ParametroBd[]{

                    new ParametroBd("order_id", orderEntity.getId()),
                    new ParametroBd("product_id", product),
                    new ParametroBd("product_quantity", orderEntity.getOrderItems().stream().filter(p -> p.getId().equals(product)).count())
            };
            // Chamada ao método Inserir do database com os parâmetros necessários
            String[] camposOrderProduct = {"order_id", "product_id", "product_quantity"};
            String tabelaOrderProduct = "order_products";
            database.Inserir(tabelaOrderProduct, camposOrderProduct, parametrosOrderProduct);

        });

        return orderEntity;
    }


    @Override
    public OrderEntity findById(Long id){
        ParametroBd[] parametros = { new ParametroBd("order_id", id) };
        List<Map<String, Object>> resultados = database.buscarPorParametros("orders", new String[]{"order_id", "status", "customer_id", "total_order_value"}, parametros);
        OrderEntity order  = OrderAdapter.mapToOrderEntityEntity(resultados);
        ProductRepositoryImpl productRepository = new ProductRepositoryImpl(this.database);
        order.setOrderItems(productRepository.findByOrder(order));
        return order;
    }

    @Override
    public OrderEntity updateStatus(OrderEntity order){
        ParametroBd[] parametros = {
                new ParametroBd("status", order.getStatus().toString()),
                new ParametroBd("order_id", order.getId()),
        };

        String[] campos = {"status"};

        String tabela = "orders";
        List<Map<String, Object>> mensagem = database.Update(tabela, campos, parametros);

        System.out.println(mensagem);
        return order;
    }

    @Override
    public List<OrderEntity> findAll() {
        ParametroBd[] parametros = {};
        List<Map<String, Object>> resultados = database.buscarPorParametros("orders", new String[]{"order_id", "status", "customer_id", "total_order_value"}, parametros);

        ProductRepositoryImpl productRepository = new ProductRepositoryImpl(this.database);

        List<OrderEntity> orders = OrderAdapter.mapToOrderEntityList(resultados);
        orders.forEach(item -> {
            item.setOrderItems(productRepository.findByOrder(item));
        });

        // Utiliza o adapter para mapear os resultados para ProductEntity
        return orders;
    }
}

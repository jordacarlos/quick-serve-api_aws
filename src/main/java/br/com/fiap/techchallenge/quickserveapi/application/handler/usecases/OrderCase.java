package br.com.fiap.techchallenge.quickserveapi.application.handler.usecases;

import br.com.fiap.techchallenge.quickserveapi.application.handler.adapters.OrderAdapter;
import br.com.fiap.techchallenge.quickserveapi.application.handler.controllers.ProductRepositoryImpl;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.OrderEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.gateway.Gateway;
import br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces.ParametroBd;
import org.springframework.boot.configurationprocessor.json.JSONArray;

import java.util.*;

public class OrderCase {

    private final Gateway gateway;

    public OrderCase(Gateway gateway) {
        this.gateway = gateway;
    }

    public OrderEntity save(OrderEntity orderEntity) {
        ParametroBd[] parametros = new ParametroBd[]{
                new ParametroBd("status", orderEntity.getStatus().toString()),
                new ParametroBd("customer_id", orderEntity.getCustomerID()),
                new ParametroBd("payment_status", orderEntity.getPaymentStatus().toString()),
                new ParametroBd("total_order_value", orderEntity.getTotalOrderValue())
        };
        // Chamada ao método Inserir do database com os parâmetros necessários
        String[] campos = {"status", "customer_id","payment_status", "total_order_value", };
        String tabela = "orders";
        List<Map<String, Object>> result = gateway.insert(tabela, campos, parametros);
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
            gateway.insert(tabelaOrderProduct, camposOrderProduct, parametrosOrderProduct);

        });

        return orderEntity;
    }

    public OrderEntity findById(Long id) {
        ParametroBd[] parametros = {new ParametroBd("order_id", id)};
        List<Map<String, Object>> resultados = gateway.find("orders", new String[]{"order_id", "status", "customer_id", "total_order_value", "payment_status"}, parametros);
        OrderEntity order = OrderAdapter.mapToOrderEntityEntity(resultados);
        ProductCase productCase = new ProductCase(this.gateway);
        order.setOrderItems(productCase.findByOrder(order));
        return order;
    }

    public OrderEntity updateStatus(OrderEntity order) {
        ParametroBd[] parametros = {
                new ParametroBd("status", order.getStatus().toString()),
                new ParametroBd("order_id", order.getId()),
        };

        String[] campos = {"status"};

        String tabela = "orders";
        List<Map<String, Object>> mensagem = gateway.update(tabela, campos, parametros);

        System.out.println(mensagem);
        return order;
    }

    public List<OrderEntity> findAll() {
        ParametroBd[] parametros = {};
        List<Map<String, Object>> resultados = gateway.find("orders", new String[]{"order_id", "status", "customer_id", "total_order_value", "payment_status"}, parametros);

        ProductCase productCase = new ProductCase(this.gateway);

        List<OrderEntity> orders = OrderAdapter.mapToOrderEntityList(resultados);
        orders.forEach(item -> {
            item.setOrderItems(productCase.findByOrder(item));
        });

        // Utiliza o adapter para mapear os resultados para ProductEntity
        return orders;
    }

    public String checkPaymentStatus(Long id){
        ParametroBd[] parametros = { new ParametroBd("order_id", id) };
        String resultados = gateway.find("orders", new String[]{"payment_status"}, parametros).toString();

        return OrderAdapter.mapToJson(resultados);
    }

    public List<OrderEntity> listByFilters() {
        String tabela = "orders";
        String[] campos = {"order_id", "status", "customer_id", "total_order_value", "payment_status"};
        ParametroBd[] parametros = {new ParametroBd("status", "FINALIZADO")}; // Exemplo de parâmetro para cláusula WHERE
        String[] filtros = {}; // Nenhum filtro adicional para ordenação padrão

        // Mapeamento do case para a ordenação
        Map<String, Integer> caseFiltros = new HashMap<>();
        caseFiltros.put("PRONTO", 1);
        caseFiltros.put("EM_PREPARACAO", 2);
        caseFiltros.put("RECEBIDO", 3);

        List<Map<String, Object>> resultados = gateway.findByFilters(tabela, campos, parametros, filtros, caseFiltros, "order_id DESC");

        ProductCase productCase = new ProductCase(this.gateway);

        List<OrderEntity> orders = OrderAdapter.mapToOrderEntityList(resultados);
        orders.forEach(item -> {
            item.setOrderItems(productCase.findByOrder(item));
        });

        // Utiliza o adapter para mapear os resultados para ProductEntity
        return orders;
    }





}

package br.com.fiap.techchallenge.quickserveapi.application.handler.controllers;

import br.com.fiap.techchallenge.quickserveapi.application.handler.adapters.ProductAdapter;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.CategoryEnum;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.OrderEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.ProductEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.external.DatabaseConnection;
import br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces.ParametroBd;
import br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductRepositoryImpl implements ProductRepository {

    private final DatabaseConnection database;

    public ProductRepositoryImpl(DatabaseConnection database) {
        this.database = database;
    }

    @Override
    public ProductEntity save(ProductEntity product) {
        ParametroBd[] parametros = new ParametroBd[]{
                new ParametroBd("name", product.getName()),
                new ParametroBd("category", product.getCategory().toString()),
                new ParametroBd("price", product.getPrice()),
                new ParametroBd("description", product.getDescription()),
                new ParametroBd("image_path", product.getImagePath())
        };

        // Chamada ao método Inserir do database com os parâmetros necessários
        String[] campos = {"name", "category", "price", "description", "image_path"};
        String tabela = "products";
        List<Map<String, Object>> result  = database.Inserir(tabela, campos, parametros);
        // Configurar o ID no ProductEntity
        if (result != null && !result.isEmpty()) {
            Map<String, Object> row = result.get(0);
            if (row.containsKey("id")) {
                product.setId(Long.parseLong(row.get("id").toString()));
            }
        }

        return product;
    }


    @Override
    public ProductEntity findById(Long id) {
        ParametroBd[] parametros = { new ParametroBd("product_id", id) };
        List<Map<String, Object>> resultados = database.buscarPorParametros("products", new String[]{"product_id", "name", "category", "price","description","image_path"}, parametros);

        // Utiliza o adapter para mapear os resultados para ProductEntity
        return ProductAdapter.mapToProductEntity(resultados);
    }


    @Override
    public List<ProductEntity> findByCategory(CategoryEnum category) {
        ParametroBd[] parametros = { new ParametroBd("category", category.name()) };
        List<Map<String, Object>> resultados = database.buscarPorParametros("products", new String[]{"product_id", "name", "category", "price","description","image_path"}, parametros);

        return ProductAdapter.mapToProductEntityList(resultados);

    }

    @Override
    public List<ProductEntity> findAll() {
        ParametroBd[] parametros = { };
        List<Map<String, Object>> resultados = database.buscarPorParametros("products", new String[]{"product_id", "name", "category", "price","description","image_path"}, parametros);

        // Utiliza o adapter para mapear os resultados para ProductEntity
        return ProductAdapter.mapToProductEntityList(resultados);
    }

    @Override
    public List<ProductEntity> findByOrder(OrderEntity order){
        ParametroBd[] parametros = { new ParametroBd("order_id", order.getId()) };
        List<Map<String, Object>> resultados = database.buscarPorParametros("order_products", new String[]{"id", "order_id", "product_id", "product_quantity"}, parametros);

        List<ProductEntity> productEntityList = new ArrayList<>();

        resultados.forEach(row ->{
            productEntityList.add(findById(
                    ((Number) row.get("product_id")).longValue()
            ));
        });

        return productEntityList;
    }

    @Override
    public String delete(Long id) {
        ParametroBd[] parametros = new ParametroBd[] {
                new ParametroBd("product_id", id)
        };

        // Chamada ao método Inserir do database com os parâmetros necessários
        String[] campos = {"product_id"};
        String tabela = "products";
        List<Map<String, Object>> mensagem = database.deletar(tabela, campos, parametros);

        return ProductAdapter.mapToMensage(mensagem);
    }
    @Override
    public ProductEntity update(Long id,ProductEntity product) {
        ParametroBd[] parametros = {
                new ParametroBd("name", product.getName()),
                new ParametroBd("category", product.getCategory().toString()),
                new ParametroBd("price", product.getPrice()),
                new ParametroBd("description", product.getDescription()),
                new ParametroBd("image_path", product.getImagePath()),
                new ParametroBd("product_id", id)};

        // Chamada ao método Inserir do database com os parâmetros necessários
        String[] campos = {"name", "category", "price","description","image_path"};

        String tabela = "products";
        List<Map<String, Object>> mensagem = database.Update(tabela, campos, parametros);

        if (mensagem != null && !mensagem.isEmpty()){
            return findById(id);

        }
        else {
            throw new RuntimeException("Falha ao atualizar o produto");
        }
    }
}
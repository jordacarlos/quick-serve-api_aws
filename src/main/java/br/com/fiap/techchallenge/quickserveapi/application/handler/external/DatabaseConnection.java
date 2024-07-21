package br.com.fiap.techchallenge.quickserveapi.application.handler.external;
import br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces.ParametroBd;
import br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces.dbconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseConnection implements dbconnection {
    private final String url;
    private final String user;
    private final String password;

    public DatabaseConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection connect() {
        Connection connection = null;
        try {
            // Registrar o driver JDBC
            Class.forName("org.postgresql.Driver");

            // Estabelecer a conexão
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão estabelecida com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC não encontrado: " + e.getMessage());
        }

        return connection;
    }

    private String getTableIdColumn(Connection connection, String tabela) throws SQLException {
        String primaryKeyColumn = null;

        // Obtenha os metadados do banco de dados
        DatabaseMetaData metaData = connection.getMetaData();

        // Obtenha as chaves primárias da tabela
        try (ResultSet rs = metaData.getPrimaryKeys(null, null, tabela)) {
            if (rs.next()) {
                primaryKeyColumn = rs.getString("COLUMN_NAME");
            }
        }

        if (primaryKeyColumn == null) {
            throw new RuntimeException("Nenhuma chave primária encontrada para a tabela: " + tabela);
        }

        return primaryKeyColumn;
    }

    public List<Map<String, Object>> executarQuery(String query, ParametroBd[] parametros, String[] campos) {
        List<Map<String, Object>> resultados = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (parametros != null) {
                for (int i = 0; i < parametros.length; i++) {
                    preparedStatement.setObject(i + 1, parametros[i].getValor());
                }
            }
            //System.out.println("Query antes da execução: " + preparedStatement); // Verifica a query completa antes de executar

            if (query.trim().toUpperCase().startsWith("SELECT")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (String campo : campos) {
                        row.put(campo, resultSet.getObject(campo));
                    }
                    resultados.add(row);
                }
            } else if (query.trim().toUpperCase().startsWith("INSERT")) {
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("id", resultSet.getObject(1));
                    resultados.add(row);
                } else {
                    throw new RuntimeException("Nenhum ID retornado pela operação de inserção.");
                }
            } else {
                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows <= 0) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("Mensagem", "Operação não foi executada, número de registros afetados foi: " + affectedRows);
                    resultados.add(row);
                } else {
                    Map<String, Object> row = new HashMap<>();
                    row.put("Mensagem", "Operação bem-sucedida, número de registros afetados: " + affectedRows);
                    resultados.add(row);
                }
            }
        } catch (SQLException e) {
            Map<String, Object> row = new HashMap<>();
            row.put("Erro", "Erro na operação: " + e.getMessage());
            resultados.add(row);
        }
        return resultados;
    }

    public List<Map<String, Object>> Inserir(String tabela, String[] campos, ParametroBd[] parametros) {
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(tabela).append(" (");

        // Adiciona os campos na query
        for (int i = 0; i < campos.length; i++) {
            query.append(campos[i]);
            if (i < campos.length - 1) {
                query.append(", ");
            }
        }
        query.append(") VALUES (");

        // Adiciona os placeholders para os valores na query
        for (int i = 0; i < parametros.length; i++) {
            query.append("?");
            if (i < parametros.length - 1) {
                query.append(", ");
            }
        }

        // Adiciona a parte RETURNING
        String idColumn;
        try (Connection connection = connect()) {
            idColumn = getTableIdColumn(connection, tabela);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter a coluna de ID da tabela: " + tabela, e);
        }

        query.append(") RETURNING ")
                .append(idColumn)
                .append(";");

        // Exibe a query construída
        System.out.println("Query construída: " + query.toString());

        return executarQuery(query.toString(), parametros, campos);
    }

    public List<Map<String, Object>> deletar(String tabela, String[] campos, ParametroBd[] parametros) {
        // Validação básica
        if (campos.length != parametros.length) {
            throw new RuntimeException("Número de campos e parâmetros não corresponde");
        }

        // Construção da query
        StringBuilder query = new StringBuilder("DELETE FROM ");
        query.append(tabela).append(" WHERE ");

        // Adiciona os campos na query
        for (int i = 0; i < campos.length; i++) {
            query.append(campos[i]).append(" = ?");
            if (i < campos.length - 1) {
                query.append(" AND ");
            }
        }
        query.append(";");

        // Exibe a query construída
        System.out.println("Query construída: " + query.toString());

        return executarQuery(query.toString(), parametros,campos);
    }

    public List<Map<String, Object>> Update(String tabela, String[] campos, ParametroBd[] parametros) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(tabela).append(" SET ");

        // Adiciona os campos na query
        for (int i = 0; i < campos.length; i++) {
            query.append(campos[i]).append(" = ?");
            if (i < campos.length - 1) {
                query.append(", ");
            }
        }

        String idColumn;
        try (Connection connection = connect()) {
            idColumn = getTableIdColumn(connection, tabela);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter a coluna de ID da tabela: " + tabela, e);
        }

        query.append(" WHERE ")
                .append(idColumn)
                .append("= ? ;");

        // Exibe a query construída
        System.out.println("Query construída: " + query.toString());

        return executarQuery(query.toString(), parametros,campos);
    }

    public List<Map<String, Object>> buscarPorParametros(String tabela, String[] campos, ParametroBd[] parametros) {
        List<Map<String, Object>> resultados = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT ");

        if (campos == null || campos.length == 0) {
            query.append("*");
        } else {
            for (int i = 0; i < campos.length; i++) {
                query.append(campos[i]);
                if (i < campos.length - 1) {
                    query.append(", ");
                }
            }
        }

        query.append(" FROM ").append(tabela);

        if (parametros != null && parametros.length > 0) {
            query.append(" WHERE ");
            for (int i = 0; i < parametros.length; i++) {
                query.append(parametros[i].getCampo()).append(" = ?");
                if (i < parametros.length - 1) {
                    query.append(" AND ");
                }
            }
        }

        // Exibe a query construída
        System.out.println("Query construída: " + query.toString());

        return executarQuery(query.toString(), parametros, campos);
    }
}

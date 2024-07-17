package br.com.fiap.techchallenge.quickserveapi;

import br.com.fiap.techchallenge.quickserveapi.Refatorar.controllers.CustomerRepositoryImpl;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.controllers.OrderRepositoryImpl;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.controllers.ProductRepositoryImpl;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.external.DatabaseConnection;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.interfaces.CustomerRepository;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.interfaces.OrderRepository;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.interfaces.ProductRepository;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.usecases.CustomerUseCases;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.usecases.OrderUseCases;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.usecases.ProductUseCases;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QuickServeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickServeApiApplication.class, args);
	}

	@Bean
	public DatabaseConnection databaseConnection() {
		String url = "jdbc:postgresql://localhost:5432/quick-serve"; // Verifique se o nome do banco de dados está correto
		String user = "postgres"; // Verifique o nome do usuário do banco de dados
		String password = "123456"; // Verifique a senha do usuário do banco de dados
		return new DatabaseConnection(url, user, password);
	}
	@Bean
	public CustomerRepository CustomerRepository(DatabaseConnection databaseConnection) {
		return new CustomerRepositoryImpl(databaseConnection);
	}

	@Bean
	public CustomerUseCases customerUseCases(CustomerRepository customerRepository) {
		return new CustomerUseCases(customerRepository);
	}

	@Bean
	public OrderRepository OrderRepository(DatabaseConnection databaseConnection) {
		return new OrderRepositoryImpl(databaseConnection);
	}

	@Bean
	public OrderUseCases orderUseCases(OrderRepository orderRepository) {
		return new OrderUseCases(orderRepository);
	}

	@Bean
	public ProductRepository productRepository(DatabaseConnection databaseConnection) {
		return new ProductRepositoryImpl(databaseConnection);
	}

	@Bean
	public ProductUseCases ProductUseCases(ProductRepository productRepository) {
		return new ProductUseCases(productRepository);
	}
}

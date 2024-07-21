package br.com.fiap.techchallenge.quickserveapi;

import br.com.fiap.techchallenge.quickserveapi.application.handler.api.Customer;
import br.com.fiap.techchallenge.quickserveapi.application.handler.controllers.*;
import br.com.fiap.techchallenge.quickserveapi.application.handler.external.DatabaseConnection;
import br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces.CustomerRepository;
import br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces.OrderRepository;
import br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces.ProductRepository;
import br.com.fiap.techchallenge.quickserveapi.application.handler.usecases.*;
import br.com.fiap.techchallenge.quickserveapi.application.handler.gateway.*;
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

	//Gateway
	@Bean
	public Gateway gateway (DatabaseConnection databaseConnection){
		return new Gateway(databaseConnection);
	}
	//Customer
	@Bean
	public CustomerController customerController (CustomerCase customerCase){
		return new CustomerController(customerCase);
	}
	@Bean
	public CustomerCase customerCase (Gateway gateway){
		return new CustomerCase(gateway);
	}
	//Product Bean
	@Bean
	public ProductController productController (ProductCase productCase){
		return new ProductController(productCase);
	}
	@Bean
	public ProductCase productCase (Gateway gateway){
		return new ProductCase(gateway);
	}

	//order
	@Bean
	public OrderController orderController (OrderCase orderCase){
		return new OrderController(orderCase);
	}
	@Bean
	public OrderCase orderCase (Gateway gateway){
		return new OrderCase(gateway);
	}

/*
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
 */
}

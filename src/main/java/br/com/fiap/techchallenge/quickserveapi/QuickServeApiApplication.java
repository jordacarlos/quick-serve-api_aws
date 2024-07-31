package br.com.fiap.techchallenge.quickserveapi;

import br.com.fiap.techchallenge.quickserveapi.application.handler.controllers.CustomerController;
import br.com.fiap.techchallenge.quickserveapi.application.handler.controllers.OrderController;
import br.com.fiap.techchallenge.quickserveapi.application.handler.controllers.ProductController;
import br.com.fiap.techchallenge.quickserveapi.application.handler.external.DatabaseConnection;
import br.com.fiap.techchallenge.quickserveapi.application.handler.gateway.Gateway;
import br.com.fiap.techchallenge.quickserveapi.application.handler.usecases.CustomerCase;
import br.com.fiap.techchallenge.quickserveapi.application.handler.usecases.OrderCase;
import br.com.fiap.techchallenge.quickserveapi.application.handler.usecases.ProductCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QuickServeApiApplication {

	@Value("${spring.datasource.url}")
	private String springDatasourceUrl;

	@Value("${spring.datasource.username}")
	private String springDatasourceUserName;

	@Value("${spring.datasource.password}")
	private String springDatasourcePassword;

	public static void main(String[] args) {
		SpringApplication.run(QuickServeApiApplication.class, args);
	}

	@Bean
	public DatabaseConnection databaseConnection() {
		String url = this.springDatasourceUrl;
		String user = this.springDatasourceUserName;
		String password = this.springDatasourcePassword;
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

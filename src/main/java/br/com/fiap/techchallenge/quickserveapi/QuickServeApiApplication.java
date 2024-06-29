package br.com.fiap.techchallenge.quickserveapi;

import br.com.fiap.techchallenge.quickserveapi.Refatorar.controllers.CustomerRepositoryImpl;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.external.DatabaseConnection;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.interfaces.CustomerRepository;
import br.com.fiap.techchallenge.quickserveapi.Refatorar.usecases.CustomerUseCases;
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
}

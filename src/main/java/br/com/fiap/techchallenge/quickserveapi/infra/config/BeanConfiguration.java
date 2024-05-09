package br.com.fiap.techchallenge.quickserveapi.infra.config;

import br.com.fiap.techchallenge.quickserveapi.domain.ports.CustomerRepositoryPort;
import br.com.fiap.techchallenge.quickserveapi.domain.ports.CustomerServicePort;
import br.com.fiap.techchallenge.quickserveapi.domain.service.CustomerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    CustomerServicePort customerService(CustomerRepositoryPort customerRepositoryPort){
        return new CustomerServiceImpl(customerRepositoryPort);
    }
}

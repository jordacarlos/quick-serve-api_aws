package br.com.fiap.techchallenge.quickserveapi.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig{

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("API Tech Challange Quick Serve")
                        .description("Esta é a documentação da API do Quick Serve"));
    }
}
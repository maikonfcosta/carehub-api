package com.carehub.carehub_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe de configuração para liberar o CORS (Cross-Origin Resource Sharing).
 * Essencial para o desenvolvimento onde o Front-end (ex: Porta 5173)
 * acessa o Back-end (Porta 8080).
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Libera todos os endpoints que começam com /api
                .allowedOrigins("http://localhost:5173") // O endereço onde o seu Front-end React está rodando
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Tipos de métodos HTTP permitidos
                .allowedHeaders("*"); // Permite todos os cabeçalhos
    }
}
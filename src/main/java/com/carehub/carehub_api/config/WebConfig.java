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
        // 🚨 CONFIGURAÇÃO FINAL PARA AMBIENTE DE DEPLOY E LOCAL 🚨
        registry.addMapping("/api/**") // Libera todos os endpoints que começam com /api
                .allowedOrigins("*") // ⬅️ Permite qualquer origem (Para Vercel, Render e Local)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Tipos de métodos HTTP permitidos
                .allowedHeaders("*"); // Permite todos os cabeçalhos
    }
}
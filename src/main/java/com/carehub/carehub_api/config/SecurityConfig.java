package com.carehub.carehub_api.config;

import com.carehub.carehub_api.security.FirebaseTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final FirebaseTokenFilter firebaseTokenFilter;

    // A injeção funciona porque o FirebaseTokenFilter tem a anotação @Component
    public SecurityConfig(FirebaseTokenFilter firebaseTokenFilter) {
        this.firebaseTokenFilter = firebaseTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())

                // Configuração do CORS (liberando todas as origens para o React/Vercel)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(Arrays.asList("*"));
                    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    configuration.setAllowedHeaders(Arrays.asList("*"));
                    return configuration;
                }))

                // Política de sessão: Stateless (sem estado - essencial para JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Regras de Autorização
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso público ao ViaCEP
                        .requestMatchers(HttpMethod.GET, "/api/cep/**").permitAll()

                        // 🚨 TODAS AS OUTRAS ROTAS EXIGEM AUTENTICAÇÃO (Bearer Token)
                        .requestMatchers("/api/**").authenticated()

                        // Permite acesso a qualquer outra rota não mapeada (como assets)
                        .anyRequest().permitAll()
                )

                // 🚨 INTEGRAÇÃO: Adiciona o Filtro Customizado na cadeia de segurança
                .addFilterBefore(firebaseTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
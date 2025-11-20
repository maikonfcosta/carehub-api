package com.carehub.carehub_api.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component; // ⬅️ 🚨 CORREÇÃO: IMPORTAR O COMPONENTE
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Filtro que intercepta requisições, extrai o JWT do Firebase e o valida.
 */
@Component // ⬅️ 🚨 ADICIONAR ESTA ANOTAÇÃO 🚨
public class FirebaseTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {
            // 1. Valida o Token com o Firebase Admin SDK
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token);

            // 2. Cria o objeto de autenticação do Spring Security
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    firebaseToken.getUid(),
                    null,
                    Collections.emptyList()
            );

            // 3. Define o usuário no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            // Token inválido, expirado ou erro do Firebase.
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token Firebase inválido ou expirado.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
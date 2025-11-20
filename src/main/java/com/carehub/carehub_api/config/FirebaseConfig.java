package com.carehub.carehub_api.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    // 🚨 Lê o JSON da chave secreta da variável de ambiente do Render 🚨
    @Value("${FIREBASE_CREDENTIALS}")
    private String firebaseCredentialsJson;

    @PostConstruct
    public void initializeFirebase() throws IOException {

        if (!FirebaseApp.getApps().isEmpty()) {
            return;
        }

        // Converte a string JSON para um InputStream
        ByteArrayInputStream serviceAccount = new ByteArrayInputStream(firebaseCredentialsJson.getBytes());

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
        System.out.println("Firebase Admin SDK inicializado com sucesso.");
    }
}
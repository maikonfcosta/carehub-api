package com.carehub.carehub_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Representa a entidade Paciente no sistema CareHub.
 * Esta classe é mapeada para uma tabela no banco de dados.
 */
@Entity
@Table(name = "pacientes") // Define o nome da tabela no DB
@Data // Anotação do Lombok: Cria automaticamente Getters, Setters, toString, equals e hashCode.
@NoArgsConstructor // Anotação do Lombok: Cria um construtor sem argumentos.
@AllArgsConstructor // Anotação do Lombok: Cria um construtor com todos os argumentos.
public class Paciente {

    // Chave primária da tabela, gerada automaticamente pelo banco de dados.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100) // Campo obrigatório
    private String nomeCompleto;

    @Column(nullable = false, length = 15, unique = true) // Campo obrigatório e único
    private String cpf;

    @Column(length = 15)
    private String telefone;

    @Column(nullable = false, length = 100)
    private String email;

    // TODO: Adicionar campos de endereço (rua, cep, cidade, etc.) que podem ser integrados com a ViaCEP API depois.
    private String cep;
}
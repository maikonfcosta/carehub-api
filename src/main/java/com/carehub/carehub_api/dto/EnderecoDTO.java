package com.carehub.carehub_api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Data Transfer Object (DTO) para mapear a resposta JSON da API ViaCEP.
 * Exemplo: { "cep": "...", "logradouro": "...", "localidade": "..." }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade; // Cidade
    private String uf;         // Estado
    private String ibge;
    private boolean erro; // Campo de erro da ViaCEP
}
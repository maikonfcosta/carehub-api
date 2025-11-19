package com.carehub.carehub_api.service;

import com.carehub.carehub_api.dto.EnderecoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

/**
 * Serviço responsável por buscar o endereço completo através da API ViaCEP.
 */
@Service
public class ViaCepService {

    private static final String VIACEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Busca o endereço completo a partir de um CEP.
     * @param cep O CEP a ser consultado (apenas números).
     * @return EnderecoDTO com as informações preenchidas.
     */
    public EnderecoDTO buscarEnderecoPorCep(String cep) {

        // 1. Constrói a URL para a requisição
        String urlFormatada = VIACEP_URL.replace("{cep}", cep);

        // 2. Faz a requisição GET e mapeia a resposta para o EnderecoDTO
        EnderecoDTO endereco;
        try {
            endereco = restTemplate.getForObject(urlFormatada, EnderecoDTO.class);
        } catch (Exception e) {
            // Em caso de falha de conexão ou timeout
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Falha ao conectar com o serviço ViaCEP.", e);
        }

        // 3. Verifica se a ViaCEP retornou erro (erro = true) ou dados nulos
        if (endereco == null || endereco.isErro()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CEP não encontrado ou formato inválido.");
        }

        return endereco;
    }
}
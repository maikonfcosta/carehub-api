package com.carehub.carehub_api.controller;

import com.carehub.carehub_api.dto.EnderecoDTO;
import com.carehub.carehub_api.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para expor o endpoint de consulta ViaCEP.
 */
@RestController
@RequestMapping("/api/cep")
public class ViaCepController {

    @Autowired
    private ViaCepService viaCepService;

    /**
     * Busca o endereço completo a partir de um CEP.
     * GET /api/cep/{cep}
     */
    @GetMapping("/{cep}")
    public EnderecoDTO buscarEndereco(@PathVariable String cep) {
        // Remove caracteres não numéricos antes de enviar para o serviço
        String cepLimpo = cep.replaceAll("[^0-9]", "");
        return viaCepService.buscarEnderecoPorCep(cepLimpo);
    }
}
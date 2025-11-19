package com.carehub.carehub_api.controller;

import com.carehub.carehub_api.dto.PagamentoRelatorioDTO;
import com.carehub.carehub_api.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    /**
     * Retorna o histórico de pagamentos processados.
     * GET /api/relatorios/pagamentos
     */
    @GetMapping("/pagamentos")
    public List<PagamentoRelatorioDTO> listarPagamentos() {
        return relatorioService.getHistoricoPagamentos();
    }
}
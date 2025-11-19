package com.carehub.carehub_api.controller;

import com.carehub.carehub_api.dto.PagamentoRequest;
import com.carehub.carehub_api.service.PagamentoService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    /**
     * Endpoint para processar um pagamento via Stripe.
     * Recebe o token do cartão e os dados da transação.
     * POST /api/pagamentos/processar
     */
    @PostMapping("/processar")
    public ResponseEntity<String> processarPagamento(@RequestBody PagamentoRequest request) {
        try {
            Charge charge = pagamentoService.processarPagamento(request);

            if (charge.getPaid()) {
                // Sucesso
                return ResponseEntity.ok("Pagamento processado com sucesso. ID da transação: " + charge.getId());
            } else {
                // Pagamento não concluído (ex: status pendente)
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Transação em processamento. Status: " + charge.getStatus());
            }
        } catch (StripeException e) {
            // Erro de lógica do Stripe (já tratado no service para retornar 400)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResponseStatusException e) {
            // Erro de validação de negócio (404, 400)
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
}
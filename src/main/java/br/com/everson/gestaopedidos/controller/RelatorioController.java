package br.com.everson.gestaopedidos.controller;

import br.com.everson.gestaopedidos.dto.RelatorioVendasDTO;
import br.com.everson.gestaopedidos.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final PedidoService pedidoService;

    public RelatorioController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/vendas")
    public RelatorioVendasDTO faturamentoPorPeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        return pedidoService.gerarRelatorio(inicio, fim);
    }
}
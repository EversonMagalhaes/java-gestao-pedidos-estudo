package br.com.everson.gestaopedidos.controller;

import br.com.everson.gestaopedidos.dto.RelatorioVendasDTO;
import br.com.everson.gestaopedidos.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/relatorios")
@Tag(name = "5. Relatórios", description = "Relatorio de VendasTotal de pedidos fechados por período")
public class RelatorioController {

    private final PedidoService pedidoService;

    public RelatorioController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/vendas")
    @Operation(summary = "Relatório de faturamento por período", description = "Relatório de faturamento por período")
    public RelatorioVendasDTO faturamentoPorPeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        return pedidoService.gerarRelatorio(inicio, fim);
    }
}
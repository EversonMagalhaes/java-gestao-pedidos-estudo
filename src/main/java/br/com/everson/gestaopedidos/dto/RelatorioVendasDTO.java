package br.com.everson.gestaopedidos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RelatorioVendasDTO {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal totalFaturado;
    private Long quantidadePedidos;

    public RelatorioVendasDTO(LocalDate dataInicio, LocalDate dataFim, BigDecimal totalFaturado, Long quantidadePedidos) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.totalFaturado = totalFaturado;
        this.quantidadePedidos = quantidadePedidos;
    }

    // Getters
    public LocalDate getDataInicio() { return dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public BigDecimal getTotalFaturado() { return totalFaturado; }
    public Long getQuantidadePedidos() { return quantidadePedidos; }
}
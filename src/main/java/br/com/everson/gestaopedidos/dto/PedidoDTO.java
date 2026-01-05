package br.com.everson.gestaopedidos.dto;

import br.com.everson.gestaopedidos.domain.pedido.PedidoStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {

    private Long id;
    private LocalDateTime dataPedido;
    private PedidoStatus status;
    private BigDecimal total;
    private List<ItemPedidoDTO> itens;

    public PedidoDTO(
            Long id,
            LocalDateTime dataPedido,
            PedidoStatus status,
            BigDecimal total,
            List<ItemPedidoDTO> itens
    ) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.status = status;
        this.total = total;
        this.itens = itens;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public PedidoStatus getStatus() {
        return status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }
}

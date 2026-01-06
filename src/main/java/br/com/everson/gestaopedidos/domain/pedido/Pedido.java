package br.com.everson.gestaopedidos.domain.pedido;

import br.com.everson.gestaopedidos.exception.RegraNegocioException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataPedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PedidoStatus status;

    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ItemPedido> itens = new ArrayList<>();

    protected Pedido() {
        // JPA
    }

    public Pedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
        this.status = PedidoStatus.ABERTO;
    }

    public void adicionarItem(ItemPedido item) {
        item.setPedido(this);
        this.itens.add(item);
    }

    public BigDecimal getTotal() {
        return itens.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

/*
    public void fechar() {
        this.status = PedidoStatus.FECHADO;
    }

    public void cancelar() {
        this.status = PedidoStatus.CANCELADO;
    }
*/

    public void fechar() {
        if (this.status != PedidoStatus.ABERTO) {
            throw new RegraNegocioException("Apenas pedidos ABERTOS podem ser fechados.");
        }
        this.status = PedidoStatus.FECHADO;
    }

    public void cancelar() {
        if (this.status == PedidoStatus.FECHADO) {
            throw new RegraNegocioException("Um pedido FECHADO não pode ser cancelado.");
        }
        this.status = PedidoStatus.CANCELADO;
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

    public List<ItemPedido> getItens() {
        return itens;
    }



}

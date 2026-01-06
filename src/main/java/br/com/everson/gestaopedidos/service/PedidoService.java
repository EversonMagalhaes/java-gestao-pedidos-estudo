package br.com.everson.gestaopedidos.service;

import br.com.everson.gestaopedidos.domain.Produto;
import br.com.everson.gestaopedidos.domain.pedido.ItemPedido;
import br.com.everson.gestaopedidos.domain.pedido.Pedido;
import br.com.everson.gestaopedidos.domain.pedido.PedidoStatus;
import br.com.everson.gestaopedidos.dto.*;
import br.com.everson.gestaopedidos.exception.PedidoNaoEncontradoException;
import br.com.everson.gestaopedidos.exception.ProdutoNaoEncontradoException;
import br.com.everson.gestaopedidos.repository.PedidoRepository;
import br.com.everson.gestaopedidos.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            ProdutoRepository produtoRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public PedidoDTO criar(PedidoCreateDTO dto) {

        Pedido pedido = new Pedido(java.time.LocalDateTime.now());

        for (ItemPedidoCreateDTO itemDTO : dto.getItens()) {

            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() ->
                            new ProdutoNaoEncontradoException(itemDTO.getProdutoId())
                    );

            ItemPedido item = new ItemPedido(produto, itemDTO.getQuantidade());
            pedido.adicionarItem(item);
        }

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return toDTO(pedidoSalvo);
    }

    private PedidoDTO toDTO(Pedido pedido) {

        List<ItemPedidoDTO> itens = pedido.getItens().stream()
                .map(item -> new ItemPedidoDTO(
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        item.getPrecoUnitario(),
                        item.getSubtotal()
                ))
                .toList();

        return new PedidoDTO(
                pedido.getId(),
                pedido.getDataPedido(),
                pedido.getStatus(),
                pedido.getTotal(),
                itens
        );
    }

    @Transactional(readOnly = true)
    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PedidoDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
        return toDTO(pedido);
    }
    @Transactional
    public PedidoDTO fecharPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));

        pedido.fechar(); // Altera status para FECHADO
        return toDTO(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoDTO cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));

        pedido.cancelar(); // Altera status para CANCELADO
        return toDTO(pedidoRepository.save(pedido));
    }

    public RelatorioVendasDTO gerarRelatorio(LocalDate inicio, LocalDate fim) {
        // Convertendo LocalDate para LocalDateTime (início e fim do dia)
        LocalDateTime dataInicial = inicio.atStartOfDay();
        LocalDateTime dataFinal = fim.atTime(23, 59, 59);

        List<Pedido> pedidos = pedidoRepository.buscarPedidosConcluidosNoPeriodo(
                PedidoStatus.FECHADO, dataInicial, dataFinal);

        BigDecimal faturamentoTotal = pedidos.stream()
                .map(Pedido::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new RelatorioVendasDTO(inicio, fim, faturamentoTotal, (long) pedidos.size());
    }

}

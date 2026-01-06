package br.com.everson.gestaopedidos.repository;

import br.com.everson.gestaopedidos.domain.pedido.Pedido;
import br.com.everson.gestaopedidos.domain.pedido.PedidoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Busca pedidos concluídos em um intervalo de tempo
    @Query("SELECT p FROM Pedido p WHERE p.status = :status AND p.dataPedido BETWEEN :inicio AND :fim")
    List<Pedido> buscarPedidosConcluidosNoPeriodo(
            @Param("status") PedidoStatus status,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

}

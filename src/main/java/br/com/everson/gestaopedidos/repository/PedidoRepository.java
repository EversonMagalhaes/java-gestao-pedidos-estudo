package br.com.everson.gestaopedidos.repository;

import br.com.everson.gestaopedidos.domain.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}

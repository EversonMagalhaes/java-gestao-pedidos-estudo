package br.com.everson.gestaopedidos.repository;

import br.com.everson.gestaopedidos.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}

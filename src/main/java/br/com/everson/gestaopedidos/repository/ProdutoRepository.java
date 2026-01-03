package br.com.everson.gestaopedidos.repository;

import br.com.everson.gestaopedidos.domain.Produto;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
@Repository
public class ProdutoRepository {
    public List<Produto> listarTodos() {
        return List.of(
                new Produto(1L, "Café", new BigDecimal("12.50"), true),
                new Produto(2L, "Mel", new BigDecimal("25.00"), true),
                new Produto(3L, "Chá", new BigDecimal("9.90"), false)
        );
    }
}

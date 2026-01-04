package br.com.everson.gestaopedidos.service;

import br.com.everson.gestaopedidos.domain.Produto;
import br.com.everson.gestaopedidos.repository.ProdutoFakeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoFakeService {
    private final ProdutoFakeRepository produtoFakeRepository;

    public ProdutoFakeService(ProdutoFakeRepository produtoFakeRepository) {
        this.produtoFakeRepository = produtoFakeRepository;
    }

    public List<Produto> listarTodos() {
        return produtoFakeRepository.listarTodos();
    }
}

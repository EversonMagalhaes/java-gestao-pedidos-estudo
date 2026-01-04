package br.com.everson.gestaopedidos.service;

import br.com.everson.gestaopedidos.domain.Produto;
import br.com.everson.gestaopedidos.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import br.com.everson.gestaopedidos.dto.ProdutoDTO;

import java.util.List;

import br.com.everson.gestaopedidos.dto.ProdutoCreateDTO;

import br.com.everson.gestaopedidos.exception.ProdutoNaoEncontradoException;

@Service

public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(produto ->
                        new ProdutoDTO(
                                produto.getId(),
                                produto.getNome(),
                                produto.getPreco(),
                                produto.isAtivo()
                        )
                )
                .toList();
    }

    public void criar(ProdutoCreateDTO dto) {
        Produto produto = new Produto(
                dto.getNome(),
                dto.getPreco(),
                dto.getAtivo()
        );

        produtoRepository.save(produto);
    }
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }
}

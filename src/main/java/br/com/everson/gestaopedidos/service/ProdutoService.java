package br.com.everson.gestaopedidos.service;

import br.com.everson.gestaopedidos.domain.Produto;
import br.com.everson.gestaopedidos.repository.ProdutoRepository;

import org.springframework.stereotype.Service;
import br.com.everson.gestaopedidos.dto.ProdutoDTO;

import java.util.List;

import br.com.everson.gestaopedidos.dto.ProdutoCreateDTO;

import br.com.everson.gestaopedidos.exception.ProdutoNaoEncontradoException;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void criar(ProdutoCreateDTO dto) {
        Produto produto = new Produto(
                dto.getNome(),
                dto.getPreco(),
                dto.getAtivo()
        );

        produtoRepository.save(produto);
        // No 'criar', o .save() ainda é comum para deixar claro,
        // mas o @Transactional garante que se algo der errado após essa linha,
        // ele desfaz (rollback) a criação
    }
    @Transactional
    public void atualizar(Long id, ProdutoCreateDTO dto) {
        Produto produto = this.buscarPorId(id); // Acha o cara

        produto.setNome(dto.getNome());   // Troca o nome
        produto.setPreco(dto.getPreco()); // Troca o preço
        produto.setAtivo(dto.getAtivo()); // Troca o status

       // produtoRepository.save(produto); // <--- PODE COMENTAR!
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    public void excluir(Long id) {
        // Primeiro verificamos se o produto existe (reaproveitando seu método buscarPorId)
        // Se não existir, ele já lança a ProdutoNaoEncontradoException automaticamente
        this.buscarPorId(id);

        // Se passou pela linha de cima, o produto existe, então deletamos
        produtoRepository.deleteById(id);
    }
}

package br.com.everson.gestaopedidos.controller;

import br.com.everson.gestaopedidos.domain.Produto;
import br.com.everson.gestaopedidos.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.everson.gestaopedidos.dto.ProdutoDTO;
import java.util.List;

import br.com.everson.gestaopedidos.dto.ProdutoCreateDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;

@RestController
@Tag(name = "3. Produtos", description = "Cadastro de Produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/produtos")
    @Operation(summary = "Listar Produtos", description = "Retorna uma lista com todos os produtos cadastrados")
    public List<ProdutoDTO> listar() {
        return produtoService.listarTodos();
    }

    @PostMapping("/produtos")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastro de Produtos", description = "Inserir um novo produto")
    public void criar(@RequestBody @Valid ProdutoCreateDTO dto) {
        produtoService.criar(dto);
    }

    @GetMapping("/produtos/{id}")
    @Operation(summary = "Busca um produto pelo Id", description = "Retorna os dados de um produto cadastrado")
    public ProdutoDTO buscarPorId(@PathVariable Long id) {

        Produto produto = produtoService.buscarPorId(id);

        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.isAtivo()
        );
    }

    @DeleteMapping("/produtos/{id}")
    @PreAuthorize("hasRole('ADMIN')") // <--- Proteção direta no método
    @Operation(summary = "Deletar Produto pelo Id", description = "Deleta o produto especificado pelo Id (Somente Admin)")
    public ResponseEntity deletar(@PathVariable Long id) {
        produtoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/produtos/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Mantendo o seu padrão de segurança
    @Operation(summary = "Editar / Atualizar um Produto", description = "Edita / Atualiza um produto especificado pelo Id ")
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ProdutoCreateDTO dto) {

        // Chamamos o service que você acabou de ajustar
        produtoService.atualizar(id, dto);

        // Buscamos o produto atualizado para devolver ao Front-end (boa prática)
        Produto produtoAtualizado = produtoService.buscarPorId(id);

        return ResponseEntity.ok(new ProdutoDTO(
                produtoAtualizado.getId(),
                produtoAtualizado.getNome(),
                produtoAtualizado.getPreco(),
                produtoAtualizado.isAtivo()
        ));
    }
}

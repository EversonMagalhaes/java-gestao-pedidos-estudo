package br.com.everson.gestaopedidos.controller;

import br.com.everson.gestaopedidos.domain.Produto;
import br.com.everson.gestaopedidos.service.ProdutoFakeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@Tag(name = "6. Static Repository", description = "ProdutoStaticRepository - testes")
public class ProdutoFakeController {
    private final ProdutoFakeService produtoFakeService;

    public ProdutoFakeController(ProdutoFakeService produtoFakeService) {
        this.produtoFakeService = produtoFakeService;
    }

    @GetMapping("/produtosfake")
    @Operation(summary = "Lista de Produtos Fake", description = "Exemplo de repositório estático")
    public List<Produto> listar() {
        return produtoFakeService.listarTodos();
    }
}

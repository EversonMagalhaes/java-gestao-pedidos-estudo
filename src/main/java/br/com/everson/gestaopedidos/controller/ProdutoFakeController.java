package br.com.everson.gestaopedidos.controller;

import br.com.everson.gestaopedidos.domain.Produto;
import br.com.everson.gestaopedidos.service.ProdutoFakeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class ProdutoFakeController {
    private final ProdutoFakeService produtoFakeService;

    public ProdutoFakeController(ProdutoFakeService produtoFakeService) {
        this.produtoFakeService = produtoFakeService;
    }

    @GetMapping("/produtosfake")
    public List<Produto> listar() {
        return produtoFakeService.listarTodos();
    }
}

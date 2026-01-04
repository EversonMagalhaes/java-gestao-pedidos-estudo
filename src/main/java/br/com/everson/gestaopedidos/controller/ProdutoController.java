package br.com.everson.gestaopedidos.controller;

//import br.com.everson.gestaopedidos.domain.Produto;
import br.com.everson.gestaopedidos.service.ProdutoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.everson.gestaopedidos.dto.ProdutoDTO;
import java.util.List;

import br.com.everson.gestaopedidos.dto.ProdutoCreateDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/produtos")
    public List<ProdutoDTO> listar() {
        return produtoService.listarTodos();
    }

    @PostMapping("/produtos")
    public void criar(@RequestBody @Valid ProdutoCreateDTO dto) {
        produtoService.criar(dto);
    }
}

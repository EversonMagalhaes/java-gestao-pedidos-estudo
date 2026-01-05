package br.com.everson.gestaopedidos.controller;

import br.com.everson.gestaopedidos.dto.PedidoCreateDTO;
import br.com.everson.gestaopedidos.dto.PedidoDTO;
import br.com.everson.gestaopedidos.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO criar(@RequestBody @Valid PedidoCreateDTO dto) {
        return pedidoService.criar(dto);
    }

    @GetMapping
    public List<PedidoDTO> listar() {
        return pedidoService.listarTodos();
    }

}

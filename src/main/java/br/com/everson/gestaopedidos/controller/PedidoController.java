package br.com.everson.gestaopedidos.controller;

import br.com.everson.gestaopedidos.dto.PedidoCreateDTO;
import br.com.everson.gestaopedidos.dto.PedidoDTO;
import br.com.everson.gestaopedidos.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "4. Pedidos", description = "Cadastro de Pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastro de Pedidos", description = "Inserir um novo pedido")
    public PedidoDTO criar(@RequestBody @Valid PedidoCreateDTO dto) {
        return pedidoService.criar(dto);
    }

    @GetMapping
    @Operation(summary = "Listar Pedidos", description = "Retorna uma lista com todos os pedidos cadastrados")
    public List<PedidoDTO> listar() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um pedido pelo Id", description = "Retorna os dados de um pedido cadastrado")
    public PedidoDTO buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    @PutMapping("/{id}/fechar")
    @Operation(summary = "Fechar pedido", description = "Fechar pedido aberto")
    public PedidoDTO fechar(@PathVariable Long id) {
        return pedidoService.fecharPedido(id);
    }

    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar pedido", description = "Cancelar pedido aberto")
    public PedidoDTO cancelar(@PathVariable Long id) {
        return pedidoService.cancelarPedido(id);
    }
}

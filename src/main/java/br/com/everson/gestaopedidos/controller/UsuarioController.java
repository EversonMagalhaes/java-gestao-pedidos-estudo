package br.com.everson.gestaopedidos.controller;

import br.com.everson.gestaopedidos.dto.UsuarioCreateDTO;
import br.com.everson.gestaopedidos.dto.UsuarioDTO;
import br.com.everson.gestaopedidos.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "2. Usuários", description = "Cadastro de Usuários")
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastro de Usuário", description = "Cria um novo usuário no sistema com senha criptografada")
    public void criar(@RequestBody @Valid UsuarioCreateDTO dto) {
        service.criar(dto);
    }

    @GetMapping
    @Operation(summary = "Listar Usuários", description = "Retorna uma lista com todos os usuários cadastrados (sem exibir senhas)")
    public List<UsuarioDTO> listar() {
        return service.listarTodos();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Editar / Atualizar Usuário", description = "Atualiza os dados de um usuário existente (login, senha e role)")
    public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioCreateDTO dto) {
        return service.atualizar(id, dto);
    }

}
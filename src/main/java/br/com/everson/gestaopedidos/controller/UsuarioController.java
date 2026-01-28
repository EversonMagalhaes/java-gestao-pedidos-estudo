package br.com.everson.gestaopedidos.controller;

import br.com.everson.gestaopedidos.domain.usuario.Role;
import br.com.everson.gestaopedidos.domain.usuario.Usuario;
import br.com.everson.gestaopedidos.dto.UsuarioCreateDTO;
import br.com.everson.gestaopedidos.dto.UsuarioDTO;
import br.com.everson.gestaopedidos.dto.UsuarioUpdateDTO;
import br.com.everson.gestaopedidos.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/roles")
    public Role[] listarRoles() {
        return Role.values();
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

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Usuário por ID", description = "Busca os detalhes de um usuário específico para edição")
    public UsuarioDTO buscarPorId(@PathVariable Long id) {
        // Chamamos o service, mas convertemos para DTO antes de enviar para o Vue
        Usuario u = service.buscarPorId(id);
        return new UsuarioDTO(u.getId(), u.getLogin(), u.getRole());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Editar / Atualizar Usuário", description = "Atualiza os dados de um usuário existente (login, senha e role)")
    public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}") // Se o Controller já tem @RequestMapping("/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir Usuário", description = "Deleta o Usuario especificado pelo Id (Somente Admin)")
    public ResponseEntity<Void> deletar(@PathVariable Long id) { // Adicione <Void> para boas práticas
        service.excluir(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }




}
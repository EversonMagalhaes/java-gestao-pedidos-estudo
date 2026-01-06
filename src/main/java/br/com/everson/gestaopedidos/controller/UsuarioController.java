package br.com.everson.gestaopedidos.controller;

import br.com.everson.gestaopedidos.dto.UsuarioCreateDTO;
import br.com.everson.gestaopedidos.dto.UsuarioDTO;
import br.com.everson.gestaopedidos.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criar(@RequestBody @Valid UsuarioCreateDTO dto) {
        service.criar(dto);
    }

    @GetMapping
    public List<UsuarioDTO> listar() {
        return service.listarTodos();
    }
}
package br.com.everson.gestaopedidos.controller;

import br.com.everson.gestaopedidos.domain.usuario.Usuario;
import br.com.everson.gestaopedidos.dto.LoginDTO;
import br.com.everson.gestaopedidos.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpMethod;

@RestController
@Tag(name = "1. Login", description = "Gestão de usuários do sistema")
@RequestMapping("/login")
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    @Operation(summary = "Login no Sistema", description = "Verifica se o usuário existe, checa a senha e retorna um token para login")
    public String efetuarLogin(@RequestBody @Valid LoginDTO dto) {
        System.out.println(">>> CHEGOU NO CONTROLLER: " + dto.login());
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
        var authentication = manager.authenticate(authenticationToken);

        return tokenService.gerarToken((Usuario) authentication.getPrincipal());
    }
}
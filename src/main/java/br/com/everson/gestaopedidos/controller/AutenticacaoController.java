package br.com.everson.gestaopedidos.controller;

import br.com.everson.gestaopedidos.domain.usuario.Usuario;
import br.com.everson.gestaopedidos.dto.LoginDTO;
import br.com.everson.gestaopedidos.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public String efetuarLogin(@RequestBody @Valid LoginDTO dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
        var authentication = manager.authenticate(authenticationToken);

        return tokenService.gerarToken((Usuario) authentication.getPrincipal());
    }
}
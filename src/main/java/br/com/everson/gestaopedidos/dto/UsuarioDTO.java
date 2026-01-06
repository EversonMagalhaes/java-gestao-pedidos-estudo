package br.com.everson.gestaopedidos.dto;

import br.com.everson.gestaopedidos.domain.usuario.Role;

public record UsuarioDTO(
        Long id,
        String login,
        Role role
) {}
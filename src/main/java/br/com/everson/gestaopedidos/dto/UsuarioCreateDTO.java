package br.com.everson.gestaopedidos.dto;

import br.com.everson.gestaopedidos.domain.usuario.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioCreateDTO(
        @NotBlank String login,
        @NotBlank String senha,
        @NotNull Role role
) {}
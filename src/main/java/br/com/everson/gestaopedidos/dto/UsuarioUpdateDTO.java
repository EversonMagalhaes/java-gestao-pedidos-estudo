package br.com.everson.gestaopedidos.dto;
import br.com.everson.gestaopedidos.domain.usuario.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioUpdateDTO(
        @NotBlank String login,
        String senha, // Sem @NotBlank aqui!
        @NotNull Role role
) {}

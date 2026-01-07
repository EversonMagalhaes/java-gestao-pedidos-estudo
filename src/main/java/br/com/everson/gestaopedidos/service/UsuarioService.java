package br.com.everson.gestaopedidos.service;

import br.com.everson.gestaopedidos.domain.usuario.Usuario;
import br.com.everson.gestaopedidos.dto.UsuarioCreateDTO;
import br.com.everson.gestaopedidos.dto.UsuarioDTO;
import br.com.everson.gestaopedidos.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    /*public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }*/

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void criar(UsuarioCreateDTO dto) {
        if (repository.findByLogin(dto.login()).isPresent()) {
            throw new RuntimeException("Login já existe!"); // No futuro usaremos RegraNegocioException
        }
        String senhaCriptografada = passwordEncoder.encode(dto.senha()); // AQUI A MÁGICA ACONTECE
        Usuario usuario = new Usuario(dto.login(), senhaCriptografada, dto.role());
        //Usuario usuario = new Usuario(dto.login(), dto.senha(), dto.role());
        repository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        return repository.findAll().stream()
                .map(u -> new UsuarioDTO(u.getId(), u.getLogin(), u.getRole()))
                .toList();
    }
}
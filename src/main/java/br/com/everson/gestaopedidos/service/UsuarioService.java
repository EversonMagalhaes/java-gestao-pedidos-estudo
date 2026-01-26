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

    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioCreateDTO dto) {
        // 1. Busca o usuário ou lança erro se não existir
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        // 2. Valida se o novo login já não pertence a OUTRO usuário
        var usuarioComMesmoLogin = repository.findByLogin(dto.login());
        if (usuarioComMesmoLogin.isPresent() && !usuarioComMesmoLogin.get().getId().equals(id)) {
            throw new RuntimeException("Este login já está em uso por outro usuário!");
        }

        // 3. Atualiza os dados (Usando a mutabilidade da Classe Usuario)
        // Se o login mudar, atualizamos; se a senha mudar, criptografamos a nova
        String senhaCriptografada = passwordEncoder.encode(dto.senha());

        // Aqui usamos os SETTERS da classe Usuario
        // Note que lemos do RECORD dto usando login() e senha()
        usuario.setLogin(dto.login());
        usuario.setSenha(senhaCriptografada);
        usuario.setRole(dto.role());

        // 4. Retorna o DTO de resposta
        return new UsuarioDTO(usuario.getId(), usuario.getLogin(), usuario.getRole());
    }

}
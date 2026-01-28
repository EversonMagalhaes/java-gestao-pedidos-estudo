package br.com.everson.gestaopedidos.service;

import br.com.everson.gestaopedidos.domain.usuario.Usuario;
import br.com.everson.gestaopedidos.dto.UsuarioCreateDTO;
import br.com.everson.gestaopedidos.dto.UsuarioDTO;
import br.com.everson.gestaopedidos.dto.UsuarioUpdateDTO;
import br.com.everson.gestaopedidos.exception.UsuarioNaoEncontradoException;
import br.com.everson.gestaopedidos.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /*public UsuarioService_new(UsuarioRepository repository) {
        this.repository = repository;
    }*/

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void criar(UsuarioCreateDTO dto) {
        if (usuarioRepository.findByLogin(dto.login()).isPresent()) {
            throw new RuntimeException("Login já existe!"); // No futuro usaremos RegraNegocioException
        }
        String senhaCriptografada = passwordEncoder.encode(dto.senha()); // AQUI A MÁGICA ACONTECE
        Usuario usuario = new Usuario(dto.login(), senhaCriptografada, dto.role());
        //Usuario usuario = new Usuario(dto.login(), dto.senha(), dto.role());
        usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(u -> new UsuarioDTO(u.getId(), u.getLogin(), u.getRole()))
                .toList();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioUpdateDTO dto) {
        // 1. Busca o usuário ou lança erro se não existir
        // Usuario usuario = repository.findById(id)
        //        .orElseThrow(() -> new UsuarioNaoEncontradoException(id)); //RuntimeException("Usuário não encontrado!"));
        Usuario usuario = this.buscarPorId(id);
        // 2. Valida se o novo login já não pertence a OUTRO usuário
        var usuarioComMesmoLogin = usuarioRepository.findByLogin(dto.login());
        if (usuarioComMesmoLogin.isPresent() && !usuarioComMesmoLogin.get().getId().equals(id)) {
            throw new RuntimeException("Este login já está em uso por outro usuário!");
        }

        // 3. Atualiza os dados (Usando a mutabilidade da Classe Usuario)
        // Se o login mudar, atualizamos; se a senha mudar, criptografamos a nova


        // Aqui usamos os SETTERS da classe Usuario
        // Note que lemos do RECORD dto usando login() e senha()
        usuario.setLogin(dto.login());
        if (dto.senha() != null && !dto.senha().isBlank()) {
            String senhaCriptografada = passwordEncoder.encode(dto.senha());
            usuario.setSenha(senhaCriptografada);
        }
        usuario.setRole(dto.role());

        // 4. Retorna o DTO de resposta
        return new UsuarioDTO(usuario.getId(), usuario.getLogin(), usuario.getRole());
    }

    public void excluir(Long id) {
        // 1. Reusa sua lógica: se não existir, já lança a UsuarioNaoEncontradoException
        this.buscarPorId(id);

        // 2. Use a variável 'usuarioRepository' (minúscula), que é o seu @Autowired
        usuarioRepository.deleteById(id);
    }

}
package br.com.everson.gestaopedidos.service;

import br.com.everson.gestaopedidos.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository repository;

    public AutenticacaoService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return repository.findByLogin(login)
                .map(u -> User.builder()
                        .username(u.getLogin())
                        .password(u.getSenha()) // CUIDADO: Por enquanto a senha no banco é 123 pura
                        .roles(u.getRole().name())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + login));
    }
}
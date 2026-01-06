package br.com.everson.gestaopedidos.domain.usuario;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    protected Usuario() {}

    public Usuario(String login, String senha, Role role) {
        this.login = login;
        this.senha = senha;
        this.role = role;
    }

    // Getters
    public Long getId() { return id; }
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public Role getRole() { return role; }
}
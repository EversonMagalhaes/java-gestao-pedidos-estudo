package br.com.everson.gestaopedidos.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private BigDecimal preco;
    private boolean ativo;

    public Produto(Long id, String nome, BigDecimal preco, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.ativo = ativo;
    }
    public Produto(String nome, BigDecimal preco, boolean ativo) {
        this.nome = nome;
        this.preco = preco;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    protected Produto() {} /* construtor exigido pelo JPA*/

}
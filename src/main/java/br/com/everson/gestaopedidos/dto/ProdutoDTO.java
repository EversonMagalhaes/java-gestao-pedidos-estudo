package br.com.everson.gestaopedidos.dto;

import java.math.BigDecimal;

public class ProdutoDTO {

    private Long id;
    private String nome;
    private BigDecimal preco;
    private boolean ativo;

    public ProdutoDTO(Long id, String nome, BigDecimal preco, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.ativo = ativo;
    }

    public Long getId() {return id;}

    public String getNome() {return nome;}

    public BigDecimal getPreco() {return preco;}

    public boolean isAtivo() {return ativo;}
}

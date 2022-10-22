package com.jeanlima.springrestapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeanlima.springrestapi.exception.RegraNegocioException;

@Entity
@Table(name = "estoque")
public class Estoque {
    
    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "produto_id")
    @JsonIgnore
    private Produto produto;

    @Column
private Integer quantidade;

    public Estoque() { }
    
    public Estoque(Produto produto) {
        this.produto = produto;
        this.id = produto.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void subtrair(Integer quantidade) {
        if(quantidade < 0) {
            throw new RegraNegocioException("Quantidade não pode ser negativo.");
        }
        
        Integer total = this.quantidade - quantidade;

        if(total < 0) {
            throw new RegraNegocioException(
                "Estoque de " + this.produto.getDescricao() + 
                " esgotado. " + this.quantidade + " unidades disponíveis.");
        } 

        this.quantidade -= quantidade;
    }

    public void somar(Integer quantidade) {
        if(quantidade < 0) {
            throw new RegraNegocioException("Quantidade não pode ser negativo.");
        }
        this.quantidade += quantidade;
    }

}

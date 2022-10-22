package com.jeanlima.springrestapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeanlima.springrestapi.enums.StatusPedido;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //Um cliente pode ter muitos pedidos!
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column
    private LocalDate dataPedido;

    //1000.00
    @Column(name = "total", precision = 20,scale = 2)
    private BigDecimal total;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedido status;

    public Pedido() {
    }


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public LocalDate getDataPedido() {
        return dataPedido;
    }
    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }
    public List<ItemPedido> getItens() {
        return itens;
    }
    public void setItens(List<ItemPedido> itens) {
        this.itens.clear();
        this.itens.addAll(itens);
    }

    public BigDecimal atualizarTotal() {
        this.total = new BigDecimal(0);
        this.itens.forEach(
            item -> {
                int quantidade = item.getQuantidade();
                BigDecimal preco = item.getProduto().getPreco();
                BigDecimal total = preco.multiply(BigDecimal.valueOf(quantidade));
                this.total = this.total.add(total);
            }
        );
        return this.total;
    }

    public void atualizarEstoque(String tipo) {

        if(tipo == "SOMAR") {
            this.itens.forEach(
                item -> {
                    int quantidade = item.getQuantidade();
                    item.getProduto().getEstoque().somar(quantidade);
                }
            );       
        }

        if(tipo == "SUBTRAIR") {
            this.itens.forEach(
                item -> {
                    int quantidade = item.getQuantidade();
                    item.getProduto().getEstoque().subtrair(quantidade);
                }
            );       
        }

    }

    public StatusPedido getStatus() {
        return status;
    }
    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    
    

    
    
}

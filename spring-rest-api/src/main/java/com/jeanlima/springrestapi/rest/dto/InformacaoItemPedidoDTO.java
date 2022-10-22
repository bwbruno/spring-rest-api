package com.jeanlima.springrestapi.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.jeanlima.springrestapi.model.ItemPedido;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacaoItemPedidoDTO {
    private String descricaoProduto;
    private BigDecimal precoUnitario;
    private Integer quantidade;
    
    public InformacaoItemPedidoDTO(ItemPedido item) {
        this.descricaoProduto = item.getProduto().getDescricao();
        this.precoUnitario = item.getProduto().getPreco();
        this.quantidade = item.getQuantidade();
    }
    
    public static List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens) {
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }

        return itens.stream().map(
                item -> new InformacaoItemPedidoDTO(item)
        ).collect(Collectors.toList());
    }

}

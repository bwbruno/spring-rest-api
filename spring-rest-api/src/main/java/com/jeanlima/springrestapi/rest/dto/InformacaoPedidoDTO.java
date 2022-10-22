package com.jeanlima.springrestapi.rest.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.jeanlima.springrestapi.model.Pedido;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder 
public class InformacaoPedidoDTO {

    private Integer codigo;
    private BigDecimal total;
    private String status;
    private List<InformacaoItemPedidoDTO> items;

    public InformacaoPedidoDTO(Pedido pedido) {
        this.codigo = pedido.getId();
        this.total = pedido.getTotal();
        this.status = pedido.getStatus().name();
        this.items =  InformacaoItemPedidoDTO.converter(pedido.getItens()); 
    }

    public static List<InformacaoPedidoDTO> converter(Set<Pedido> set) {
        if(CollectionUtils.isEmpty(set)){
            return Collections.emptyList();
        }

        return set.stream().map(
                pedido -> new InformacaoPedidoDTO(pedido)
        ).collect(Collectors.toList());
    }
}
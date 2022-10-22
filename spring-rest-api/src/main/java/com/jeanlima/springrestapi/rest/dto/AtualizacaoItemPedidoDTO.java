package com.jeanlima.springrestapi.rest.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizacaoItemPedidoDTO {
    private List<ItemPedidoDTO> novosItens;
}

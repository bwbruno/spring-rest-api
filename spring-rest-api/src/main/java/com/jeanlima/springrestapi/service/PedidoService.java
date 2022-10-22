package com.jeanlima.springrestapi.service;

import java.util.List;
import java.util.Optional;

import com.jeanlima.springrestapi.enums.StatusPedido;
import com.jeanlima.springrestapi.model.Pedido;
import com.jeanlima.springrestapi.rest.dto.ItemPedidoDTO;
import com.jeanlima.springrestapi.rest.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar( PedidoDTO dto );
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
    void excluirPedido(Integer id);    
    List<Pedido> obterTodosOsPedidos();
    Pedido atualizaCliente(Integer id, Integer clienteId);
    Pedido atualizaItens(Integer id, List<ItemPedidoDTO> novosItens);
}

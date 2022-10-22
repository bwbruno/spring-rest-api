
package com.jeanlima.springrestapi.rest.dto;

import lombok.*;

import java.util.List;

import com.jeanlima.springrestapi.model.Cliente;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder 
public class InformacoesClienteDTO {
    
    private Integer id;
    private String cpf;
    private String nome;
    private List<InformacaoPedidoDTO> pedidos;

    public InformacoesClienteDTO(Cliente c) {
        this.id = c.getId();
        this.cpf = c.getCpf();
        this.nome = c.getNome();
        this.pedidos = InformacaoPedidoDTO.converter(c.getPedidos());
    }

}

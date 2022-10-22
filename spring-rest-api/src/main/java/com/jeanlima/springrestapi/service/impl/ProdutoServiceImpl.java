package com.jeanlima.springrestapi.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jeanlima.springrestapi.exception.RegraNegocioException;
import com.jeanlima.springrestapi.model.Estoque;
import com.jeanlima.springrestapi.model.Produto;
import com.jeanlima.springrestapi.repository.EstoqueRepository;
import com.jeanlima.springrestapi.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl {
    
    private final ProdutoRepository repository;
    private final EstoqueRepository estoqueRepository;

    public Produto findById(Integer id) {
        return repository
                    .findById(id)
                    .orElseThrow(
                        () -> new RegraNegocioException("Código de produto inválido: "+ id)
                    );
    }

    @Transactional
    public Produto save(Produto p) {
        Produto produto = repository.save(p);  
        Estoque estoque = new Estoque(produto);
        estoque.setQuantidade(0);
        estoqueRepository.save(estoque);
        return produto; 
    }

    public void remove(Integer id) {
        repository.deleteById(id);
    }

}

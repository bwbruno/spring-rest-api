package com.jeanlima.springrestapi.rest.controllers;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.jeanlima.springrestapi.model.Estoque;
import com.jeanlima.springrestapi.model.Produto;
import com.jeanlima.springrestapi.repository.EstoqueRepository;
import com.jeanlima.springrestapi.repository.ProdutoRepository;
import com.jeanlima.springrestapi.rest.dto.EstoqueDTO;

@RequestMapping("/api/estoques")
@RestController //anotação especializadas de controller - todos já anotados com response body!
public class EstoqueController {

    @Autowired
    private EstoqueRepository estoques;

    @Autowired
    private ProdutoRepository produtos;

    @GetMapping
    public List<Estoque> find(Estoque filtro, @RequestParam Optional<String> nomeProduto){
        if(nomeProduto.isPresent()) {
            return estoques.findByNomeProduto(nomeProduto.get());
        }

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING );

        Example example = Example.of(filtro, matcher);
        return estoques.findAll(example);
    }

    @GetMapping("{id}")
    public Estoque getEstoqueById(@PathVariable Integer id){
        return estoques
                .findById(id)
                .orElseThrow(() -> //se nao achar lança o erro!
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Estoque não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estoque save(@RequestBody EstoqueDTO estoqueDto){
        
        Produto produto = produtos
            .findById(estoqueDto.getProduto())
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estoque não encontrado")
            );

        Estoque estoque = new Estoque(produto);
        estoque.setQuantidade(estoqueDto.getQuantidade());

        return estoques.save(estoque);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        // Não existe Produto sem Estoque.
        // Se apagar Produto, apaga o estoque e vice versa.
        produtos.deleteById(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Integer id, @RequestBody EstoqueDTO estoqueDto ){
        estoques
            .findById(id)
            .map(e -> {
                e.setQuantidade(estoqueDto.getQuantidade());
                estoques.save(e);
                return e;
            })
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Estoque não encontrado")
            );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Estoque> updatePartially(@PathVariable int id, @RequestBody Map<String, Object> fields, UriComponentsBuilder uriBuilder) {

        Optional<Estoque> estoque = estoques.findById(id);

        if (estoque.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Estoque.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, estoque.get(), value);
            });
            estoques.save(estoque.get());

            URI uri = uriBuilder
                            .path("/api/estoques/{id}")
                            .buildAndExpand(estoque.get().getId()).toUri();

            return ResponseEntity.created(uri).body(estoque.get());
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

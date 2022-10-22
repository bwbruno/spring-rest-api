package com.jeanlima.springrestapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jeanlima.springrestapi.model.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque,Integer>{

    @Query(value = "SELECT * FROM estoque e LEFT JOIN produto p ON e.produto_id = p.id WHERE p.descricao = :nome", nativeQuery = true)
    List<Estoque> findByNomeProduto(@Param("nome") String nome);

}

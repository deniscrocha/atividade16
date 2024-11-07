package com.denis.atividade16.controllers;

import com.denis.atividade16.DTO.ProdutoDTO;
import com.denis.atividade16.models.Produto;
import com.denis.atividade16.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
  @Autowired
  private ProdutoService service;

  @GetMapping
  public ResponseEntity<List<Produto>> readAllProdutos(){
    return service.readAllProdutos();
  }
  @GetMapping("/{id}")
  public ResponseEntity<Produto> readProduto(@PathVariable Long id){
    return service.readProduto(id);
  }
  @PostMapping
  public ResponseEntity<Produto> createProduto(@RequestBody ProdutoDTO dto){
    return service.createProduto(dto);
  }
  @PutMapping("/{id}")
  public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody ProdutoDTO dto){
    return service.updateProduto(id, dto);
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<Produto> deleteProduto(@PathVariable Long id){
    return service.deleteProduto(id);
  }
}
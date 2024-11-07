package com.denis.atividade16.services;

import com.denis.atividade16.DTO.ProdutoDTO;
import com.denis.atividade16.models.Produto;
import com.denis.atividade16.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
  @Autowired
  private ProdutoRepository repository;
  public ResponseEntity<Produto> createProduto(ProdutoDTO dto){
    Produto produto = new Produto();
    produto.setNome(dto.getNome());
    produto.setDescricao(dto.getDescricao());
    produto.setPreco(dto.getPreco());
    produto.setQtdEstoque(dto.getQtdEstoque());
    return ResponseEntity.ok(produto);
  }
  public ResponseEntity<List<Produto>> readAllProdutos(){
    return ResponseEntity.ok(repository.findAll());
  }
  public ResponseEntity<Produto> readProduto(Long id){
    Optional<Produto> produtoOptional = repository.findById(id);
    if(produtoOptional.isEmpty()) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(produtoOptional.get());
  }
  public ResponseEntity<Produto> updateProduto(Long id, ProdutoDTO dto){
    Optional<Produto> produtoOptional = repository.findById(id);
    if(produtoOptional.isEmpty()) return ResponseEntity.notFound().build();
    Produto produto = produtoOptional.get();
    produto.setNome(dto.getNome());
    produto.setDescricao(dto.getDescricao());
    produto.setPreco(dto.getPreco());
    produto.setQtdEstoque(dto.getQtdEstoque());
    repository.save(produto);
    return ResponseEntity.ok(produto);
  }
  public ResponseEntity<Produto> deleteProduto(Long id){
    Optional<Produto> produtoOptional = repository.findById(id);
    if(produtoOptional.isEmpty()) return ResponseEntity.notFound().build();
    repository.delete(produtoOptional.get());
    return ResponseEntity.ok().build();
  }
}
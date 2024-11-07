package com.denis.atividade16.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
  private String nome;
  private String descricao;
  private float preco;
  private int qtdEstoque;
}

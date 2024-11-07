package com.denis.atividade16;

import com.denis.atividade16.DTO.ProdutoDTO;
import com.denis.atividade16.controllers.ProdutoController;
import com.denis.atividade16.models.Produto;
import com.denis.atividade16.services.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTests {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private ProdutoService service;
  @InjectMocks
  private ProdutoController controller;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testGetAllProdutos() throws Exception{
    Produto produto = new Produto();
    produto.setId(1L);
    produto.setNome("Litrão de Coca");
    produto.setDescricao("Refrigerante Coca Cola de 1 litro");
    produto.setPreco(5.90f);
    produto.setQtdEstoque(5);
    when(service.readAllProdutos()).thenReturn(ResponseEntity.ok(List.of(produto)));
    mockMvc.perform(get("/produtos"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].nome").value("Litrão de Coca"));
  }
  @Test
  public void testCreateProduto() throws Exception{
    Produto produto = new Produto();
    produto.setId(1L);
    produto.setNome("Litrão de Coca");
    produto.setDescricao("Refrigerante Coca Cola de 1 litro");
    produto.setPreco(5.90f);
    produto.setQtdEstoque(5);
    ProdutoDTO dto = new ProdutoDTO(produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getQtdEstoque());
    when(service.createProduto(dto)).thenReturn(ResponseEntity.ok(produto));
    mockMvc.perform(post("/produtos").content(objectMapper.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.nome").value("Litrão de Coca"));
  }
  @Test
  public void testCreateProdutoWithoutValues() throws Exception{
    Produto produto = new Produto();
    produto.setId(1L);
    produto.setNome("Litrão de Coca");
    produto.setDescricao("Refrigerante Coca Cola de 1 litro");
    produto.setQtdEstoque(5);
    ProdutoDTO dto = new ProdutoDTO(produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getQtdEstoque());
    when(service.createProduto(dto)).thenReturn(ResponseEntity.ok(produto));
    mockMvc.perform(post("/produtos"))
            .andExpect(status().isBadRequest());
  }
  @Test
  public void testGetProdutoById() throws Exception{
    Produto produto = new Produto();
    produto.setId(1L);
    produto.setNome("Litrão de Coca");
    produto.setDescricao("Refrigerante Coca Cola de 1 litro");
    produto.setPreco(5.90f);
    produto.setQtdEstoque(5);
    when(service.readProduto(1l)).thenReturn(ResponseEntity.ok(produto));
    mockMvc.perform(get("/produtos/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.nome").value("Litrão de Coca"));
  }
  @Test
  public void testGetProdutoByIdWithoutIdExists() throws Exception{
    when(service.readProduto(1l)).thenReturn(ResponseEntity.notFound().build());
    mockMvc.perform(get("/produtos/1"))
            .andExpect(status().isNotFound());
  }
  @Test
  public void testUpdateProduto() throws Exception{
    Produto produto = new Produto();
    produto.setId(1L);
    produto.setNome("Litrão de Coca");
    produto.setDescricao("Refrigerante Coca Cola de 1 litro");
    produto.setPreco(5.90f);
    produto.setQtdEstoque(5);
    ProdutoDTO dto = new ProdutoDTO("Litrão de Coca", "Refrigerante Coca Cola de 1 litro", 5.90f, 5);
    when(service.updateProduto(1l, dto)).thenReturn(ResponseEntity.ok(produto));
    mockMvc.perform(put("/produtos/1").content(objectMapper.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.nome").value("Litrão de Coca"));
  }
  @Test
  public void testUpdateProdutoWithoutValues() throws Exception{
    Produto produto = new Produto();
    ProdutoDTO dto = new ProdutoDTO("Litrão de Coca", "Refrigerante Coca Cola de 1 litro", produto.getPreco(), 5);
    when(service.updateProduto(1l, dto)).thenReturn(ResponseEntity.badRequest().build());
    mockMvc.perform(put("/produtos/1").content(objectMapper.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
  }
  @Test
  public void testUpdateProdutoWithoutOriginalProdutoExists() throws Exception{
    ProdutoDTO dto = new ProdutoDTO("Litrão de Coca", "Refrigerante Coca Cola de 1 litro", 5.90f, 5);
    when(service.updateProduto(1l, dto)).thenReturn(ResponseEntity.notFound().build());
    mockMvc.perform(put("/produtos/1").content(objectMapper.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }
  @Test
  public void testDeleteProduto() throws Exception{
    when(service.deleteProduto(1l)).thenReturn(ResponseEntity.ok().build());
    mockMvc.perform(delete("/produtos/1"))
            .andExpect(status().isOk());
  }
  @Test
  public void testDeleteProdutoWithouExists() throws Exception{
    when(service.deleteProduto(1l)).thenReturn(ResponseEntity.notFound().build());
    mockMvc.perform(delete("/produtos/1"))
            .andExpect(status().isNotFound());
  }
}

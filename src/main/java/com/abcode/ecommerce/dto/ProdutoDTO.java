package com.abcode.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO {

    private Integer id;
    private String nome;

    public ProdutoDTO(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}

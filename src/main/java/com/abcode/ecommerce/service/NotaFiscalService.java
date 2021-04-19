package com.abcode.ecommerce.service;

import com.abcode.ecommerce.model.Pedido;

public class NotaFiscalService {

    public void gerar(Pedido pedido){
        System.out.println("Gerando nota para o pedido " + pedido.getId() + ".");
    }
}

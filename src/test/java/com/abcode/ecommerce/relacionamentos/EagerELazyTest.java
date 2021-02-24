package com.abcode.ecommerce.relacionamentos;

import com.abcode.ecommerce.iniciandocomjpa.EntityManagerTest;
import com.abcode.ecommerce.model.Pedido;
import org.junit.Test;

public class EagerELazyTest extends EntityManagerTest {

    @Test
    public void  verificarComportamento(){
        Pedido pedido = entityManager.find(Pedido.class, 1);

        pedido.getItens().isEmpty();
    }
}

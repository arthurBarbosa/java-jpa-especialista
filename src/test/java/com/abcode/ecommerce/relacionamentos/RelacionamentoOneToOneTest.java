package com.abcode.ecommerce.relacionamentos;

import com.abcode.ecommerce.iniciandocomjpa.EntityManagerTest;
import com.abcode.ecommerce.model.NotaFiscal;
import com.abcode.ecommerce.model.PagamentoCartao;
import com.abcode.ecommerce.model.Pedido;
import com.abcode.ecommerce.model.StatusPagamento;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class RelacionamentoOneToOneTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamento() {
        Pedido pedido = entityManager.find(Pedido.class, 1);

        PagamentoCartao pagamentoCartao = new PagamentoCartao();
        pagamentoCartao.setNumero("1234");
        pagamentoCartao.setStatus(StatusPagamento.PROCESSANDO);
        pagamentoCartao.setPedido(pedido);

        entityManager.getTransaction().begin();

        entityManager.persist(pagamentoCartao);

        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificação = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificação.getPagamento());

    }

    @Test
    public void verificarRelacionamentoPedidoNotaFiscal() {
        Pedido pedido = entityManager.find(Pedido.class, 1);

        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setXml("TESTE".getBytes(StandardCharsets.UTF_8));
        notaFiscal.setDataEmissao(new Date());
        notaFiscal.setPedido(pedido);

        entityManager.getTransaction().begin();
        entityManager.persist(notaFiscal);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificacao.getNotaFiscal());
    }


}

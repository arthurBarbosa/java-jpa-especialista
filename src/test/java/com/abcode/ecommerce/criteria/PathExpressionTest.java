package com.abcode.ecommerce.criteria;

import com.abcode.ecommerce.iniciandocomjpa.EntityManagerTest;
import com.abcode.ecommerce.model.*;
import com.abcode.ecommerce.model.Cliente_;
import com.abcode.ecommerce.model.ItemPedido_;
import com.abcode.ecommerce.model.Pedido_;
import com.abcode.ecommerce.model.Produto_;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class PathExpressionTest extends EntityManagerTest {

    @Test
    public void usandoPathExpression() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        criteriaQuery.where(
                criteriaBuilder.like(root.get(Pedido_.CLIENTE).get(Cliente_.NOME), "M%"));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void buscarPedidosComProdutoDeIDIgual1Exercicio() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        Join<Pedido, ItemPedido> itemPedidoJoin = root.join(Pedido_.ITENS);

        criteriaQuery.select(root);

        criteriaQuery.where(
                criteriaBuilder.equal(
                        itemPedidoJoin.get(ItemPedido_.PRODUTO).get(Produto_.ID), 1));
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

    }


}

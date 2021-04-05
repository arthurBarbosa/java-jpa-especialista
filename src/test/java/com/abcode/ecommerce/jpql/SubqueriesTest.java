package com.abcode.ecommerce.jpql;

import com.abcode.ecommerce.iniciandocomjpa.EntityManagerTest;
import com.abcode.ecommerce.model.Cliente;
import com.abcode.ecommerce.model.Pedido;
import com.abcode.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class SubqueriesTest extends EntityManagerTest {

    @Test
    public void pesquisarSubqueries() {
        String jpql = "select p from Produto p where " +
                "p.preco = (select max (preco) from Produto )";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(p -> System.out.println("ID " + p.getId() + " Preço " + p.getPreco()));
    }

    @Test
    public void pesquisarPedidoAcimaDaMedia() {
        String jpql = "select p from Pedido p where " +
                "p.total > (select avg (total) from Pedido)";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(p -> System.out.println("ID " + p.getId() + " total do pedido " + p.getTotal()));
    }

    @Test
    public void pesquisarClientesComMaisPedidos() {
        String jpql = "select c from Cliente c where " +
                " 500 < (select sum(p.total) from c.pedidos p)";

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);
        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID " + obj.getId() + " Nome do cliente " + obj.getNome()));
    }

    @Test
    public void pesquisarClientesComMaisPedidosUsandoObjCliente() {
        String jpql = "select c from Cliente c where " +
                " 500 < (select sum(p.total) from Pedido p where p.cliente = c)";

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);
        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID " + obj.getId() + " Nome do cliente " + obj.getNome()));
    }

    @Test
    public void pesquisarPedidosComIn() {
        String jpql = "select p from Pedido p where p.id in " +
                " (select p2.id from ItemPedido i2 " +
                " join i2.pedido p2 join i2.produto pro2 where pro2.preco > 100)";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID " + obj.getId() + " total pedido " + obj.getTotal() + " nome do cliente " + obj.getCliente().getNome()));
    }

    @Test
    public void pesquisarPedidosComExists() {
        String jpql = "select p from Produto p where exists " +
                " (select 1 from ItemPedido itemPedido join itemPedido.produto produto where produto = p )";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID " + obj.getId() + " total pedido " + obj.getNome()));
    }

    @Test
    public void pesquisarPedidosComNotExists() {
        String jpql = "select p from Produto p where not exists " +
                " (select 1 from ItemPedido itemPedido join itemPedido.produto produto where produto = p )";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        List<Produto> lista = typedQuery.getResultList();
        Assert.assertTrue(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID " + obj.getId() + " total pedido " + obj.getNome()));
    }

    @Test
    public void pesquisarPedidosComAll() {
        String jpql = "select p from Produto p where " +
                " p.preco = ALL (select precoProduto from ItemPedido where produto = p)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID " + obj.getId() + " total pedido " + obj.getNome()));
    }

    @Test
    public void pesquisarPedidosComAny() {
        String jpql = "select p from Produto p " +
                "where p.preco = ANY (select precoProduto from ItemPedido where produto = p)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID " + obj.getId() + " total pedido " + obj.getNome()));
    }
}

package com.abcode.ecommerce.jpql;

import com.abcode.ecommerce.iniciandocomjpa.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class GroupByTest extends EntityManagerTest {

    @Test
    public void agruparResultado() {
        String jpql = "select c.nome, count(c.id) from Categoria c join c.produtos p group by c.id";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void agruparTotalVendasResultado() {
        String jpql = "select concat(year(p.dataCriacao), function('monthname', p.dataCriacao)), sum (p.total) " +
                "from Pedido p " +
                "group by year(p.dataCriacao), month(p.dataCriacao)";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void totaldeVendasPorCategoria() {

        String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
                " join ip.produto pro join pro.categorias c " +
                " group by c.id";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void totaldeVendasPorCliente() {

        String jpql = "select c.nome, sum (ip.precoProduto) from ItemPedido ip " +
                "join ip.pedido  p  join p.cliente c " +
                "group by c.id";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void totaldeVendasPorDiaECategoria() {

        String jpql = "select " +
                "concat(year(p.dataCriacao), '/', month(p.dataCriacao), '/', day(p.dataCriacao))," +
                "concat(c.nome, ': ', sum (ip.precoProduto)) " +
                "from ItemPedido ip " +
                "join ip.pedido p " +
                "join ip.produto pro " +
                "join pro.categorias c " +
                "group by year(p.dataCriacao), month(p.dataCriacao)";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }
}

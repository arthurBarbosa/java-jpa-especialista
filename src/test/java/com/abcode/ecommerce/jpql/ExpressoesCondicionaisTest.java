package com.abcode.ecommerce.jpql;

import com.abcode.ecommerce.iniciandocomjpa.EntityManagerTest;
import com.abcode.ecommerce.model.Cliente;
import com.abcode.ecommerce.model.NotaFiscal;
import com.abcode.ecommerce.model.Pedido;
import com.abcode.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ExpressoesCondicionaisTest extends EntityManagerTest {

    @Test
    public void usarExpressaoCondicionalLike() {
        String jpql = "select c from Cliente c where c.nome like concat('%', :nome, '%')";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        typedQuery.setParameter("nome", "a");

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarIsEmpty() {
        String jpql = "select p from Produto p where p.categorias is empty";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usandoIsNull() {
        String jpql = "select p from Produto p where p.foto is null";


        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarMaiorMenor() {
        String jpql = "select p from Produto p " +
                "where p.preco >= :precoInicial and p.preco <= :precoFinal";
        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
        typedQuery.setParameter("precoInicial", new BigDecimal(400));
        typedQuery.setParameter("precoFinal", new BigDecimal(1500));

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarMaiorMenorComDatas() {
        String jpql = "select p from Pedido p where p.dataCriacao > :data";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        typedQuery.setParameter("data", LocalDateTime.now().minusDays(2));

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarBetween() {
        String jpql = "select p from Pedido p " +
                "where p.dataCriacao between :dataInicial and :dataFinal";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        typedQuery.setParameter("dataInicial", LocalDateTime.now().minusDays(10));
        typedQuery.setParameter("dataFinal", LocalDateTime.now());

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarExpressaoDiferente() {
        String jpql = "select p from Produto p where p.preco <> 100";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarExpressaoCase() {
        String jpql = "select p.id, " +
                "case p.status " +
                "when 'PAGO' then 'Está pago' " +
                "when 'CANCELADO' then 'Esta cancelado' " +
                "else 'Está aguardando' " +
                "end " +
                "from Pedido p";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void usarExpressaoIn() {
        List<Integer> paramentros = Arrays.asList(1, 2, 3);

        String jpql = "select p from Pedido  p where p.id in (:lista)";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        typedQuery.setParameter("lista", paramentros);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarExpressaoInCliente() {
        Cliente cliente1 = entityManager.find(Cliente.class, 1);
        Cliente cliente2 = entityManager.find(Cliente.class, 2);

        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        String jpql = "select p from Pedido  p where p.cliente in (:clientes)";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        typedQuery.setParameter("clientes", clientes);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarDistinct(){
        String jpql = "select distinct p from Pedido p " +
                " join p.itens i join i.produto pro " +
                " where pro.id in (1, 2, 3, 4) ";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        System.out.println(lista.size());
    }


}

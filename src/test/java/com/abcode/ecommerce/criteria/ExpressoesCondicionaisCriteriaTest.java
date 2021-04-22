package com.abcode.ecommerce.criteria;

import com.abcode.ecommerce.iniciandocomjpa.EntityManagerTest;
import com.abcode.ecommerce.model.*;
import com.abcode.ecommerce.model.Cliente_;
import com.abcode.ecommerce.model.Pedido_;
import com.abcode.ecommerce.model.Produto_;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ExpressoesCondicionaisCriteriaTest extends EntityManagerTest {

    @Test
    public void usarExpressaoCondicionalLike() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.like(root.get(Cliente_.NOME), "%a%"));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarIsNull() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.isNull(root.get(Produto_.FOTO)));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

    }

    @Test
    public void usarIsNullProdutoSemCategoria() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.isEmpty(root.get(Produto_.CATEGORIAS)));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarIsNullProdutoComCategoria() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.isNotEmpty(root.get(Produto_.CATEGORIAS)));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarMaiorMenor() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(root.get(Produto_.PRECO), new BigDecimal(799)));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
        for (Produto produto : lista) {
            System.out.println("Id " + produto.getId() + " nome: " + produto.getNome() + " preço " + produto.getPreco());
        }
    }

    @Test
    public void usarMenor() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder
                .lessThan(root.get(Produto_.PRECO), new BigDecimal(3500)));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
        for (Produto produto : lista) {
            System.out.println("Id " + produto.getId() + " nome: " + produto.getNome() + " preço " + produto.getPreco());
        }
    }

    @Test
    public void usarMenorEAnd() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        criteriaQuery.where(
                criteriaBuilder
                        .greaterThan(root.get(Produto_.PRECO), new BigDecimal(799)),
                criteriaBuilder
                        .lessThan(root.get(Produto_.PRECO), new BigDecimal(3500)));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
        for (Produto produto : lista) {
            System.out.println("Id " + produto.getId() + " nome: " + produto.getNome() + " preço " + produto.getPreco());
        }
    }

    @Test
    public void usarMaiorMenorComDate() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        criteriaQuery.where(
                criteriaBuilder.greaterThanOrEqualTo(
                        root.get(Pedido_.DATA_CRIACAO), LocalDateTime.now().minusDays(3)));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
        lista.forEach(p -> System.out.println(p));
    }

    @Test
    public void usarBetween() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.between(
                root.get(Pedido_.TOTAL), new BigDecimal(499), new BigDecimal(2398)));


    }

    @Test
    public void betweenData() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.between(root.get(Pedido_.DATA_CRIACAO),
                LocalDateTime.now().minusDays(5).withSecond(0).withMinute(0).withHour(0),
                LocalDateTime.now()));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
        lista.forEach(p -> System.out.println("Id: " + p.getId() + " total " + p.getTotal()));
    }

    @Test
    public void usandoExpressaoNot() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.notEqual(root.get(Pedido_.TOTAL), new BigDecimal(499)));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
        lista.forEach(p -> System.out.println("Id: " + p.getId() + " total " + p.getTotal()));
    }
}

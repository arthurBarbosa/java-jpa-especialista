package com.abcode.ecommerce.criteria;

import com.abcode.ecommerce.iniciandocomjpa.EntityManagerTest;
import com.abcode.ecommerce.model.*;
import com.abcode.ecommerce.model.Categoria_;
import com.abcode.ecommerce.model.Cliente_;
import com.abcode.ecommerce.model.ItemPedido_;
import com.abcode.ecommerce.model.Pedido_;
import com.abcode.ecommerce.model.Produto_;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class GroupByCriteriaTest extends EntityManagerTest {

    @Test
    public void agruparResultado01() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);
        Join<ItemPedido, Produto> joinProduto = root.join(ItemPedido_.PRODUTO);
        Join<Produto, Categoria> produtoCategoriaJoin = joinProduto.join(Produto_.CATEGORIAS);

        criteriaQuery.multiselect(
                produtoCategoriaJoin.get(Categoria_.NOME),
                criteriaBuilder.sum(root.get(ItemPedido_.PRECO_PRODUTO)));

        criteriaQuery.groupBy(produtoCategoriaJoin.get(Categoria_.ID));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();
        lista.forEach(arr -> System.out.println("Nome: " + arr[0] + ", sum: " + arr[1]));
    }

    @Test
    public void agruparResultado02() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Categoria> root = criteriaQuery.from(Categoria.class);
        Join<Categoria, Produto> joinProduto = root.join(Categoria_.PRODUTOS, JoinType.LEFT);

        criteriaQuery.multiselect(
                root.get(Categoria_.NOME),
                criteriaBuilder.count(joinProduto.get(Produto_.ID)));

        criteriaQuery.groupBy(root.get(Categoria_.ID));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();
        lista.forEach(arr -> System.out.println("Nome: " + arr[0] + ", count: " + arr[1]));
    }

    @Test
    public void totalVendasCliente() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);
        Join<ItemPedido, Pedido> joinProduto = root.join(ItemPedido_.PEDIDO);
        Join<ItemPedido, Cliente> itemPedidoClienteJoin = joinProduto.join(Pedido_.CLIENTE);

        criteriaQuery.multiselect(
                itemPedidoClienteJoin.get(Cliente_.NOME),
                criteriaBuilder.sum(root.get(ItemPedido_.PRECO_PRODUTO)));

        criteriaQuery.groupBy(itemPedidoClienteJoin.get(Cliente_.ID));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();
        lista.forEach(arr -> System.out.println("Nome cliente: " + arr[0] + ", Sum: " + arr[1]));
    }

}

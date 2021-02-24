package com.abcode.ecommerce.jpql;

import com.abcode.ecommerce.iniciandocomjpa.EntityManagerTest;
import com.abcode.ecommerce.model.Cliente;
import com.abcode.ecommerce.model.Pedido;
import com.abcode.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class BasicoJPQLTest extends EntityManagerTest {

    @Test
    public void buscarPorIdentificador() {
        TypedQuery<Pedido> typedQuery = entityManager.createQuery("select p from Pedido p where p.id = 1", Pedido.class);
        Pedido pedido = typedQuery.getSingleResult();
        Assert.assertNotNull(pedido);
    }

    @Test
    public void selecionarUmAtributoParaRetorno() {
        String jpql = "select p.nome from Produto p";
        TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);
        List<String> lista = typedQuery.getResultList();

        Assert.assertTrue(String.class.equals(lista.get(0).getClass()));

        String jpqlCliente = "select p.cliente from Pedido p";
        TypedQuery<Cliente> clienteTypedQuery = entityManager.createQuery(jpqlCliente, Cliente.class);
        List<Cliente> listaClientes = clienteTypedQuery.getResultList();
        Assert.assertTrue(Cliente.class.equals(listaClientes.get(0).getClass()));
    }

    @Test
    public void projetarResultado() {
        String jpql = "select id, nome from Produto";
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertTrue(lista.get(0).length == 2);

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }
}

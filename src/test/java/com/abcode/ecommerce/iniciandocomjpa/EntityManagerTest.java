package com.abcode.ecommerce.iniciandocomjpa;

import com.abcode.ecommerce.model.Produto;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerTest {

    protected static EntityManagerFactory entityManagerFactory;

    protected EntityManager entityManager;

    @BeforeClass
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        entityManagerFactory.close();
    }

    @Before
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() {
        entityManager.close();
    }

    @Test
    public void buscarPeloIdentificador(){
        Produto produto = entityManager.find(Produto.class, 1);
//        Produto produto = entityManager.getReference(Produto.class, 1);
        Assert.assertNotNull(produto);
        Assert.assertEquals("Kindle", produto.getNome());
    }

    @Test
    public void atualizaAReferencia(){
        Produto produto = entityManager.find(Produto.class, 1);
        produto.setNome("Microfone Samson");

        entityManager.refresh(produto);

        Assert.assertEquals("Kindle", produto.getNome());
    }


}

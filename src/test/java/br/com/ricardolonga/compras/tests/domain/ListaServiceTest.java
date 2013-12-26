package br.com.ricardolonga.compras.tests.domain;

import javax.inject.Inject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.ricardolonga.compras.domain.builders.ListaBuilder;
import br.com.ricardolonga.compras.domain.model.entities.Lista;
import br.com.ricardolonga.compras.domain.model.valueobjects.Descricao;
import br.com.ricardolonga.compras.domain.repositories.IListaRepository;
import br.com.ricardolonga.compras.domain.repositories.IRepository;
import br.com.ricardolonga.compras.infrastructure.persistence.jpa.GenericDAO;
import br.com.ricardolonga.compras.infrastructure.persistence.jpa.ListaDAO;
import br.com.ricardolonga.compras.infrastructure.producers.LoggerProducer;

@RunWith(Arquillian.class)
public class ListaServiceTest {

    @Inject
    IListaRepository listaRepository;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class) //
                .addPackages(true, Lista.class.getPackage(), Descricao.class.getPackage(), LoggerProducer.class.getPackage(), EqualsBuilder.class.getPackage()) //
                .addClasses(ListaDAO.class, ListaBuilder.class, GenericDAO.class, IListaRepository.class, IRepository.class) //
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml") //
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void deve_injetar() {
        Assert.assertNotNull(listaRepository);
    }

    @Test
    public void guardar_um_novo_item() {
        Lista lista = ListaBuilder.create().descricao("Petshop").build();
        listaRepository.persist(lista);
    }

}

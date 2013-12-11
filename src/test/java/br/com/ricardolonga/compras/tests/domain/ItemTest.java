package br.com.ricardolonga.compras.tests.domain;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.ricardolonga.compras.domain.model.entities.Item;
import br.com.ricardolonga.compras.domain.model.valueobjects.Descricao;
import br.com.ricardolonga.compras.domain.repositories.IGenericRepository;
import br.com.ricardolonga.compras.domain.services.ItemService;
import br.com.ricardolonga.compras.infrastructure.persistence.jpa.GenericDAO;
import br.com.ricardolonga.compras.infrastructure.persistence.jpa.ItemDAO;
import br.com.ricardolonga.compras.infrastructure.producers.LoggerProducer;

@RunWith(Arquillian.class)
public class ItemTest {

    @Inject
    private ItemService itemService;

    @Inject
    ItemDAO itemRepository;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class) //
                .addPackages(true, Item.class.getPackage(), Descricao.class.getPackage(), LoggerProducer.class.getPackage()) //
                .addClass(ItemService.class) //
                .addClasses(ItemDAO.class, GenericDAO.class, IGenericRepository.class) //
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml") //
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    // @Test
    // public void deve_injetar() {
    // Assert.assertNotNull(itemService);
    // }
    //
    // @Test
    // public void criar_um_novo_item_completo() {
    // Item item = Item.newInstance();
    //
    // item.setDescricao(Descricao.newInstance("Desodorante"));
    // item.setValorUnitario(Valor.newInstance(new BigDecimal(4.86)));
    //
    // Assert.assertNotNull(item);
    //
    // Assert.assertEquals("Desodorante", item.getDescricao().getTexto());
    // Assert.assertEquals(new BigDecimal(4.86), item.getValorUnitario().getValor());
    // }

    @Test
    public void guardar_um_novo_item() {
        Item item = Item.newInstance();
        itemRepository.persist(item);
    }

}

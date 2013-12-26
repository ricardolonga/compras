package br.com.ricardolonga.compras.application.controller;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;

import br.com.ricardolonga.compras.application.controller.shared.AbstractPaginationController;
import br.com.ricardolonga.compras.application.utils.UIParameterBuilder;
import br.com.ricardolonga.compras.domain.model.entities.Item;
import br.com.ricardolonga.compras.domain.model.entities.Lista;
import br.com.ricardolonga.compras.domain.repositories.IListaRepository;
import br.com.ricardolonga.compras.domain.repositories.IRepository;

@Named
@ConversationScoped
public class ListaController extends AbstractPaginationController<Lista> {

    private static final long serialVersionUID = 1L;

    @Inject
    private Conversation conversation;

    @Inject
    private IListaRepository listaRepository;

    protected Long id;

    private Lista lista;

    private final Lista filtro = new Lista();

    private Item itemASerAdicionado = new Item();

    private final Item itemSelecionado = new Item();

    /** Por padrão a grid não é apresentada. */
    private boolean renderizarGridParaAdicionarItem = false;

    // ============= //
    // Controladores //
    // ============= //

    public void novo() {
        this.conversation.begin();
        redirect("nova-lista");
    }

    public void retrieve() {
        if (FacesContext.getCurrentInstance().isPostback()) {
            return;
        }

        if (this.conversation.isTransient()) {
            this.conversation.begin();
        }

        if (id == null) {
            this.lista = this.filtro;
        } else {
            this.lista = findById(getId());
        }
    }

    public Lista findById(Long id) {
        return listaRepository.findById(id);
    }

    public void atualizar() {
        this.conversation.end();

        try {
            listaRepository.persist(lista);

            if (id == null) {
                addInfoMessage(getFromBundle("msg.cadastro_realizado_com_sucesso"));
                redirect("visualizar-listas");
            } else {
                addInfoMessage(getFromBundle("msg.atualizacao_realizada_com_sucesso"));
                redirect("visualizar-lista", UIParameterBuilder.create().id("id").value(this.lista.getId()).build());
            }
        } catch (Exception e) {
            addErrorMessage(e.getMessage());
        }
    }

    public void excluir() {
        this.conversation.end();

        try {
            Lista deletableEntity = findById(getId());
            listaRepository.remove(deletableEntity);
            redirect("visualizar-listas");
        } catch (Exception e) {
            logger.error(e);
            addErrorMessage(getFromBundle("msg.erro_ao_excluir"));
        }
    }

    public void iniciarCriacaoNovoItem() {
        itemASerAdicionado = new Item();
        itemASerAdicionado.setLista(lista);
        showGridNovoItem();
    }

    public void confirmarNovoItem() {
        lista.getItens().add(itemASerAdicionado);
        hideGridNovoItem();
    }

    public void cancelarNovoItem() {
        hideGridNovoItem();
    }

    public void editarItem(Item item) {
        itemASerAdicionado = item;
        showGridNovoItem();
    }

    public void itemAdicionadoNoCarrinho(Item itemAdicionadoNoCarrinho) {
        this.conversation.end();

        lista.getItens().remove(itemAdicionadoNoCarrinho);
        listaRepository.persist(lista);
        redirect("visualizar-lista", UIParameterBuilder.create().id("id").value(lista.getId()).build());
    }

    public void deletarItem(Item item) {
        lista.getItens().remove(item);
    }

    private void showGridNovoItem() {
        renderizarGridParaAdicionarItem = true;
    }

    private void hideGridNovoItem() {
        renderizarGridParaAdicionarItem = false;
    }

    public void paginate() {
        super.paginate(filtro);
    }

    @Override
    protected void adicionarFiltros(StringBuilder query) {
        if (filtro.temDescricao()) {
            query.append(" where lower(e.descricao.texto) like :descricao");
        }
    }

    @Override
    protected void adicionarParametros(Query query) {
        if (filtro.temDescricao()) {
            query.setParameter("descricao", "%" + filtro.getDescricao().getTexto().toLowerCase() + "%");
        }
    }

    // ========= //
    // Acessores //
    // ==========//

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lista getLista() {
        return this.lista;
    }

    public Lista getFiltro() {
        return this.filtro;
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected IRepository getRepository() {
        return listaRepository;
    }

    public Item getItemSelecionado() {
        return this.itemSelecionado;
    }

    public boolean getRenderizarGridParaAdicionarItem() {
        return renderizarGridParaAdicionarItem;
    }

    public Item getItemASerAdicionado() {
        return this.itemASerAdicionado;
    }

    public void setItemASerAdicionado(Item itemASerAdicionado) {
        this.itemASerAdicionado = itemASerAdicionado;
    }

}
package br.com.ricardolonga.compras.application.controller;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;

import org.primefaces.model.UploadedFile;

import br.com.ricardolonga.compras.application.controller.shared.AbstractPaginationController;
import br.com.ricardolonga.compras.application.utils.UIParameterBuilder;
import br.com.ricardolonga.compras.domain.model.entities.Produto;
import br.com.ricardolonga.compras.domain.model.valueobjects.Imagem;
import br.com.ricardolonga.compras.domain.repositories.IProdutoRepository;
import br.com.ricardolonga.compras.domain.repositories.IRepository;

@Named
@ConversationScoped
public class ProdutoController extends AbstractPaginationController<Produto> {

    private static final long serialVersionUID = 1L;

    @Inject
    private Conversation conversation;

    @Inject
    private IProdutoRepository produtoRepository;

    protected Long id;

    private Produto produto;

    private UploadedFile imagemCarregada;

    private final Produto filtro = new Produto();

    // ============= //
    // Controladores //
    // ============= //

    public void novo() {
        this.conversation.begin();
        redirect("novo-produto");
    }

    public void retrieve() {
        if (FacesContext.getCurrentInstance().isPostback()) {
            return;
        }

        if (this.conversation.isTransient()) {
            this.conversation.begin();
        }

        if (id == null) {
            this.produto = this.filtro;
        } else {
            imagemCarregada = null;
            this.produto = findById(getId());
            if (this.produto.getImagem() != null)
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("imagem", this.produto.getImagem().getConteudo());
        }
    }

    public Produto findById(Long id) {
        return produtoRepository.findById(id);
    }

    public void atualizar() {
        this.conversation.end();

        if (imagemCarregada != null) {
            Imagem imagem = new Imagem();
            imagem.setConteudo(imagemCarregada.getContents());
            this.produto.setImagem(imagem);
        }

        try {
            produtoRepository.persist(produto);

            if (id == null) {
                addInfoMessage(getFromBundle("msg.cadastro_realizado_com_sucesso"));
                redirect("visualizar-produtos");
            } else {
                addInfoMessage(getFromBundle("msg.atualizacao_realizada_com_sucesso"));
                redirect("visualizar-produto", UIParameterBuilder.create().id("id").value(this.produto.getId()).build());
            }
        } catch (Exception e) {
            addErrorMessage(e.getMessage());
        }
    }

    public void excluir() {
        this.conversation.end();

        try {
            Produto deletableEntity = findById(getId());
            produtoRepository.remove(deletableEntity);
            redirect("visualizar-produtos");
        } catch (Exception e) {
            logger.error(e);
            addErrorMessage(getFromBundle("msg.erro_ao_excluir"));
        }
    }

    @Override
    protected void adicionarFiltros(StringBuilder query) {
        if (filtro.temDescricao()) {
            query.append(" where lower(e.descricao.texto) like :descricao");
        }
    }

    public void paginate() {
        super.paginate(filtro);
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

    public Produto getProduto() {
        return this.produto;
    }

    public Produto getFiltro() {
        return this.filtro;
    }

    public void setImagemCarregada(UploadedFile imagemCarregada) {
        this.imagemCarregada = imagemCarregada;
    }

    public UploadedFile getImagemCarregada() {
        return this.imagemCarregada;
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected IRepository getRepository() {
        return produtoRepository;
    }

}
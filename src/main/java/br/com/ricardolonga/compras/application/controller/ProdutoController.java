package br.com.ricardolonga.compras.application.controller;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.UploadedFile;

import br.com.ricardolonga.compras.application.utils.UIParameterBuilder;
import br.com.ricardolonga.compras.domain.model.entities.Produto;
import br.com.ricardolonga.compras.domain.model.valueobjects.Imagem;
import br.com.ricardolonga.compras.domain.repositories.IProdutoRepository;

@Named
@ConversationScoped
public class ProdutoController extends AbstractController {

    private static final long serialVersionUID = 1L;

    @Inject
    private Conversation conversation;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private IProdutoRepository produtoRepository;

    private Long id;

    private Produto produto;

    private UploadedFile imagemCarregada;

    private int page;
    private long count;
    private List<Produto> pageItems;

    private Produto filtro = new Produto();

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

        if (this.id == null) {
            this.produto = this.filtro;
        } else {
            imagemCarregada = null;
            this.produto = findById(getId());
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

            if (this.id == null) {
                addInfoMessage(getMessageFromBundle("msg.cadastro_realizado_com_sucesso"));
                redirect("visualizar-produtos");
            } else {
                addInfoMessage(getMessageFromBundle("msg.atualizacao_realizada_com_sucesso"));
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
            addErrorMessage(e.getMessage());
        }
    }

    public void paginate() {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

        // Populate this.count
        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<Produto> root = countCriteria.from(Produto.class);
        countCriteria = countCriteria.select(builder.count(root)).where(getSearchPredicates(root));
        this.count = this.entityManager.createQuery(countCriteria).getSingleResult();

        // Populate this.pageItems
        CriteriaQuery<Produto> criteria = builder.createQuery(Produto.class);
        root = criteria.from(Produto.class);
        TypedQuery<Produto> query = this.entityManager.createQuery(criteria.select(root).where(getSearchPredicates(root)));
        query.setFirstResult(this.page * getPageSize()).setMaxResults(getPageSize());
        this.pageItems = query.getResultList();
    }

    private Predicate[] getSearchPredicates(Root<Produto> root) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();

        // String descricao = this.example.getDescricao().getTexto();
        // if (descricao != null && !descricao.trim().isEmpty()) {
        // predicatesList.add(builder.like(root.get("descricao.texto"), descricao));
        // }

        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }

    public List<Produto> getAll() {
        return produtoRepository.findAll();
        // CriteriaQuery<Produto> criteria = this.entityManager.getCriteriaBuilder().createQuery(Produto.class);
        // return this.entityManager.createQuery(criteria.select(criteria.from(Produto.class))).getResultList();
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

    public void setImagemCarregada(UploadedFile imagemCarregada) {
        this.imagemCarregada = imagemCarregada;
    }

    public UploadedFile getImagemCarregada() {
        return this.imagemCarregada;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return 2;
    }

    public Produto getFiltro() {
        return this.filtro;
    }

    public void setFiltro(Produto filtro) {
        this.filtro = filtro;
    }

    public void pesquisar() {
        this.page = 0;
    }

    public List<Produto> getPageItems() {
        return this.pageItems;
    }

    public long getCount() {
        return this.count;
    }

}
package br.com.ricardolonga.compras.application.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.ricardolonga.compras.application.utils.UIParameterBuilder;
import br.com.ricardolonga.compras.domain.model.entities.Produto;
import br.com.ricardolonga.compras.domain.model.valueobjects.Imagem;

@Named
@Stateful
@ConversationScoped
public class ProdutoController extends AbstractController {

    private static final long serialVersionUID = 1L;

    /*
     * Support creating and retrieving Produto entities
     */
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Produto produto;

    public Produto getProduto() {
        return this.produto;
    }

    @Inject
    private Conversation conversation;

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

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
            this.produto = findById(getId());
        }
    }

    private UploadedFile imagem;

    public void setImagem(UploadedFile imagem) {
        this.imagem = imagem;
    }

    public UploadedFile getImagem() {
        return this.imagem;
    }

    public Produto findById(Long id) {
        return this.entityManager.find(Produto.class, id);
    }

    private StreamedContent graphicText;

    /**
     * Esse método é usado pra fazer a imagem ser exibida na tela.
     */
    public StreamedContent getFotoStreamed() {
        return new DefaultStreamedContent(new ByteArrayInputStream(this.produto.getImagem().getConteudo()));
    }

    /*
     * Support updating and deleting Item entities
     */
    public void atualizar() {
        this.conversation.end();

        Imagem imagem_ = new Imagem();
        imagem_.setConteudo(imagem.getContents());
        this.produto.setImagem(imagem_);

        try {
            if (this.id == null) {
                this.entityManager.persist(this.produto);
                redirect("visualizar-produtos");
            } else {
                this.entityManager.merge(this.produto);
                redirect("visualizar-produto", UIParameterBuilder.create().id("id").value(this.produto.getId()).build());
            }
        } catch (Exception e) {
            addErrorMessage(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
        }
    }

    public void excluir() {
        this.conversation.end();

        try {
            Produto deletableEntity = findById(getId());

            this.entityManager.remove(deletableEntity);
            this.entityManager.flush();

            redirect("visualizar-produtos");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
        }
    }

    /*
     * Support searching Item entities with pagination
     */
    private int page;
    private long count;
    private List<Produto> pageItems;

    private Produto filtro = new Produto();

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return 10;
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

    public List<Produto> getPageItems() {
        return this.pageItems;
    }

    public long getCount() {
        return this.count;
    }

    /*
     * Support listing and POSTing back Item entities (e.g. from inside an HtmlSelectOneMenu)
     */
    public List<Produto> getAll() {
        CriteriaQuery<Produto> criteria = this.entityManager.getCriteriaBuilder().createQuery(Produto.class);
        return this.entityManager.createQuery(criteria.select(criteria.from(Produto.class))).getResultList();
    }

    @Resource
    private SessionContext sessionContext;

    public Converter getConverter() {
        final ProdutoController ejbProxy = this.sessionContext.getBusinessObject(ProdutoController.class);

        return new Converter() {
            @Override
            public Object getAsObject(FacesContext context, UIComponent component, String value) {
                return ejbProxy.findById(Long.valueOf(value));
            }

            @Override
            public String getAsString(FacesContext context, UIComponent component, Object value) {
                if (value == null) {
                    return "";
                }

                return String.valueOf(((Produto) value).getId());
            }
        };
    }

    /*
     * Support adding children to bidirectional, one-to-many tables
     */
    private Produto add = new Produto();

    public Produto getAdd() {
        return this.add;
    }

    public Produto getAdded() {
        Produto added = this.add;
        this.add = new Produto();
        return added;
    }
}
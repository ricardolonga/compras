package br.com.ricardolonga.compras.application.controller;

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

import br.com.ricardolonga.compras.domain.model.entities.Produto;

@Named
@Stateful
@ConversationScoped
public class ProdutoController extends AbstractController {

    private static final long serialVersionUID = 1L;

    /*
     * Support creating and retrieving Item entities
     */
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Produto item;

    public Produto getItem() {
        return this.item;
    }

    @Inject
    private Conversation conversation;

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public String create() {
        this.conversation.begin();
        return "pretty:item-create";
    }

    public void retrieve() {
        if (FacesContext.getCurrentInstance().isPostback()) {
            return;
        }

        if (this.conversation.isTransient()) {
            this.conversation.begin();
        }

        if (this.id == null) {
            this.item = this.example;
        } else {
            this.item = findById(getId());
        }
    }

    public Produto findById(Long id) {
        return this.entityManager.find(Produto.class, id);
    }

    /*
     * Support updating and deleting Item entities
     */
    public String update() {
        this.conversation.end();

        try {
            if (this.id == null) {
                this.entityManager.persist(this.item);
                return "search?faces-redirect=true";
            } else {
                this.entityManager.merge(this.item);
                return "view?faces-redirect=true&id=" + this.item.getId();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    public String delete() {
        this.conversation.end();

        try {
            Produto deletableEntity = findById(getId());

            this.entityManager.remove(deletableEntity);
            this.entityManager.flush();

            return "search?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    /*
     * Support searching Item entities with pagination
     */
    private int page;
    private long count;
    private List<Produto> pageItems;

    private Produto example = Produto.newInstance();

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return 10;
    }

    public Produto getExample() {
        return this.example;
    }

    public void setExample(Produto example) {
        this.example = example;
    }

    public void search() {
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

        int quantidade = this.example.getQuantidade();
        if (quantidade != 0) {
            predicatesList.add(builder.equal(root.get("quantidade"), quantidade));
        }

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
    private Produto add = Produto.newInstance();

    public Produto getAdd() {
        return this.add;
    }

    public Produto getAdded() {
        Produto added = this.add;
        this.add = Produto.newInstance();
        return added;
    }
}
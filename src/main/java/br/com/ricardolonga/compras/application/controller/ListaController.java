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

import br.com.ricardolonga.compras.domain.model.entities.Lista;

@Named
@Stateful
@ConversationScoped
public class ListaController extends AbstractController {

    private static final long serialVersionUID = 1L;

    /*
     * Support creating and retrieving Lista entities
     */
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Lista lista;

    public Lista getLista() {
        return this.lista;
    }

    @Inject
    private Conversation conversation;

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public String create() {
        this.conversation.begin();
        return "pretty:lista-create";
    }

    public void retrieve() {
        if (FacesContext.getCurrentInstance().isPostback()) {
            return;
        }

        if (this.conversation.isTransient()) {
            this.conversation.begin();
        }

        if (this.id == null) {
            this.lista = this.example;
        } else {
            this.lista = findById(getId());
        }
    }

    public Lista findById(Long id) {
        return this.entityManager.find(Lista.class, id);
    }

    /*
     * Support updating and deleting Lista entities
     */
    public String update() {
        this.conversation.end();

        try {
            if (this.id == null) {
                this.entityManager.persist(this.lista);
                return "search?faces-redirect=true";
            } else {
                this.entityManager.merge(this.lista);
                return "view?faces-redirect=true&id=" + this.lista.getId();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    public String delete() {
        this.conversation.end();

        try {
            Lista deletableEntity = findById(getId());

            this.entityManager.remove(deletableEntity);
            this.entityManager.flush();
            return "search?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
    }

    /*
     * Support searching Lista entities with pagination
     */
    private int page;
    private long count;
    private List<Lista> pageItems;

    private Lista example = new Lista();

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return 10;
    }

    public Lista getExample() {
        return this.example;
    }

    public void setExample(Lista example) {
        this.example = example;
    }

    public void search() {
        this.page = 0;
    }

    public void paginate() {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

        // Populate this.count

        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<Lista> root = countCriteria.from(Lista.class);
        countCriteria = countCriteria.select(builder.count(root)).where(getSearchPredicates(root));
        this.count = this.entityManager.createQuery(countCriteria).getSingleResult();

        // Populate this.pageItems

        CriteriaQuery<Lista> criteria = builder.createQuery(Lista.class);
        root = criteria.from(Lista.class);
        TypedQuery<Lista> query = this.entityManager.createQuery(criteria.select(root).where(getSearchPredicates(root)));
        query.setFirstResult(this.page * getPageSize()).setMaxResults(getPageSize());
        this.pageItems = query.getResultList();
    }

    private Predicate[] getSearchPredicates(Root<Lista> root) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();

        String nomeLista = this.example.getDescricao().getTexto();
        if (nomeLista != null && !"".equals(nomeLista)) {
            predicatesList.add(builder.like(root.<String> get("nomeLista"), '%' + nomeLista + '%'));
        }

        return predicatesList.toArray(new Predicate[predicatesList.size()]);
    }

    public List<Lista> getPageItems() {
        return this.pageItems;
    }

    public long getCount() {
        return this.count;
    }

    /*
     * Support listing and POSTing back Lista entities (e.g. from inside an HtmlSelectOneMenu)
     */
    public List<Lista> getAll() {
        CriteriaQuery<Lista> criteria = this.entityManager.getCriteriaBuilder().createQuery(Lista.class);
        return this.entityManager.createQuery(criteria.select(criteria.from(Lista.class))).getResultList();
    }

    @Resource
    private SessionContext sessionContext;

    public Converter getConverter() {
        final ListaController ejbProxy = this.sessionContext.getBusinessObject(ListaController.class);

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

                return String.valueOf(((Lista) value).getId());
            }
        };
    }

    /*
     * Support adding children to bidirectional, one-to-many tables
     */
    private Lista add = new Lista();

    public Lista getAdd() {
        return this.add;
    }

    public Lista getAdded() {
        Lista added = this.add;
        this.add = new Lista();
        return added;
    }
}
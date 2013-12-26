package br.com.ricardolonga.compras.application.controller.shared;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.ricardolonga.compras.domain.model.entities.BaseEntity;
import br.com.ricardolonga.compras.domain.repositories.IRepository;

@SuppressWarnings("rawtypes")
public abstract class AbstractPaginationController<T extends BaseEntity> extends AbstractController {

    private static final long serialVersionUID = 4330590256073966550L;

    private int page;
    private long count;
    private List<T> pageItems;

    @SuppressWarnings("unchecked")
    protected void paginate(T filter) {
        StringBuilder countQueryString = new StringBuilder("select count(e.id) from " + filter.getClass().getSimpleName() + " e ");
        adicionarFiltros(countQueryString);
        TypedQuery<Long> countQuery = getRepository().getEntityManager().createQuery(countQueryString.toString(), Long.class);
        adicionarParametros(countQuery);
        count = countQuery.getSingleResult();

        StringBuilder selectQueryString = new StringBuilder("from " + filter.getClass().getSimpleName() + " e ");
        adicionarFiltros(selectQueryString);
        TypedQuery<? extends BaseEntity> query = getRepository().getEntityManager().createQuery(selectQueryString.toString(), filter.getClass());
        adicionarParametros(query);
        query.setFirstResult(page * getPageSize());
        query.setMaxResults(getPageSize());
        this.pageItems = (List<T>) query.getResultList();
    }

    protected abstract void adicionarFiltros(StringBuilder hql);

    protected abstract void adicionarParametros(Query query);

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return getRepository().findAll();
    }

    // ========= //
    // Acessores //
    // ==========//

    protected abstract IRepository getRepository();

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return 5;
    }

    public void pesquisar() {
        this.page = 0;
    }

    public List<T> getPageItems() {
        return this.pageItems;
    }

    public long getCount() {
        return this.count;
    }

}

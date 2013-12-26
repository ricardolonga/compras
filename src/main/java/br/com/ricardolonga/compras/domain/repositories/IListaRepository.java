package br.com.ricardolonga.compras.domain.repositories;

import java.util.List;

import br.com.ricardolonga.compras.domain.model.entities.Lista;

public interface IListaRepository extends IRepository<Lista, Long> {

    Lista getListaCompletaById(Long id);

    List<Lista> getListasCompletas();

}

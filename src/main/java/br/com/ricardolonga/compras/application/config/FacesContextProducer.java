package br.com.ricardolonga.compras.application.config;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

/**
 * Objeto responsável por retornar o contexto do Faces respectivo a cada request.
 * 
 * @author Ricardo Longa
 */
public class FacesContextProducer {

    @Produces
    @RequestScoped
    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}

package br.com.ricardolonga.compras.application.config;

import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * Classe instanciada uma única vez com as configurações que devem permanecer no escopo de aplicação.
 * 
 * @author Ricardo Longa
 */
@ApplicationScoped
public class ResourceBundleProducer {

    @Inject
    private FacesContext facesContext;

    private ResourceBundle bundle;

    @Produces
    @MessageBundle
    public ResourceBundle getResourceBundle() {
        if (bundle == null) {
            bundle = facesContext.getApplication().getResourceBundle(facesContext, "msg");
        }

        return bundle;
    }

}

package br.com.ricardolonga.compras.infrastructure.producers;

import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

@ApplicationScoped
public class ResourceBundleProducer {

    private ResourceBundle bundle;

    @Produces
    @Bundle
    public ResourceBundle getResourceBundle() {
        if (bundle == null) {
            bundle = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "msg");
        }

        return bundle;
    }

}

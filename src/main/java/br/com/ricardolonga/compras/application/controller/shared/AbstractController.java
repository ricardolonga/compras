package br.com.ricardolonga.compras.application.controller.shared;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;

import br.com.ricardolonga.compras.infrastructure.producers.Bundle;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.faces.config.mapping.UrlMapping;
import com.ocpsoft.pretty.faces.util.PrettyURLBuilder;

/**
 * Superclasse para os ManagedBeans com métodos auxiliares para consultas em resourceBundles e adições de mensagens
 * (info/warn/error) para o usuário.
 * 
 * @author Ricardo Longa
 */
public class AbstractController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    protected Logger logger;

    @Inject
    @Bundle
    protected transient ResourceBundle bundle;

    // =========== //
    // PrettyFaces //
    // =========== //

    protected void redirect(String target, UIParameter... params) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        PrettyContext context = PrettyContext.getCurrentInstance(request);

        UrlMapping mapping = context.getConfig().getMappingById(target);
        String targetURL = new PrettyURLBuilder().build(mapping, true, Arrays.asList(params));

        try {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/compras" + targetURL);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {
            logger.error(e);
        }
    }

    // ============== //
    // ResourceBundle //
    // ============== //

    /**
     * Utilizado para obter a mensagem do ResourceBundle.
     * 
     * @param key
     * @return
     */
    protected String getFromBundle(String key) {
        return bundle.getString(key);
    }

    // ============ //
    // FacesMessage //
    // ============ //

    /**
     * Utilizado para apresentar mensagens informativas ao usuário, como por exemplo, sucesso ao persistir um objeto.
     * 
     * @param mensagem
     */
    protected void addInfoMessage(String mensagem) {
        addInfoMessage(mensagem, null);
    }

    /**
     * Utilizado para apresentar mensagens de alerta ao usuário, como por exemplo, nenhum registro encontrado em uma
     * pesquisa.
     * 
     * @param mensagem
     */
    protected void addWarnMessage(String mensagem) {
        addWarnMessage(mensagem, null);
    }

    /**
     * Utilizado para apresentar mensagens de erro ao usuário, como por exemplo, campos obrigatórios de um formulário
     * não preenchidos.
     * 
     * @param mensagem
     */
    protected void addErrorMessage(String mensagem) {
        addErrorMessage(mensagem, null);
    }

    /**
     * Utilizado para apresentar mensagens informativas ao usuário, como por exemplo, sucesso ao persistir um objeto.
     * 
     * @param mensagem
     * @param componentId
     */
    protected void addInfoMessage(String mensagem, String componentId) {
        addMessage(mensagem, componentId, FacesMessage.SEVERITY_INFO);
    }

    /**
     * Utilizado para apresentar mensagens de alerta ao usuário, como por exemplo, nenhum registro encontrado em uma
     * pesquisa.
     * 
     * @param mensagem
     * @param componentId
     */
    protected void addWarnMessage(String mensagem, String componentId) {
        addMessage(mensagem, componentId, FacesMessage.SEVERITY_WARN);
    }

    /**
     * Utilizado para apresentar mensagens de erro ao usuário, como por exemplo, campos obrigatórios de um formulário
     * não preenchidos.
     * 
     * @param mensagem
     * @param componentId
     */
    protected void addErrorMessage(String mensagem, String componentId) {
        addMessage(mensagem, componentId, FacesMessage.SEVERITY_ERROR);
    }

    private void addMessage(String mensagem, String componentId, Severity severidade) {
        FacesContext.getCurrentInstance().addMessage(componentId, new FacesMessage(severidade, mensagem, mensagem));
    }

}
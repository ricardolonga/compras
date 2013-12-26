package br.com.ricardolonga.compras.application.converters;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ricardolonga.compras.domain.model.entities.Produto;

@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter {

    private static Map<String, Produto> cache = new HashMap<String, Produto>();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return cache.get(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String retorno = Long.toString(((Produto) value).getId());
        cache.put(retorno, (Produto) value);
        return retorno;
    }

}

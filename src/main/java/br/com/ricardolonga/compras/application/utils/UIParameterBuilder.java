package br.com.ricardolonga.compras.application.utils;

import javax.faces.component.UIParameter;

public class UIParameterBuilder {

    private final UIParameter param = new UIParameter();

    public static UIParameterBuilder create() {
        return new UIParameterBuilder();
    }

    public UIParameterBuilder id(String id) {
        param.setId(id);
        return this;
    }

    public UIParameterBuilder value(Object value) {
        param.setValue(value);
        return this;
    }

    public UIParameter build() {
        return param;
    }

}

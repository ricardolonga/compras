package br.com.ricardolonga.compras.application.converters;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "valorMonetarioConverter")
public class ValorMonetarioConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String valorTela) throws ConverterException {
        if (valorTela == null || valorTela.toString().trim().equals("")) {
            return null;
        }

        try {
            if (valorTela.contains(".")) {
                valorTela.length();
                char[] valor = valorTela.toCharArray();
                int indice1 = valorTela.length() - 3;
                Character x = valorTela.charAt(indice1);

                if (x.equals('.')) {
                    valor[indice1] = ',';
                }

                int indice2 = valorTela.length() - 2;
                x = valorTela.charAt(indice2);

                if (x.equals('.')) {
                    valor[indice1] = ',';
                }

                valorTela = new String(valor);

                if (valorTela.contains(",")) {
                    valorTela = valorTela.replaceAll(Pattern.quote("."), "");
                    String novoValor = valorTela.replace(",", ".");
                    return new BigDecimal(novoValor);
                }
            }
        } catch (Exception e) {
            throw new ConverterException("Valor inválido");
        }

        try {
            valorTela = valorTela.replaceAll(Pattern.quote("."), "");
            String novoValor = valorTela.replace(",", ".");
            return new BigDecimal(novoValor);
        } catch (Exception e) {
            throw new ConverterException("Valor inválido");
        }
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object valorTela) throws ConverterException {
        if (valorTela == null || valorTela.toString().trim().equals("")) {
            return "";
        }

        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));

        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);

        return nf.format(Double.valueOf(valorTela.toString()));
    }
}

package ma.fstt.Converters;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;
import ma.fstt.Entities.Vitrine;
import ma.fstt.beans.ProduitBean;

import jakarta.inject.Inject;

@FacesConverter(value = "VitrineConverter")
public class VitrineConverter implements Converter {

    @Inject
    private ProduitBean produitBean;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        // Programmatically retrieve ProduitBean
        ProduitBean produitBean = context.getApplication().evaluateExpressionGet(context, "#{produitBean}", ProduitBean.class);
        return produitBean.findVitrineById(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(((Vitrine) value).getId_vitrine());
    }
}

package mz.co.ubisse.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.apache.commons.lang.StringUtils;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.util.MessageFactory;

@FacesConverter(value="localDateConverter")
public class FacesConvertLocalDate implements Converter {

	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        try
        {
            return LocalDate.parse(value, buildParser(component.getAttributes().getOrDefault("pattern", "").toString()));
        }
        catch(DateTimeParseException e)
        {
            throw new ConverterException(formatErrorMessage("Date", context, component, value), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value instanceof String)
        {
            return (String)value;
        }
        LocalDate dateValue = (LocalDate) value;
        return primefacesSupport(component, dateValue.format(DateTimeFormatter
                .ofPattern(component.getAttributes().getOrDefault("pattern", "M/d/yyyy").toString())));
    }


private static String primefacesSupport(UIComponent component, String val)
{
    // PrimeFaces support
    if (component instanceof Calendar)
    {
        Calendar cal = (Calendar) component;
        cal.setValue(val);
    }
    return val;
}


private static FacesMessage formatErrorMessage(String which, FacesContext context, UIComponent component, String value)
{
    return new FacesMessage(FacesMessage.SEVERITY_ERROR, String.format("%s Conversion Failed: %s - %s", which, 
                    (String) MessageFactory.getLabel(context, component), value), "");
}


private static DateTimeFormatter buildParser(String pattern)
{
    DateTimeFormatterBuilder dtf = new DateTimeFormatterBuilder().parseLenient();
    dtf.appendOptional(DateTimeFormatter.ofPattern("M/dd/yy"))
            .appendOptional(DateTimeFormatter.ofPattern("M/dd/yy HH:mm:ss"));
    if(StringUtils.isNotBlank(pattern))
    {
        dtf.appendOptional(DateTimeFormatter.ofPattern(pattern));
    }
    return dtf.appendOptional(DateTimeFormatter.ofPattern("M/dd/yyyy HH:mm:ss"))
            .appendOptional(DateTimeFormatter.ofPattern("M/dd/yyyy"))
            .toFormatter();
}    
}

package mz.co.ubisse.util;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

import org.junit.Test;

public class TesteMoedas {

	@Test
	public void moedas() throws ParseException {
		
		String valor = "100,00"; //valor do campo
		BigDecimal v = new BigDecimal(valor.replaceAll("\\.", "").replace(",",".")); //remove a virgula e troca por ponto
		System.out.println(v);
		
		DecimalFormat dec = new DecimalFormat("###,###,##0.00");
		System.out.println(dec.format(1200000));
		DecimalFormat formater = new DecimalFormat("#.00");
        formater.setCurrency(Currency.getInstance(new Locale("pt", "BR")));
        System.out.println(formater.format(100000)); // vai imprimir 10,00
        System.out.println(formater.parse("15,20")); // vai imprimir 15.2
	}
}

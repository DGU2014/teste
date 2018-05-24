package mz.co.ubisse.converter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Convert;

import org.joda.time.DateTime;

public class ConverterData {
	@Convert(converter = LocalDateConverter.class)
	public LocalDate diasUteis(Calendar cal) {
		List<Date> feriados = new ArrayList<Date>();
		Calendar c1 = Calendar.getInstance();
		c1.set(DateTime.now().getYear(), 1, 1);// Dia de Ano Novo (Confraternização Universal)
		Calendar c2 = Calendar.getInstance();
		c2.set(DateTime.now().getYear(), 2, 3); // Dia dos Herois Mocambicanos
		Calendar c3 = Calendar.getInstance();
		c3.set(DateTime.now().getYear(), 4, 7);// Dia da Mulher Mocambicana
		Calendar c4 = Calendar.getInstance();
		c4.set(DateTime.now().getYear(), 5, 1);// Dia Internacional dos Trabalhadores
		Calendar c5 = Calendar.getInstance();
		c5.set(DateTime.now().getYear(), 6, 25);// Dia da Independencia Nacional
		Calendar c6 = Calendar.getInstance();
		c6.set(DateTime.now().getYear(), 9, 7);// Dia da Vitoria
		Calendar c7 = Calendar.getInstance();
		c7.set(DateTime.now().getYear(), 9, 25);// Dia das Forcas Armadas de Libertacao de Mocambique
		Calendar c8 = Calendar.getInstance();
		c8.set(DateTime.now().getYear(), 10, 4);// Dia da Paz e Reconciliacao
		Calendar c9 = Calendar.getInstance();
		c9.set(DateTime.now().getYear(), 12, 25);// Dia das Familias
		feriados.add(c1.getTime());
		feriados.add(c2.getTime());
		feriados.add(c3.getTime());
		feriados.add(c4.getTime());
		feriados.add(c5.getTime());
		feriados.add(c6.getTime());
		feriados.add(c7.getTime());
		feriados.add(c8.getTime());
		feriados.add(c9.getTime());
		int daysToAdd = 15; // quantidade de dias a adicionar
		// Incializa data
		cal = Calendar.getInstance();
		for (int i = 0; i < daysToAdd; i++) {
			cal.add(Calendar.DATE, 1);
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
					|| feriados.contains(cal.getTime())) {
				daysToAdd++;
			}
		}
		Date input = cal.getTime();
		LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return date;
	}
}

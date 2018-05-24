package mz.co.ubisse.util;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.Test;

public class ValidacaoDoSistema {

	@SuppressWarnings({ "unused", "static-access" })
	@Test
	public void aviso() {

		LocalDate data = null;

		LocalTime time = null;
		System.out.println("Mes "+data.now().getMonth().getValue());
	if (LocalDate.now().getDayOfMonth() == 29 && LocalDate.now().getMonth().getValue()>=1 && LocalDate.now().getYear()>=2017) {
		System.out.println("Mes "+data.now().getMonth());
	}else {
		System.out.println("eu");
	}
	}
}

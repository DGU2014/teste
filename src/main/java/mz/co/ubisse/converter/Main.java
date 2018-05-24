package mz.co.ubisse.converter;

import java.util.Calendar;

public class Main {

	public static void main(String[] args) {

ConverterData converterData = new ConverterData();
Calendar cal = Calendar.getInstance();
System.out.println(converterData.diasUteis(cal));

	}

}

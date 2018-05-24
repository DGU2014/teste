package mz.co.ubisse.util;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;

public class Feriados {

	@SuppressWarnings("unlikely-arg-type")
	public static void main(String[] args) {
				
		System.out.println("Este Ano "+DateTime.now().getYear());
		List<DateTime> feriados = new ArrayList<DateTime>();
    	Calendar c1 = Calendar.getInstance();
    	c1.set(2016, 6, 6);
    	Calendar c2 = Calendar.getInstance();
    	c2.set(2016, 6, 10);
     	Calendar c3 = Calendar.getInstance();
    	c3.set(2016, 6, 29);
    	
    	DateTime d = new DateTime(("2016-6-6"));
    	DateTime d2 = new DateTime(("2016-6-10"));
    	DateTime d3 = new DateTime(("2016-6-29"));
    	feriados.add(d);
    	feriados.add(d2);
    	feriados.add(d3);
        int daysToAdd = 60; // quantidade de dias a adicionar
        // Incializa data
        
        Calendar cal = Calendar.getInstance();
 //       DateTime cal1 =DateTime.parse(cal.getTime().toString());
 
		for (int i = 0; i <= daysToAdd; i++) {
			cal.add(Calendar.DATE, 1);
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
					|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
					|| feriados.contains(cal.getTime())) {				
				daysToAdd++;
			}
		}
	}

}

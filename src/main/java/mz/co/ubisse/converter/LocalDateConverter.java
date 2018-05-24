package mz.co.ubisse.converter;

import java.time.LocalDate;
import java.sql.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDate entityValue) {
		if (entityValue != null) {
			return Date.valueOf(entityValue);
		}
		return null;
	}

	@Override
	public LocalDate convertToEntityAttribute(Date databaseValue) {
		if (databaseValue != null) {
			return databaseValue.toLocalDate();
		}
		return null;
	}

}
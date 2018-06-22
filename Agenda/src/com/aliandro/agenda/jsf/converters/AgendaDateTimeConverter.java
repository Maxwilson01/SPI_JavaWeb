package com.aliandro.agenda.jsf.converters;

import java.util.Date;
import java.util.TimeZone;

import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass=Date.class)
public class AgendaDateTimeConverter extends DateTimeConverter {

	public AgendaDateTimeConverter() {
		this.setPattern("dd/MM/yyyy");
		this.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
	}

}

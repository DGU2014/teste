package mz.co.ubisse.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import mz.co.ubisse.converter.LocalDateConverter;

@SuppressWarnings("serial")
@Entity
@Table(name = "guia_de_remessa")
public class GuiaDeRemessa extends GenericDomain {

	@OneToOne
	@JoinColumn(nullable = true)
	private VendaDinheiro vendaDinheiro;
	@OneToOne
	@JoinColumn(nullable = true)
	private Factura factura;

	@Convert(converter = LocalDateConverter.class)
	@Column
	private LocalDate data;

	public VendaDinheiro getVendaDinheiro() {
		return vendaDinheiro;
	}

	public void setVendaDinheiro(VendaDinheiro vendaDinheiro) {
		this.vendaDinheiro = vendaDinheiro;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

}

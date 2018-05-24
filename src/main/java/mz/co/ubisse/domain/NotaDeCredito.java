package mz.co.ubisse.domain;

import java.math.BigDecimal;
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
@Table(name = "nota_de_credito")
public class NotaDeCredito extends GenericDomain {

	@Column(name = "codigo_nc", length = 10, unique = true, nullable = false)
	private String codigoNC;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Factura factura;
	@Column(nullable = false, precision = 15, scale = 2)
	private BigDecimal valorTotal;
	@Column(nullable = false, name = "valor_subtotal", precision = 15, scale = 2)
	private BigDecimal valorSubtotal;
	@Column(nullable = false, name = "valor_pago_iva", precision = 15, scale = 2)
	private BigDecimal valorPagoIva;

	@Convert(converter = LocalDateConverter.class)
	@Column(name = "data_emissao", nullable = false)
	private LocalDate dataDeEmissao;

	
	public String getCodigoNC() {
		return codigoNC;
	}

	public void setCodigoNC(String codigoNC) {
		this.codigoNC = codigoNC;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorSubtotal() {
		return valorSubtotal;
	}

	public void setValorSubtotal(BigDecimal valorSubtotal) {
		this.valorSubtotal = valorSubtotal;
	}

	public BigDecimal getValorPagoIva() {
		return valorPagoIva;
	}

	public void setValorPagoIva(BigDecimal valorPagoIva) {
		this.valorPagoIva = valorPagoIva;
	}

	public LocalDate getDataDeEmissao() {
		return dataDeEmissao;
	}

	public void setDataDeEmissao(LocalDate dataDeEmissao) {
		this.dataDeEmissao = dataDeEmissao;
	}

}

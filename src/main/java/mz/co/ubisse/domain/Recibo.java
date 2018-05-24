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
@Table(name = "recibo")
public class Recibo extends GenericDomain {

	@Column(name = "codigo_rc", length = 10, unique = true, nullable = false)
	private String codigoRC;

	@Convert(converter = LocalDateConverter.class)
	@Column
	private LocalDate data;
	@Column(nullable = false, precision = 15, scale = 2)
	private BigDecimal valorTotal;
	@Column(nullable = false, name = "valor_subtotal", precision = 15, scale = 2)
	private BigDecimal valorSubtotal;
	@Column(nullable = false, name = "valor_pago_iva", precision = 15, scale = 2)
	private BigDecimal valorPagoIva;
	@Column(nullable = true, name = "valor_pago_factura", precision = 15, scale = 2)
	private BigDecimal valorPagoFactura;
	@Column(nullable = true, name = "valor_pago_debido", precision = 15, scale = 2)
	private BigDecimal valorPagoDebido;
	@Column(length = 9, nullable = true)
	private String estado;
	@Column(length = 1, nullable = false)
	private Character controlo;
	@Column(name = "observacoes", nullable = true, length = 600)
	private String observacoes;
	@Column(nullable = false, name = "chave")
	private Long chaveEmpresa;
	@Column(name = "tipo_pagamento", nullable = true, length = 20)
	private String tipoPagamento;
	@Column(name = "detalhe_tipo_pagamento", nullable = true, length = 50)
	private String detalheTipoPagamento;

	@OneToOne
	@JoinColumn(nullable = false)
	private Factura factura;

	public String getCodigoRC() {
		return codigoRC;
	}

	public void setCodigoRC(String codigoRC) {
		this.codigoRC = codigoRC;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
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

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Character getControlo() {
		return controlo;
	}

	public void setControlo(Character controlo) {
		this.controlo = controlo;
	}

	public BigDecimal getValorPagoFactura() {
		return valorPagoFactura;
	}

	public void setValorPagoFactura(BigDecimal valorPagoFactura) {
		this.valorPagoFactura = valorPagoFactura;
	}

	public BigDecimal getValorPagoDebido() {
		return valorPagoDebido;
	}

	public void setValorPagoDebido(BigDecimal valorPagoDebido) {
		this.valorPagoDebido = valorPagoDebido;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Long getChaveEmpresa() {
		return chaveEmpresa;
	}

	public void setChaveEmpresa(Long chaveEmpresa) {
		this.chaveEmpresa = chaveEmpresa;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public String getDetalheTipoPagamento() {
		return detalheTipoPagamento;
	}

	public void setDetalheTipoPagamento(String detalheTipoPagamento) {
		this.detalheTipoPagamento = detalheTipoPagamento;
	}

}

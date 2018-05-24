package mz.co.ubisse.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "venda")
public class Venda extends GenericDomain {

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date horario;
	@Column(nullable = false, precision = 15, scale = 2)
	private BigDecimal valorTotal;
	@Column(nullable = true, name = "valor_subtotal", precision = 15, scale = 2)
	private BigDecimal valorSubtotal;
	@Column(nullable = true, name = "valor_pago_iva", precision = 15, scale = 2)
	private BigDecimal valorPagoIva;
	@Column(nullable = true, name = "percentagem_desconto")
	private Short percentagemDesconto;
	@Column(nullable = true, name = "valor_extenso")
	private String valorExtenso;
	@Column(nullable = true, name = "valor_pago", precision = 15, scale = 2)
	private BigDecimal valorPago;
	@Column(nullable = true, name = "valor_troco", precision = 15, scale = 2)
	private BigDecimal valorTroco;
	@Column(name = "tipo_pagamento", nullable = true, length = 20)
	private String tipoPagamento;
	@Column(name = "detalhe_tipo_pagamento", nullable = true, length = 50)
	private String detalheTipoPagamento;

	@JoinColumn(nullable = true)
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Funcionario funcionario;

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
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

	public Short getPercentagemDesconto() {
		return percentagemDesconto;
	}

	public void setPercentagemDesconto(Short percentagemDesconto) {
		this.percentagemDesconto = percentagemDesconto;
	}

	public String getValorExtenso() {
		return valorExtenso;
	}

	public void setValorExtenso(String valorExtenso) {
		this.valorExtenso = valorExtenso;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public BigDecimal getValorTroco() {
		return valorTroco;
	}

	public void setValorTroco(BigDecimal valorTroco) {
		this.valorTroco = valorTroco;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
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

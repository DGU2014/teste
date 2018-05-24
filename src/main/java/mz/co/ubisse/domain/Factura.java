package mz.co.ubisse.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import mz.co.ubisse.converter.LocalDateConverter;

@SuppressWarnings("serial")
@Entity
@Table(name = "factura")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Factura extends GenericDomain {

	@Column(name = "codigo_ft", length = 10, unique = true, nullable = false)
	private String codigoFT;

	@OneToOne
	@JoinColumn(nullable = false)
	private Venda venda;
	@Convert(converter = LocalDateConverter.class)
	@Column(name = "data_facturacao", nullable = false)
	private LocalDate dataFacturacao;
	@Convert(converter = LocalDateConverter.class)
	@Column(name = "data_validade", nullable = false)
	private LocalDate dataValidade;
	@Column(nullable = true, length = 30)
	private String estado;
	@Column(name = "forma_pagamento", nullable = true, length = 20)
	private String formaPagamento;
	@Column(nullable = false, precision = 15, scale = 2)
	private BigDecimal saldo;
	@Column(name = "observacoes", nullable = true, length = 600)
	private String obseracoes;

	@Column(name = "data_pagamento", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataPagamento;

	@Column(nullable = false, name = "chave")
	private Long chaveEmpresa;

	public String getCodigoFT() {
		return codigoFT;
	}

	public void setCodigoFT(String codigoFT) {
		this.codigoFT = codigoFT;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public LocalDate getDataFacturacao() {
		return dataFacturacao;
	}

	public void setDataFacturacao(LocalDate dataFacturacao) {
		this.dataFacturacao = dataFacturacao;
	}

	public LocalDate getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(LocalDate dataValidade) {
		this.dataValidade = dataValidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getObseracoes() {
		return obseracoes;
	}

	public void setObseracoes(String obseracoes) {
		this.obseracoes = obseracoes;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Long getChaveEmpresa() {
		return chaveEmpresa;
	}

	public void setChaveEmpresa(Long chaveEmpresa) {
		this.chaveEmpresa = chaveEmpresa;
	}

}

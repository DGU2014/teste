package mz.co.ubisse.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import mz.co.ubisse.converter.LocalDateConverter;

@SuppressWarnings("serial")
@Entity
@Table(name = "items_venda")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ItemsVenda extends GenericDomain {

	// columnDefinition="smallint(6) default 0"

	@Column(nullable = true)
	private Short quantidade;
	@Column(nullable = true, name = "quantidade_cancelada", columnDefinition = "smallint(6) default 0")
	private Short quantidadeCancelada;
	@Column(nullable = false, precision = 15, scale = 2, name = "valor_parcial")
	private BigDecimal valorParcial;
	@Column(nullable = true, precision = 15, scale = 2, name = "valor_parcial_cancelado")
	private BigDecimal valorParcialCancelado;
	@Column(nullable = false, precision = 15, scale = 2, name = "valor_unitario")
	private BigDecimal valorUnitario;
	@Convert(converter = LocalDateConverter.class)
	@Column(name = "data_venda")
	private LocalDate dataVenda;

	@Column(nullable = false)
	private Boolean estado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Produto produto;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Venda venda;

	public Short getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Short quantidade) {
		this.quantidade = quantidade;
	}

	public Short getQuantidadeCancelada() {
		return quantidadeCancelada;
	}

	public void setQuantidadeCancelada(Short quantidadeCancelada) {
		this.quantidadeCancelada = quantidadeCancelada;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public BigDecimal getValorParcial() {
		return valorParcial;
	}

	public void setValorParcial(BigDecimal valorParcial) {
		this.valorParcial = valorParcial;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public LocalDate getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(LocalDate dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public BigDecimal getValorParcialCancelado() {
		return valorParcialCancelado;
	}

	public void setValorParcialCancelado(BigDecimal valorParcialCancelado) {
		this.valorParcialCancelado = valorParcialCancelado;
	}
	
}

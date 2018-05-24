package mz.co.ubisse.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "items_debido")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ItemsDebido extends GenericDomain {

	@Column(nullable = true)
	private Short quantidade;
	@Column(nullable = false, precision = 15, scale = 2, name = "valor_parcial")
	private BigDecimal valorParcial;
	@Column(nullable = true, precision = 15, scale = 2, name = "valor_unitario")
	private BigDecimal valorUnitario;
	@Column(nullable = false, length=600)
	private String produto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private NotaDeDebido notaDeDebido;

	public Short getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Short quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorParcial() {
		return valorParcial;
	}

	public void setValorParcial(BigDecimal valorParcial) {
		this.valorParcial = valorParcial;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public NotaDeDebido getNotaDeDebido() {
		return notaDeDebido;
	}

	public void setNotaDeDebido(NotaDeDebido notaDeDebido) {
		this.notaDeDebido = notaDeDebido;
	}

}

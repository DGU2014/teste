package mz.co.ubisse.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "servico")
public class Servico extends GenericDomain {

	@Column(nullable = false, length = 100)
	private String descricao;
	@Column(nullable = false, precision = 15, scale = 2, name = "valor")
	private BigDecimal valor;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Venda venda;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

}

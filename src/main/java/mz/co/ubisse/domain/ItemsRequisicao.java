package mz.co.ubisse.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "items_requisicao")
public class ItemsRequisicao extends GenericDomain {

	@Column(nullable = false)
	private Short quantidade;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Produto produto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "requisicao_produto")
	private RequisicaoProduto requisicaoProduto;

	public Short getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Short quantidade) {
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public RequisicaoProduto getRequisicaoProduto() {
		return requisicaoProduto;
	}

	public void setRequisicaoProduto(RequisicaoProduto requisicaoProduto) {
		this.requisicaoProduto = requisicaoProduto;
	}

}
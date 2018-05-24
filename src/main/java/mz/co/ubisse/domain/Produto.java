package mz.co.ubisse.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "produto")
public class Produto extends GenericDomain {

	@Column(length = 20, nullable = true)
	private String nome;
	@Column(length = 20, nullable = true)
	private String marca;
	@Column(length = 20, nullable = true)
	private String referencia;
	@Column(length = 20, nullable = true)
	private String modelo;
	@Column(nullable = true)
	private Short quantidade;
	@Column(nullable = true, name = "quantidade_armazenada")
	private Short quantidadeArmazenada;
	@Column(nullable = true, precision = 15, scale = 2, name = "preco")
	private BigDecimal preco;
	@Column(name = "preco_iva", precision = 15, scale = 2)
	private BigDecimal precoIva;
	@Column(name = "preco_fornecedor", precision = 15, scale = 2)
	private BigDecimal precoFornecedor;
	@Column(name = "preco_fornecedor_iva", precision = 15, scale = 2)
	private BigDecimal precoFornecedorIva;
	@Column(length = 600, nullable = false)
	private String descricao;
	@Column(nullable = true, name = "estoque_minimo")
	private Short estoqueMinimo;
	@Column(nullable = true, name = "estoque_minimo_armazem")
	private Short estoqueMinimoArmazem;
	@Column(nullable = true)
	private boolean estado;
	@Column(name = "data_controlo_estoque", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataControloEstoque;
	@Column(nullable = false, name = "chave")
	private Long chaveEmpresa;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = true)
	private Categoria categoria;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Short getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Short quantidade) {
		this.quantidade = quantidade;
	}

	public Short getQuantidadeArmazenada() {
		return quantidadeArmazenada;
	}

	public void setQuantidadeArmazenada(Short quantidadeArmazenada) {
		this.quantidadeArmazenada = quantidadeArmazenada;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Short getEstoqueMinimo() {
		return estoqueMinimo;
	}

	public void setEstoqueMinimo(Short estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}

	public Short getEstoqueMinimoArmazem() {
		return estoqueMinimoArmazem;
	}

	public void setEstoqueMinimoArmazem(Short estoqueMinimoArmazem) {
		this.estoqueMinimoArmazem = estoqueMinimoArmazem;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Date getDataControloEstoque() {
		return dataControloEstoque;
	}

	public void setDataControloEstoque(Date dataControloEstoque) {
		this.dataControloEstoque = dataControloEstoque;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Long getChaveEmpresa() {
		return chaveEmpresa;
	}

	public void setChaveEmpresa(Long chaveEmpresa) {
		this.chaveEmpresa = chaveEmpresa;
	}

	public BigDecimal getPrecoIva() {
		return precoIva;
	}

	public void setPrecoIva(BigDecimal precoIva) {
		this.precoIva = precoIva;
	}

	public BigDecimal getPrecoFornecedor() {
		return precoFornecedor;
	}

	public void setPrecoFornecedor(BigDecimal precoFornecedor) {
		this.precoFornecedor = precoFornecedor;
	}

	public BigDecimal getPrecoFornecedorIva() {
		return precoFornecedorIva;
	}

	public void setPrecoFornecedorIva(BigDecimal precoFornecedorIva) {
		this.precoFornecedorIva = precoFornecedorIva;
	}

}

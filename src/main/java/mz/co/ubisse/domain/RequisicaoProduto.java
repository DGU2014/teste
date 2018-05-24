package mz.co.ubisse.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mz.co.ubisse.converter.LocalDateConverter;


@SuppressWarnings("serial")
@Entity
@Table(name = "requisicao_produto")
public class RequisicaoProduto extends GenericDomain {

	@Column(nullable = false, name = "valor_total", precision = 15, scale = 2)
	private BigDecimal valorTotal;
	@Column(name = "data_requisicao", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataRequisicao;
	@Column(name = "data_vencimento", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataVencimento;

	@Convert(converter = LocalDateConverter.class)
	@Column
	private LocalDate data;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Funcionario funcionario;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Fornecedor fornecedor;

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Date getDataRequisicao() {
		return dataRequisicao;
	}

	public void setDataRequisicao(Date dataRequisicao) {
		this.dataRequisicao = dataRequisicao;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

}

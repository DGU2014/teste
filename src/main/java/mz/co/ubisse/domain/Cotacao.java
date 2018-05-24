package mz.co.ubisse.domain;

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

import mz.co.ubisse.converter.LocalDateConverter;

@SuppressWarnings("serial")
@Entity
@Table(name = "cotacao")
public class Cotacao extends GenericDomain {

	@Column(name = "codigo_ct", length = 10, unique = true, nullable = false)
	private String codigoCT;
	@Convert(converter = LocalDateConverter.class)
	@Column
	private LocalDate data;
	@Column(name = "data_validade", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataValidade;
	@Column(name = "observacoes", nullable = true, length = 300)
	private String obseracoes;
	@Column(nullable = true, length = 10)
	private String estado;

	@OneToOne
	@JoinColumn(nullable = false)
	private Venda venda;

	@Column(nullable = false, name = "chave")
	private Long chaveEmpresa;

	public String getCodigoCT() {
		return codigoCT;
	}

	public void setCodigoCT(String codigoCT) {
		this.codigoCT = codigoCT;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getObseracoes() {
		return obseracoes;
	}

	public void setObseracoes(String obseracoes) {
		this.obseracoes = obseracoes;
	}

	public Long getChaveEmpresa() {
		return chaveEmpresa;
	}

	public void setChaveEmpresa(Long chaveEmpresa) {
		this.chaveEmpresa = chaveEmpresa;
	}

}

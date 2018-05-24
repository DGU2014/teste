package mz.co.ubisse.domain;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import mz.co.ubisse.converter.LocalDateConverter;

import java.time.LocalDate;

@SuppressWarnings("serial")
@Entity
@Table(name = "venda_dinheiro")
public class VendaDinheiro extends GenericDomain {

	@Column(name = "codigo_vd", length = 10, unique = true, nullable = false)
	private String codigoVD;

	@Column(name = "observacoes", nullable = true, length = 300)
	private String obseracoes;

	@Column(nullable = true, length = 30)
	private String estado;

	@Column(nullable = false, name = "chave")
	private Long chaveEmpresa;

	@OneToOne
	@JoinColumn(nullable = false)
	private Venda venda;

	@Convert(converter = LocalDateConverter.class)
	@Column
	private LocalDate data;

	public String getCodigoVD() {
		return codigoVD;
	}

	public void setCodigoVD(String codigoVD) {
		this.codigoVD = codigoVD;
	}

	public String getObseracoes() {
		return obseracoes;
	}

	public void setObseracoes(String obseracoes) {
		this.obseracoes = obseracoes;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Long getChaveEmpresa() {
		return chaveEmpresa;
	}

	public void setChaveEmpresa(Long chaveEmpresa) {
		this.chaveEmpresa = chaveEmpresa;
	}

}

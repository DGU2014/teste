package mz.co.ubisse.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "funcionario")
public class Funcionario extends GenericDomain {

	@Column(nullable = true, name = "data_admissao")
	@Temporal(TemporalType.DATE)
	private Date dataAdmissao;
	@OneToOne
	@JoinColumn(nullable = false)
	private Pessoa pessoa;

	@Column(nullable = false)
	private boolean estado;

	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}

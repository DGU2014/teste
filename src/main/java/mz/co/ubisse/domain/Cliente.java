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
@Table(name = "cliente")
public class Cliente extends GenericDomain {

	@Column(name = "data_cadastro", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	@OneToOne
	@JoinColumn(nullable = false)
	private Pessoa pessoa;
	@Column(nullable = false)
	private Boolean estado;

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

}

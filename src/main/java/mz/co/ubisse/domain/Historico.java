package mz.co.ubisse.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name="historico")
public class Historico extends GenericDomain {

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	@Column(nullable = false, length = 200)
	private String historico;

	@Column(nullable = false, length = 10)
	private String tipo;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}

package mz.co.ubisse.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "categoria")
public class Categoria extends GenericDomain {

	@Column(nullable = false, length = 30, unique = false)
	private String nome;

	@Column(nullable = false, name = "chave")
	private Long chaveEmpresa;

	@Column(nullable = true)
	private boolean estado;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getChaveEmpresa() {
		return chaveEmpresa;
	}

	public void setChaveEmpresa(Long chaveEmpresa) {
		this.chaveEmpresa = chaveEmpresa;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}

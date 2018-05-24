package mz.co.ubisse.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name="usuario")
public class Usuario extends GenericDomain {

	@Column(length = 20, nullable = false, unique = true)
	private String usuario;
	@Column(length = 100, nullable = false)
	private String senha;
	@Transient
	private String senhaSemCriptografia;

	@Column(nullable = false)
	private Character tipo;

	@Column(nullable = false)
	private Boolean ativo;

	@OneToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenhaSemCriptografia() {
		return senhaSemCriptografia;
	}

	public void setSenhaSemCriptografia(String senhaSemCriptografia) {
		this.senhaSemCriptografia = senhaSemCriptografia;
	}

	public Character getTipo() {
		return tipo;
	}
	@Transient
	public String getTipoFormatado() {
		String tipoFormatado = null;

		if (tipo == 'A') {
			tipoFormatado = "Administrador";
		} else if (tipo == 'V') {
			tipoFormatado = "Vendedor";
		} else if (tipo == 'G') {
			tipoFormatado = "Gerente";
		}
		return tipoFormatado;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	@Transient
	public String getActivoFormatado() {
		String ativoFormatado = null;
		if (ativo) {
			ativoFormatado="Sim";
		} else {
			ativoFormatado="Nao";
		}
		return ativoFormatado;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

}

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
@Table(name = "pessoa")
public class Pessoa extends GenericDomain {

	@Column(length = 20, nullable = true)
	private String apelido;

	@Column(length = 50, nullable = false)
	private String nome;

	@Column(name = "estado_civil", length = 12, nullable = true)
	private String estadoCivil;

	@Column(name = "genero", length = 10, nullable = true)
	private String genero;

	@Column(name = "data_nascimento", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@Column(name = "identificacao", length = 12, nullable = true)
	private String identificacao;

	@Column(name = "nuit", length = 9, nullable = true)
	private int nuit;

	@Column(length = 50, nullable = true)
	private String pais;

	@Column(length = 30, nullable = true)
	private String localidade;

	@Column(length = 20, nullable = true)
	private String cidade;

	@Column(length = 30, nullable = true)
	private String morada;

	@Column(length = 20, nullable = true)
	private String bairro;

	@Column(length = 15, nullable = true)
	private String celular;

	@Column(length = 15, nullable = true)
	private String telefone;

	@Column(length = 30, nullable = true)
	private String email1;

	@OneToOne
	@JoinColumn(nullable = false)
	private Empresa empresa;

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getNuit() {
		return nuit;
	}

	public void setNuit(int nuit) {
		this.nuit = nuit;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getMorada() {
		return morada;
	}

	public void setMorada(String morada) {
		this.morada = morada;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
package mz.co.ubisse.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "empresa")
public class Empresa extends GenericDomain {

	@Column(length = 50, nullable = false)
	private String nome;

	@Column(length = 50, nullable = true)
	private String slogan;

	@Column(length = 30, nullable = true)
	private String websiste;

	@Column(nullable = false, name = "iva_incluso_total")
	private boolean ivaInclusoTotal;
	// Modo Servico Ou Produto Ou Produto Servico
	@Column(length = 10, nullable = false, name = "modo_funcionamento")
	private String modoFuncionamento;

	@Column(length = 15, nullable = false)
	private String modulo;

	@Column(length = 50, nullable = false)
	private String pais;

	@Column(length = 20, nullable = false)
	private String cidade;

	@Column(length = 50, nullable = false)
	private String morada;

	@Column(name = "celular", length = 15, nullable = true)
	private String celular;

	@Column(name = "celular2", length = 15, nullable = true)
	private String celular2;

	@Column(name = "celular3", length = 15, nullable = true)
	private String celular3;

	@Column(length = 15, nullable = true)
	private String telefone;

	@Column(length = 15, nullable = true)
	private String fax;

	@Column(length = 30, nullable = true)
	private String email;

	@Column(name = "nuit", length = 9, nullable = true)
	private Integer nuit;

	@Column(name = "banco1", length = 20, nullable = true)
	private String banco1;
	@Column(name = "conta1", nullable = true, length = 20, unique = false)
	private String conta1;
	@Column(nullable = true, unique = false, length = 30)
	private String nib1;

	@Column(name = "banco2", length = 20, nullable = true)
	private String banco2;
	@Column(name = "conta2", nullable = true, length = 20, unique = false)
	private String conta2;
	@Column(nullable = true, unique = false, length = 30)
	private String nib2;

	@Column(name = "banco3", length = 20, nullable = true)
	private String banco3;
	@Column(name = "conta3", nullable = true, length = 20, unique = false)
	private String conta3;
	@Column(nullable = true, unique = false, length = 30)
	private String nib3;

	@Column(name = "banco4", length = 20, nullable = true)
	private String banco4;
	@Column(nullable = true, unique = false)
	private Long conta4;
	@Column(nullable = true, unique = false, length = 30)
	private String nib4;

	@Column(name = "activacao_facturacao", length = 15, nullable = true, unique = false)
	private String activacaoFacturacao;

	@Column(name = "activacao_geral", length = 15, nullable = true, unique = false)
	private String activacaoGeral;

	@Column(name = "letra_codigo", length = 2, unique = true)
	private String letraCodigo;

	@Column(name = "isencao", length = 20, nullable = true)
	private String isencao;

	@Transient
	private String logo;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getWebsiste() {
		return websiste;
	}

	public void setWebsiste(String websiste) {
		this.websiste = websiste;
	}

	public boolean isIvaInclusoTotal() {
		return ivaInclusoTotal;
	}

	public void setIvaInclusoTotal(boolean ivaInclusoTotal) {
		this.ivaInclusoTotal = ivaInclusoTotal;
	}

	public String getModoFuncionamento() {
		return modoFuncionamento;
	}

	public void setModoFuncionamento(String modoFuncionamento) {
		this.modoFuncionamento = modoFuncionamento;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
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

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCelular2() {
		return celular2;
	}

	public void setCelular2(String celular2) {
		this.celular2 = celular2;
	}

	public String getCelular3() {
		return celular3;
	}

	public void setCelular3(String celular3) {
		this.celular3 = celular3;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getNuit() {
		return nuit;
	}

	public void setNuit(Integer nuit) {
		this.nuit = nuit;
	}

	public String getLogo() {
		return logo;
	}

	public String getBanco1() {
		return banco1;
	}

	public void setBanco1(String banco1) {
		this.banco1 = banco1;
	}

	public String getConta1() {
		return conta1;
	}

	public void setConta1(String conta1) {
		this.conta1 = conta1;
	}

	public String getNib1() {
		return nib1;
	}

	public void setNib1(String nib1) {
		this.nib1 = nib1;
	}

	public String getBanco2() {
		return banco2;
	}

	public void setBanco2(String banco2) {
		this.banco2 = banco2;
	}

	public String getConta2() {
		return conta2;
	}

	public void setConta2(String conta2) {
		this.conta2 = conta2;
	}

	public String getNib2() {
		return nib2;
	}

	public void setNib2(String nib2) {
		this.nib2 = nib2;
	}

	public String getBanco3() {
		return banco3;
	}

	public void setBanco3(String banco3) {
		this.banco3 = banco3;
	}

	public String getConta3() {
		return conta3;
	}

	public void setConta3(String conta3) {
		this.conta3 = conta3;
	}

	public String getNib3() {
		return nib3;
	}

	public void setNib3(String nib3) {
		this.nib3 = nib3;
	}

	public String getBanco4() {
		return banco4;
	}

	public void setBanco4(String banco4) {
		this.banco4 = banco4;
	}

	public Long getConta4() {
		return conta4;
	}

	public void setConta4(Long conta4) {
		this.conta4 = conta4;
	}

	public String getNib4() {
		return nib4;
	}

	public void setNib4(String nib4) {
		this.nib4 = nib4;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getActivacaoFacturacao() {
		return activacaoFacturacao;
	}

	public void setActivacaoFacturacao(String activacaoFacturacao) {
		this.activacaoFacturacao = activacaoFacturacao;
	}

	public String getActivacaoGeral() {
		return activacaoGeral;
	}

	public void setActivacaoGeral(String activacaoGeral) {
		this.activacaoGeral = activacaoGeral;
	}

	public String getLetraCodigo() {
		return letraCodigo;
	}

	public void setLetraCodigo(String letraCodigo) {
		this.letraCodigo = letraCodigo;
	}

	public String getIsencao() {
		return isencao;
	}

	public void setIsencao(String isencao) {
		this.isencao = isencao;
	}
	
	

}

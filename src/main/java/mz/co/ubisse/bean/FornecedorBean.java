package mz.co.ubisse.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import mz.co.ubisse.dao.FornecedorDAO;
import mz.co.ubisse.dao.PessoaDAO;
import mz.co.ubisse.domain.Fornecedor;
import mz.co.ubisse.domain.Pessoa;
import mz.co.ubisse.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FornecedorBean implements Serializable {

	private Fornecedor fornecedor;
	private List<Fornecedor> fornecedors;
	private Pessoa pessoa;
	private Usuario usuario;

	private AutenticacaoBean autenticacaoBean;

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getFornecedors() {
		return fornecedors;
	}

	public void setFornecedors(List<Fornecedor> fornecedors) {
		this.fornecedors = fornecedors;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public FornecedorBean() {
		autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		usuario = autenticacaoBean.getUsuarioLogado();
	}

	@PostConstruct
	public void inicializar() {
		try {
			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedors = fornecedorDAO.listarFornecedores();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar os Fornecedores");
			erro.printStackTrace();
		}
	}

	public void novo() {
		pessoa = new Pessoa();
		fornecedor = new Fornecedor();
		pessoa.setEmpresa(usuario.getFuncionario().getPessoa().getEmpresa());
	}

	public void salvar() {
		try {
			fornecedor.setDataCadastro(new Date());
			fornecedor.setEstado(true);
			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedorDAO.save(pessoa, fornecedor);

			Messages.addGlobalInfo("Fornecedor salvo com sucesso");

		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar um novo Fornecedor");
			e.printStackTrace();
		}
	}

	public void carregar(ActionEvent evento) {
		fornecedor = (Fornecedor) evento.getComponent().getAttributes().get("fornecedorSelecionado");
		PessoaDAO pessoaDAO = new PessoaDAO();
		try {
			pessoa = pessoaDAO.buscarFornecedor(fornecedor);
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar o FORNECEDOR. Volte a tentar de novo");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent event) {
		fornecedor = (Fornecedor) event.getComponent().getAttributes().get("fornecedorSelecionado");
		try {
			fornecedor.setEstado(false);
			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedorDAO.merge(fornecedor);
			Messages.addFlashGlobalError("Fornecedor exluido com sucesso");
			novo();
			inicializar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar excluir um fornecedor");
			erro.printStackTrace();
		}
	}
}

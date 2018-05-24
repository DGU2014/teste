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

import mz.co.ubisse.dao.EmpresaDAO;
import mz.co.ubisse.dao.FuncionarioDAO;
import mz.co.ubisse.dao.PessoaDAO;
import mz.co.ubisse.domain.Empresa;
import mz.co.ubisse.domain.Funcionario;
import mz.co.ubisse.domain.Pessoa;
import mz.co.ubisse.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FuncionarioBean implements Serializable {

	private Funcionario funcionario;
	private Empresa empresa;
	private List<Funcionario> funcionarios;
	private Usuario usuario;
	private List<Empresa> empresas;

	private AutenticacaoBean autenticacaoBean;

	private Pessoa pessoa;

	private boolean componenteEmpresa;
	private Long codigoEmpresa;

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public boolean isComponenteEmpresa() {
		return componenteEmpresa;
	}

	public void setComponenteEmpresa(boolean componenteEmpresa) {
		this.componenteEmpresa = componenteEmpresa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public FuncionarioBean() {
		autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		usuario = autenticacaoBean.getUsuarioLogado();
		codigoEmpresa = usuario.getFuncionario().getPessoa().getEmpresa().getCodigo();
	}

	@PostConstruct
	public void inicializar() {
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			if (usuario.getFuncionario().getPessoa().getEmpresa().getNome().equals("Ubisse")) {
				funcionarios = funcionarioDAO.listarFuncionario();
			} else {
				funcionarios = funcionarioDAO
						.listarFuncionario(codigoEmpresa);
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar os funcionarios");
			erro.printStackTrace();
		}
	}

	public void empresas() {
		try {
			EmpresaDAO empresaDAO = new EmpresaDAO();
			empresas = empresaDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as empresas");
			erro.printStackTrace();
		}
	}

	public void novo() {
		empresa = new Empresa();

		pessoa = new Pessoa();
		funcionario = new Funcionario();
		funcionario.setEstado(true);

		System.out.println("empresa do func" + usuario.getFuncionario().getPessoa().getEmpresa().getNome());
		if (usuario.getFuncionario().getPessoa().getEmpresa().getNome().equals("Ubisse")) {
			empresas();
			componenteEmpresa = true;
			System.out.println("verdadeiro comp");
		} else {
			componenteEmpresa = false;
			System.out.println("falso comp");
			pessoa.setEmpresa(usuario.getFuncionario().getPessoa().getEmpresa());
		}
	}

	public void salvar() {
		try {
			if (usuario.getFuncionario().getPessoa().getEmpresa().getNome().equals("Ubisse")) {
				pessoa.setEmpresa(empresa);
			}
			funcionario.setDataAdmissao(new Date());
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.save(pessoa, funcionario);
			Messages.addGlobalInfo("funcionarior salvo com sucesso");

		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar um novo funcionarior");
			e.printStackTrace();
		}
	}

	public void carregar(ActionEvent evento) {
		funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");
		PessoaDAO pessoaDAO = new PessoaDAO();
		try {
			pessoa = pessoaDAO.buscarFuncionario(funcionario);
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar o funcionario. Volte a tentar de novo");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent event) {
		funcionario = (Funcionario) event.getComponent().getAttributes().get("funcionarioSelecionado");
		try {
			funcionario.setEstado(false);
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.merge(funcionario);
			Messages.addFlashGlobalError("exluido com sucesso");
			novo();
			inicializar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar excluir um funcionario");
			erro.printStackTrace();
		}
	}
}

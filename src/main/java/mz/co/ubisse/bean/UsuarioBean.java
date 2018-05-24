package mz.co.ubisse.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import mz.co.ubisse.dao.FuncionarioDAO;
import mz.co.ubisse.dao.UsuarioDAO;
import mz.co.ubisse.domain.Funcionario;
import mz.co.ubisse.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

	private Usuario usuario;

	private List<Usuario> usuarios;
	private List<Funcionario> funcionarios;
	private Usuario usuarioLogado;
	private AutenticacaoBean autenticacaoBean;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public UsuarioBean() {
		autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		usuarioLogado = autenticacaoBean.getUsuarioLogado();
	}

	@PostConstruct
	public void listar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarios = usuarioDAO.listarUsuarios(usuarioLogado.getFuncionario().getPessoa().getEmpresa().getCodigo());
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar os Usuarios");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			usuario = new Usuario();
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO
					.listarFuncionario(usuarioLogado.getFuncionario().getPessoa().getEmpresa().getCodigo());
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar um novo Usuario");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {

			SimpleHash hash = new SimpleHash("md5", usuario.getSenhaSemCriptografia());
			usuario.setSenha(hash.toHex());

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioDAO.merge(usuario);

			usuario = new Usuario();

			usuarios = usuarioDAO.listarUsuarios(usuarioLogado.getFuncionario().getPessoa().getEmpresa().getCodigo());

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO
					.listarFuncionario(usuario.getFuncionario().getPessoa().getEmpresa().getCodigo());

			Messages.addGlobalInfo("Usuario salvo com sucesso");
			listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar um novo Usuario");
			erro.printStackTrace();
		}
	}

	public void carregarUsuario(ActionEvent event) {
		usuario = (Usuario) event.getComponent().getAttributes().get("usuarioSelecionado");
	}

	public void excluir(ActionEvent event) {
		usuario = (Usuario) event.getComponent().getAttributes().get("usuarioSelecionado");
		try {
			usuario.setAtivo(false);
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioDAO.merge(usuario);
			Messages.addFlashGlobalError("excluido com sucesso");
			novo();
			listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar excluir um produto");
			erro.printStackTrace();
		}
	}
}

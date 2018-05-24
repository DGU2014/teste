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

import mz.co.ubisse.dao.ClienteDAO;
import mz.co.ubisse.dao.PessoaDAO;
import mz.co.ubisse.domain.Cliente;
import mz.co.ubisse.domain.Pessoa;
import mz.co.ubisse.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {

	private Cliente cliente;
	private Pessoa pessoa;
	private Usuario usuario;

	private AutenticacaoBean autenticacaoBean;

	private List<Pessoa> pessoas;
	private List<Cliente> clientes;

	private Long chave;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public ClienteBean() {
		autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		usuario = autenticacaoBean.getUsuarioLogado();
		chave = usuario.getFuncionario().getPessoa().getEmpresa().getCodigo();
	}

	@PostConstruct
	public void listarCliente() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listarClientes(chave);
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar os Clientes");
			erro.printStackTrace();
		}
	}

	public void novoCliente() {
		pessoa = new Pessoa();
		cliente = new Cliente();
		pessoa.setEmpresa(usuario.getFuncionario().getPessoa().getEmpresa());
	}

	public void salvar() {
		try {
			if (pessoa.getNome() == null) {
				Messages.addFlashGlobalWarn("Obrigatorio indicar o nome do Cliente");
			} else {
				System.out.println("Nome do Cliente " + pessoa.getNome());

				cliente.setEstado(true);
				cliente.setDataCadastro(new Date());

				ClienteDAO clienteDAO = new ClienteDAO();
				clienteDAO.save(pessoa, cliente);
				novoCliente();
				listarCliente();
				Messages.addFlashGlobalInfo("Cliente salvo com sucesso");
			}
		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar novo Cliente");
			e.printStackTrace();
		}

	}

	public void carregarCliente(ActionEvent evento) {
		cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
		PessoaDAO pessoaDAO = new PessoaDAO();
		try {
			pessoa = pessoaDAO.buscarCliente(cliente);
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar o Cliente. Volte a tentar de novo");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent event) {
		cliente = (Cliente) event.getComponent().getAttributes().get("clienteSelecionado");
		try {
			cliente.setEstado(false);
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.merge(cliente);
			Messages.addFlashGlobalError("Cliente excluido com sucesso");
			listarCliente();
			novoCliente();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar excluir um cliente");
			erro.printStackTrace();
		}
	}

}

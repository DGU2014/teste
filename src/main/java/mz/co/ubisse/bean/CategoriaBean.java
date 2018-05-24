package mz.co.ubisse.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import mz.co.ubisse.dao.CategoriaDAO;
import mz.co.ubisse.domain.Categoria;
import mz.co.ubisse.domain.Empresa;
import mz.co.ubisse.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CategoriaBean implements Serializable {

	private Categoria categoria;
	private Empresa empresaReselva;
	private Usuario usuario;
	private List<Categoria> categorias;

	private AutenticacaoBean autenticacaoBean;

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public void novo() {
		categoria = new Categoria();
	}

	public CategoriaBean() {
		autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		usuario = autenticacaoBean.getUsuarioLogado();

		empresaReselva = usuario.getFuncionario().getPessoa().getEmpresa();
	}

	@PostConstruct
	public void listar() {
		novo();
		try {
			CategoriaDAO categoriaDAO = new CategoriaDAO();
			categorias = categoriaDAO.listarPorEmpresa(empresaReselva.getCodigo());
		} catch (RuntimeException e) {

			Messages.addGlobalError("Ocorreu um erro ao tentar listar as Categorias");
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (categoria.equals(null) || categoria.getNome() == "") {
				Messages.create("Warning!").warn().detail("Obrigatorio indicar o Nome da Categoria.").add();
			} else {
				categoria.setChaveEmpresa(empresaReselva.getCodigo());
				CategoriaDAO categoriaDAO = new CategoriaDAO();
				categoriaDAO.merge(categoria);
				Messages.create("Info::").detail("Categoria salva com sucesso.").add();
				novo();
				listar();
			}
		} catch (RuntimeException e) {
			Messages.create("Erro::").error()
					.detail("Ocorreu um erro ao tentar salvar a Categoria. "
							+ "Volte a tentar por favor, se o erro persistir contacte o Administrador do Sistema.")
					.add();
			e.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		categoria = (Categoria) evento.getComponent().getAttributes().get("categoriaSelecionada");
	}

	public void excluir(ActionEvent evento) {
		categoria = (Categoria) evento.getComponent().getAttributes().get("categoriaSelecionada");
		CategoriaDAO categoriaDAO = new CategoriaDAO();
		try {
			categoriaDAO.excluir(categoria);
			Messages.create("Info::").detail("Categoria excluida com sucesso.").add();
			listar();
		} catch (RuntimeException e) {
			e.printStackTrace();

			Messages.create("Info::").warn()
					.detail("Não é possivel apagar a  categoria porque se estiver relaçionada com algum produto"
							+ " verifique se a categoria não se encontra vinculda, se o erro persistir contacte o Administrador do Sistema UBI.")
					.add();

		}
		novo();
	}
}

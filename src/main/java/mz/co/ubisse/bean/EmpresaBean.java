package mz.co.ubisse.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import mz.co.ubisse.dao.EmpresaDAO;
import mz.co.ubisse.domain.Empresa;
import mz.co.ubisse.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EmpresaBean implements Serializable {

	private Empresa empresa;
	private List<Empresa> empresas;

	private AutenticacaoBean autenticacaoBean;
	private Usuario usuario;
	private Long codigoEmpresa;

	private boolean painelCadastro;
	private boolean painelListar;
	private boolean btnAlterar;
	private boolean btnCancelar;

	private String pastaRaiz;
	private File directorioRaiz;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public boolean isPainelCadastro() {
		return painelCadastro;
	}

	public void setPainelCadastro(boolean painelCadastro) {
		this.painelCadastro = painelCadastro;
	}

	public boolean isPainelListar() {
		return painelListar;
	}

	public void setPainelListar(boolean painelListar) {
		this.painelListar = painelListar;
	}

	public boolean isBtnAlterar() {
		return btnAlterar;
	}

	public void setBtnAlterar(boolean btnAlterar) {
		this.btnAlterar = btnAlterar;
	}

	public boolean isBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(boolean btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public String getPastaRaiz() {
		return pastaRaiz;
	}

	public void setPastaRaiz(String pastaRaiz) {
		this.pastaRaiz = pastaRaiz;
	}

	public EmpresaBean() {
		autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		usuario = autenticacaoBean.getUsuarioLogado();
		codigoEmpresa = usuario.getFuncionario().getPessoa().getEmpresa().getCodigo();

		Path path = Paths.get(FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/resources/images/ubiLogo"));

		FacesContext context = FacesContext.getCurrentInstance();
		ServletContext sc = (ServletContext) context.getExternalContext().getContext();
		sc.getRealPath("/resources/images/ubiLogo");

		pastaRaiz = path.toString();

		// pastaRaiz = System.getProperty("user.home");
	}

	@PostConstruct
	public void listar() {
		try {
			EmpresaDAO empresaDAO = new EmpresaDAO();
			if (usuario.getFuncionario().getPessoa().getEmpresa().getNome().equals("Ubisse")) {
				empresas = empresaDAO.listar();
				cancelar();
			} else {
				empresa = empresaDAO.buscar(codigoEmpresa);
				System.out.println("nome da empresa " + empresa.getNome());
				btnAlterar = true;
				painelCadastro = true;
			}

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao Listar as Empresas");
			erro.printStackTrace();
		}
	}

	public void novo() {
		painelListar = false;
		painelCadastro = true;
		btnCancelar = true;
		empresa = new Empresa();
	}

	public void cancelar() {
		painelListar = true;
		painelCadastro = false;
		btnCancelar = false;
	}

	public void salvar() {
		try {

			String logoTipo = empresa.getLogo();

			EmpresaDAO empresaDAO = new EmpresaDAO();
			if (empresa.getCodigo() == null) {
				empresa = empresaDAO.salva(empresa);
			} else {
				empresa = empresaDAO.merge(empresa);
			}

			System.out.println("codigo empresa " + empresa.getCodigo());
			Path origem = Paths.get(logoTipo);
			System.out.println("Origem " + origem);
			Path destino = Paths.get(directorioRaiz + "/" + empresa.getCodigo() + ".png");
			System.out.println("destino " + destino);
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

			System.out.println("destino " + destino);
			if (empresa.getCodigo() == null) {
				Messages.create("Info::").detail("Empresa Salva/Alterada com Sucesso.").add();
			} else {
				Messages.create("Info::").detail("Empresa Actualizada com Sucesso.").add();
			}
			listar();
		} catch (RuntimeException | IOException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar a Empresa");
			erro.printStackTrace();
		}
	}

	public void carregarEmpresa(ActionEvent event) {
		empresa = (Empresa) event.getComponent().getAttributes().get("empresaSelecionada");

		painelListar = false;
		painelCadastro = true;
		btnCancelar = true;
	}

	public void excluir(ActionEvent event) {
		empresa = (Empresa) event.getComponent().getAttributes().get("empresaSelecionada");
		try {
			EmpresaDAO empresaDAO = new EmpresaDAO();
			empresaDAO.excluir(empresa);
			Messages.create("Info::").detail("Excluido com Sucesso.").add();
			listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar excluir a Empresa");
			erro.printStackTrace();
		}
	}

	public void upload(FileUploadEvent event) {

		try {
			UploadedFile uploadedFile = event.getFile();
			Path temporario = Files.createTempFile(null, null);
			Files.copy(uploadedFile.getInputstream(), temporario, StandardCopyOption.REPLACE_EXISTING);
			empresa.setLogo(temporario.toString());
			System.out.println("primeiro caminho " + temporario.toString());

			directorioRaiz = new File(pastaRaiz);
			/*
			 * directorioRaiz = new File(pastaRaiz + "/" + "ubiLogo");
			 * 
			 * System.out.println("caminho do directorio " + directorioRaiz.toString()); if
			 * (!directorioRaiz.exists()) { directorioRaiz.mkdirs(); }
			 */

		} catch (IOException e) {
			Messages.create("Fatal!").fatal().detail("Erro ao tentar fazer o upload do Logotipo.").add();
			e.printStackTrace();
		}
	}
}

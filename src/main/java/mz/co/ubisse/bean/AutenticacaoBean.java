package mz.co.ubisse.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import mz.co.ubisse.dao.HistoricoDAO;

import mz.co.ubisse.dao.UsuarioDAO;
import mz.co.ubisse.domain.Historico;
import mz.co.ubisse.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class AutenticacaoBean implements Serializable {

	private Usuario usuario;
	private Usuario usuarioLogado;
	private Historico historico;
	private String script;
	private String msg1;
	private String msg2;
	private String msg3;
	private boolean activado;
	private String caminhoLogo;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public boolean isActivado() {
		return activado;
	}

	public void setActivado(boolean activado) {
		this.activado = activado;
	}

	public String getMsg1() {
		return msg1;
	}

	public void setMsg1(String msg1) {
		this.msg1 = msg1;
	}

	public String getMsg2() {
		return msg2;
	}

	public void setMsg2(String msg2) {
		this.msg2 = msg2;
	}

	public String getMsg3() {
		return msg3;
	}

	public void setMsg3(String msg3) {
		this.msg3 = msg3;
	}

	public String getCaminhoLogo() {
		return caminhoLogo;
	}

	public void setCaminhoLogo(String caminhoLogo) {
		this.caminhoLogo = caminhoLogo;
	}

	@PostConstruct
	public void iniciar() {
		usuario = new Usuario();
		historico = new Historico();
	}

	public void logoTipo() {
		Path path = Paths
				.get(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/ubiLogo/"
						+ usuarioLogado.getFuncionario().getPessoa().getEmpresa().getCodigo() + ".png"));
		caminhoLogo = path.toString();
	}

	public void autenticar() {
		try {

			msg1 = "autenticando";
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioLogado = usuarioDAO.autenticar(usuario.getUsuario(), usuario.getSenha());

			if (usuarioLogado == null) {
				Messages.create("Acesso N�o Autorizado").fatal().detail("Senha ou Usuario errado.").add();
				historico.setData(new Date());
				historico.setTipo("Login");
				historico.setHistorico("Tentativas de aceder o sistema Utilizador = " + usuario.getUsuario()
						+ " Senha = " + usuario.getSenha());
				HistoricoDAO historicoDAO = new HistoricoDAO();
				historicoDAO.salvar(historico);
				return;
			} else {
				historico.setData(new Date());
				historico.setTipo("Login");
				historico.setHistorico("O Funcionario numero:" + usuarioLogado.getFuncionario().getCodigo() + " nome: "
						+ usuarioLogado.getFuncionario().getPessoa().getNome() + " acedeu o sistema");
				HistoricoDAO historicoDAO = new HistoricoDAO();
				historicoDAO.salvar(historico);
				Character v = 'V';
				if (usuarioLogado.getTipo() == v) {
					Faces.getFlash().setKeepMessages(true);
					Messages.addGlobalInfo("Bem Vindo " + usuarioLogado.getFuncionario().getPessoa().getNome());
					activado = true;
					Faces.redirect("./pages/venda/Venda.xhtml");
					// gerar();
					msg3 = "Bem Vindo";
				} else {
					Messages.create("Bem Vindo ").detail(usuarioLogado.getFuncionario().getPessoa().getNome()).add();
					Faces.getFlash().setKeepMessages(true);
					activado = true;
					Faces.redirect("index.xhtml");

					// gerar();
					msg3 = "Bem Vindo";
				}
				historico = new Historico();
				logoTipo();
			}
		} catch (IOException e) {
			e.printStackTrace();
			Messages.addFlashGlobalError(e.getMessage());
		}
	}

	public boolean temPermissoes(List<String> permissoes) {
		for (String permissao : permissoes) {
			if (usuarioLogado.getTipo() == permissao.charAt(0)) {
				return true;
			}
		}

		return false;
	}

	public void sair() {
		historico.setData(new Date());
		historico.setTipo("Login");
		historico.setHistorico("O Funcionario numero:" + usuarioLogado.getFuncionario().getCodigo() + " nome: "
				+ usuarioLogado.getFuncionario().getPessoa().getNome() + " saiu do sistema");
		usuarioLogado = null;
		try {
			HistoricoDAO historicoDAO = new HistoricoDAO();
			historicoDAO.salvar(historico);
			Faces.redirect("login.xhtml");
			gerar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	@SuppressWarnings("unused")
	private static String VERSION = "5.7.14";

	private static String SEPARATOR = File.separator;

	private static String MYSQL_PATH =

			"C:" + SEPARATOR +

					"wamp" + SEPARATOR + "bin" + SEPARATOR +

					"MySQL" + SEPARATOR +

					"mysql5.7.14" + SEPARATOR +

					"bin" + SEPARATOR;

	// Lista dos bancos de dados a serem "backupeados"; se desejar adicionar
	// mais,

	// basta colocar o nome separado por espaços dos outros nomes

	private static String DATABASES =

			"ubisse_ubi_v1";

	private List<String> dbList = new ArrayList<String>();

	@Test
	@Ignore
	public void gerar() {
		msg2 = "Actualizando a Base de Dados";
		DateTime dateTime = new DateTime();
		System.out.println("data detetime " + dateTime.getMillis());
		String command = MYSQL_PATH + "mysqldump.exe";

		String[] databases = DATABASES.split(" ");

		for (int i = 0; i < databases.length; i++)

			dbList.add(databases[i]);

		System.out.println("Iniciando backups...\n\n");

		// Contador

		int i = 1;

		// Tempo

		long time1, time2, time;

		// Início

		time1 = System.currentTimeMillis();

		for (String dbName : dbList) {

			ProcessBuilder pb = new ProcessBuilder(

					command,

					"--user=root",

					"--password=",

					dbName,

					"--result-file=" + "C:" + SEPARATOR + "Backup" + SEPARATOR + dateTime.getMillisOfDay() + "-"
							+ dateTime.getDayOfMonth() + "-" + dateTime.getMonthOfYear() + "-" + dateTime.getYear()
							+ dbName + ".sql");

			try {

				System.out.println(

						"Backup do banco de dados (" + i + "): " + dbName + " ...");

				pb.start();

				pb = new ProcessBuilder(command,

						"--user=root",

						"--password=",

						dbName,

						"--result-file=" + "C:" + SEPARATOR + "Backup" + SEPARATOR + dateTime.getMillisOfDay() + "-"
								+ dateTime.getDayOfMonth() + "-" + dateTime.getMonthOfYear() + "-" + dateTime.getYear()
								+ dbName + ".sql");

			}

			catch (Exception e) {

				e.printStackTrace();

			}

			i++;

		}

		// Fim

		time2 = System.currentTimeMillis();

		// Tempo total da operação

		time = time2 - time1;

		// Avisa do sucesso

		System.out.println("\nBackups realizados com sucesso.\n\n");

		System.out.println("Tempo total de processamento: " + time + " ms\n");

		System.out.println("Finalizando...");

		try {

			// Paralisa por 2 segundos

			Thread.sleep(2000);

		}

		catch (Exception e) {
		}

		// Termina o aplicativo

		// System.exit(0);

	}
}

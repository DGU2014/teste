package mz.co.ubisse.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
//import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

//import org.apache.commons.mail.EmailException;
//import org.apache.commons.mail.SimpleEmail;
import org.joda.time.DateTime;
//import org.omnifaces.util.Messages;
import org.omnifaces.util.Faces;

import mz.co.ubisse.dao.ProdutoDAO;
import mz.co.ubisse.domain.Produto;
import mz.co.ubisse.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class InicioBean implements Serializable {

	private Produto produto;
	private List<Produto> produtos;
	private List<Produto> produtosArmazem;
	private Usuario usuario;
	private AutenticacaoBean autenticacaoBean;
	private Long codigoEmpresa;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Produto> getProdutosArmazem() {
		return produtosArmazem;
	}

	public void setProdutosArmazem(List<Produto> produtosArmazem) {
		this.produtosArmazem = produtosArmazem;
	}

	@SuppressWarnings("unlikely-arg-type")
	@PostConstruct
	public void controloEstoque() {
		autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		usuario = autenticacaoBean.getUsuarioLogado();
		codigoEmpresa = usuario.getFuncionario().getPessoa().getEmpresa().getCodigo();

		ProdutoDAO produtoDAO = new ProdutoDAO();

		if (usuario.getFuncionario().getPessoa().getEmpresa().getNome().equals("Ubisse")) {
			produtos = produtoDAO.listarEstoqueNegativo();
			produtosArmazem = produtoDAO.listarEstoqueNegativoArmazem();
		} else {
			produtos = produtoDAO.listarEstoqueNegativo();
			produtosArmazem = produtoDAO.listarEstoqueNegativoArmazem();
		}

		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();

		for (Produto produto : produtos) {

			if (produto.getDataControloEstoque() == null) {
				Calendar cal = Calendar.getInstance();
				cal.set(DateTime.now().getYear(), DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth() - 1);
				produto.setDataControloEstoque(cal.getTime());
				produtoDAO.merge(produto);
			} else {
				if (produto.getDataControloEstoque().equals(localDate.format(formatador))) {

				} else {
					/*
					 * try { DateTime dateTime = new DateTime(); produto.setData7ControloEstoque(new
					 * Date());
					 * 
					 * SimpleEmail email = new SimpleEmail(); email.setHostName("smtp.gmail.com");
					 * email.setSmtpPort(587); email.addTo("softprogdc@gmail.com", "Programador");
					 * email.setFrom("turmaupab@gmail.com", "SOFTWARE DE GESTÃO DE ESTOQUE V.1");
					 * email.setSubject("Aviso de Estoque Negativo dia " + dateTime.getDayOfMonth()
					 * + "/" + dateTime.getDayOfMonth() + "/" + dateTime.getYear());
					 * email.setMsg("O Produto " + produto.getNome() + " Referencia" +
					 * produto.getReferencia() + "" + " esta com o estoque negativo " +
					 * produto.getQuantidade());
					 * 
					 * email.setSSL(true); email.setAuthentication("turmaupab", "informaticaab");
					 * email.send();
					 * 
					 * produtoDAO.merge(produto);
					 * 
					 * System.out.println("Estoque negativo enviando email"); } catch
					 * (RuntimeException erro) { Messages.addFlashGlobalError(
					 * "Ocorreu um erro ao tentar enviar o email dos produtos com estoque negativo"
					 * ); erro.printStackTrace(); } catch (EmailException e) {
					 * Messages.addFlashGlobalError(
					 * "Ocorreu um erro ao tentar enviar o email dos produtos com estoque negativo"
					 * ); e.printStackTrace(); }
					 */
				}
			}
		}

	}
}

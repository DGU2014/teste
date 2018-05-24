package mz.co.ubisse.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import mz.co.ubisse.dao.RequisicaoDAO;
import mz.co.ubisse.domain.RequisicaoProduto;

@ManagedBean
@ViewScoped
public class AquisicaoBean {

	private RequisicaoProduto requisicaoProduto;
	private List<RequisicaoProduto> requisicaoProdutos;
	private Long codigo;

	public List<RequisicaoProduto> getRequisicaoProdutos() {
		return requisicaoProdutos;
	}

	public void setRequisicaoProdutos(List<RequisicaoProduto> requisicaoProdutos) {
		this.requisicaoProdutos = requisicaoProdutos;
	}

	public RequisicaoProduto getRequisicaoProduto() {
		return requisicaoProduto;
	}

	public void setRequisicaoProduto(RequisicaoProduto requisicaoProduto) {
		this.requisicaoProduto = requisicaoProduto;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@PostConstruct
	public void listar() {
		try {
			requisicaoProduto = new RequisicaoProduto();
			RequisicaoDAO requisicaoDAO = new RequisicaoDAO();
			requisicaoProdutos = requisicaoDAO.listar();
		} catch (Exception erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar a Entrada dos Produtos");
			erro.printStackTrace();
		}

	}

	public void carregarAquisicao(ActionEvent event) {

		requisicaoProduto = (RequisicaoProduto) event.getComponent().getAttributes().get("aquisicaoSelecionada");

		setCodigo(requisicaoProduto.getCodigo());
		System.out.println("venda carregada nr 2 " + getCodigo());

	}

	public RequisicaoProduto getByQuery() {
		System.out.println("venda carregada nr 3 " + getCodigo());
		Long c = 1L;
		System.out.println(c);
		RequisicaoDAO requisicaoDAO = new RequisicaoDAO();
		return requisicaoDAO.readAll(c);
		// return null;
	}
}

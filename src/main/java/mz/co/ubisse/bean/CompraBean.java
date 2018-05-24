package mz.co.ubisse.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import mz.co.ubisse.dao.FornecedorDAO;
import mz.co.ubisse.dao.ProdutoDAO;
import mz.co.ubisse.dao.RequisicaoDAO;
import mz.co.ubisse.domain.Empresa;
import mz.co.ubisse.domain.Fornecedor;
import mz.co.ubisse.domain.Historico;
import mz.co.ubisse.domain.ItemsRequisicao;
import mz.co.ubisse.domain.Produto;
import mz.co.ubisse.domain.RequisicaoProduto;
import mz.co.ubisse.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CompraBean implements Serializable {

	private Fornecedor fornecedor;
	private RequisicaoProduto requisicaoProduto;
	private ItemsRequisicao itemsRequisicao;
	private Produto produto;
	private Historico historico;
	private Empresa empresaReselva;

	private List<ItemsRequisicao> itemsRequisicaos;
	private List<RequisicaoProduto> requisicaoProdutos;
	private List<Produto> produtos;
	private List<Fornecedor> fornecedors;

	private AutenticacaoBean autenticacaoBean;
	private Usuario usuario;

	private Short quantidade;

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public RequisicaoProduto getRequisicaoProduto() {
		return requisicaoProduto;
	}

	public void setRequisicaoProduto(RequisicaoProduto requisicaoProduto) {
		this.requisicaoProduto = requisicaoProduto;
	}

	public ItemsRequisicao getItemsRequisicao() {
		return itemsRequisicao;
	}

	public void setItemsRequisicao(ItemsRequisicao itemsRequisicao) {
		this.itemsRequisicao = itemsRequisicao;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<ItemsRequisicao> getItemsRequisicaos() {
		return itemsRequisicaos;
	}

	public void setItemsRequisicaos(List<ItemsRequisicao> itemsRequisicaos) {
		this.itemsRequisicaos = itemsRequisicaos;
	}

	public List<RequisicaoProduto> getRequisicaoProdutos() {
		return requisicaoProdutos;
	}

	public void setRequisicaoProdutos(List<RequisicaoProduto> requisicaoProdutos) {
		this.requisicaoProdutos = requisicaoProdutos;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Fornecedor> getFornecedors() {
		return fornecedors;
	}

	public void setFornecedors(List<Fornecedor> fornecedors) {
		this.fornecedors = fornecedors;
	}

	public Short getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Short quantidade) {
		this.quantidade = quantidade;
	}

	public CompraBean() {
		autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		usuario = autenticacaoBean.getUsuarioLogado();

	}

	@PostConstruct
	public void novo() {
		try {
			empresaReselva = usuario.getFuncionario().getPessoa().getEmpresa();
			itemsRequisicaos = new ArrayList<>();
			requisicaoProduto = new RequisicaoProduto();
			historico = new Historico();
			historico.setData(new Date());
			historico.setTipo("Compra");
			requisicaoProduto.setData(LocalDate.now());

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listarActivos("codigo", empresaReselva.getCodigo());

			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedors = fornecedorDAO.listar();

		} catch (RuntimeException erro) {
			Messages.create("Error!").error().detail(
					"Ocorreu um erro durante o carregamento da tela de Compra .Tente de novo Por Favor. Se erro persistir contacte o Administrador do Sitema UBI")
					.add();
			erro.printStackTrace();
		}

	}

	public void mostrarNasTex() {
		try {

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produto = produtoDAO.buscarPorProduto(produto.getCodigo());

			setQuantidade(new Short("1"));

		} catch (RuntimeException erro) {
			Messages.create("Error!").error().detail(
					"Ocorreu um erro durante a seleção do Produto.Tente de novo. se erro persistir contacte o Administrador do Sitema UBI")
					.add();
			erro.printStackTrace();
		}
	}

	public void adicionar(ActionEvent evento) {

		if (produto.equals(null) && produto.getPreco() == null) {

			Messages.create("Aviso!").warn().detail("É NECESSARIO SELECIONAR O PRODUTO DA COMPRA.").add();
			System.out.println("Produto " + produto);
		} else {
			quantidade = getQuantidade();

			int achou = -1;
			for (int i = 0; i < itemsRequisicaos.size(); i++) {
				if (itemsRequisicaos.get(i).getProduto().equals(produto)) {
					achou = i;
				}
			}
			if (achou < 0) {
				itemsRequisicao = new ItemsRequisicao();
				itemsRequisicao.setProduto(produto);
				itemsRequisicao.setQuantidade(quantidade);
				itemsRequisicaos.add(itemsRequisicao);
			} else {
				itemsRequisicao = new ItemsRequisicao();
				ItemsRequisicao itemsRequisicao = itemsRequisicaos.get(achou);
				itemsRequisicao.setQuantidade(new Short(itemsRequisicao.getQuantidade() + quantidade + ""));
			}
		}

	}

	public void remover(ActionEvent evento) {
		ItemsRequisicao itemsRequisicao = (ItemsRequisicao) evento.getComponent().getAttributes()
				.get("itemSelecionado");
		int achou = -1;
		for (int i = 0; i < itemsRequisicaos.size(); i++) {
			if (itemsRequisicaos.get(i).getProduto().equals(produto)) {
				achou = i;
			}
		}
		if (achou > -1) {

			quantidade = 1;

			itemsRequisicao.setQuantidade(new Short(itemsRequisicao.getQuantidade() - quantidade + ""));
			itemsRequisicaos.remove(achou);
		}
	}

	public void salvar() {
		try {
			requisicaoProduto.setFornecedor(requisicaoProduto.getFornecedor());
			requisicaoProduto.setFuncionario(usuario.getFuncionario());
			RequisicaoDAO requisicaoDAO = new RequisicaoDAO();
			historico.setHistorico("O funcionario " + usuario.getFuncionario().getPessoa().getNome() + " "
					+ " de entrada de produtos na hora " + historico.getData());
			requisicaoDAO.salvar(requisicaoProduto, itemsRequisicaos, historico);
			Messages.create("Sucesso::").detail("Operação realizada com sucesso.").add();
		} catch (Exception erro) {
			Messages.create("Error!").error().detail(
					"Ocorreu um erro ao tentar salvar a Compra. Tente de novo. Se erro persistir contacte o Administrador do Sitema UBI")
					.add();
			erro.printStackTrace();
		}
	}
}

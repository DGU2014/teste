package mz.co.ubisse.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import mz.co.ubisse.dao.CategoriaDAO;
import mz.co.ubisse.dao.ProdutoDAO;
import mz.co.ubisse.domain.Categoria;
import mz.co.ubisse.domain.Historico;
import mz.co.ubisse.domain.Produto;
import mz.co.ubisse.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable {

	private Produto produto;
	private Usuario usuarioLogado;
	private Historico historico;
	private List<Produto> produtos;
	private List<Categoria> categorias;

	private short quantidadePorAdicionar;
	private short controlo;
	public short quantidadeLoja;

	private boolean painelCadastro;
	private boolean painelListar;

	private Long chave;

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

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public short getQuantidadePorAdicionar() {
		return quantidadePorAdicionar;
	}

	public void setQuantidadePorAdicionar(short quantidadePorAdicionar) {
		this.quantidadePorAdicionar = quantidadePorAdicionar;
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

	public ProdutoBean() {
		AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		usuarioLogado = autenticacaoBean.getUsuarioLogado();
		chave = usuarioLogado.getFuncionario().getPessoa().getEmpresa().getCodigo();
	}

	@PostConstruct
	public void listar() {
		try {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listarActivos("codigo", chave);
			cancelar();
		} catch (RuntimeException erro) {
			Messages.create("Erro:: ").error().detail(
					"Ocorreu um erro ao tentar carregar a tela de produtos. TENTE DE NOVO. Se o erro persistir contacte o Administrador do Sistema UBI")
					.add();
			erro.printStackTrace();
		}
	}

	public void cancelar() {
		painelListar = true;
		painelCadastro = false;
	}

	public void novo() {
		try {
			painelListar = false;
			painelCadastro = true;
			produto = new Produto();
			produto.setEstado(true);
			produto.setChaveEmpresa(chave);
			CategoriaDAO categoriaDAO = new CategoriaDAO();
			categorias = categoriaDAO.listarPorEmpresa(chave);
			setQuantidadePorAdicionar((short) 0);

			historico = new Historico();
			historico.setHistorico("O funcionario nr " + usuarioLogado.getFuncionario().getCodigo() + " Nome "
					+ usuarioLogado.getFuncionario().getPessoa().getNome() + " Cadastrou o " + " o produto "
					+ produto.getReferencia() + " quantidade armazem" + produto.getQuantidadeArmazenada() + " qtd loja "
					+ produto.getQuantidade() + " Data: " + historico.getData());

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar um novo Produto");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			historico.setTipo("Produtos");
			historico.setData(new Date());
			if (produto.getQuantidade() == null) {
				produto.setQuantidade((short) 0);
			}
			if (produto.getEstoqueMinimo() == null) {
				produto.setEstoqueMinimo((short) 0);
			}
			if (produto.getEstoqueMinimoArmazem() == null) {
				produto.setEstoqueMinimoArmazem((short) 0);
			}
			if (produto.getQuantidadeArmazenada() == null) {
				produto.setQuantidadeArmazenada((short) 0);
			}
			ProdutoDAO produtoDAO = new ProdutoDAO();
			if (getQuantidadePorAdicionar() > controlo && controlo == -100) {
				Messages.create("Erro:: ").error()
						.detail("Não ha quantidade suficiente no estoque armazenado, por favor, reduza a quantidade.")
						.add();

			} else {
				System.out.println(" salvar");
				produtoDAO.merge(produto, historico);
				Messages.create("Sucesso::").detail("Produto Salvo.").add();
				novo();
				listar();
			}
		} catch (RuntimeException erro) {
			Messages.create("Erro:: ").error().detail(
					"Ocorreu um erro ao tentar salvar um produto. TENTE DE NOVO. Se o erro persistir contacte o Administrador do Sistema UBI")
					.add();
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent event) {
		produto = (Produto) event.getComponent().getAttributes().get("produtoSelecionado");
		try {

			historico = new Historico();
			historico.setTipo("Produtos");
			historico.setData(new Date());
			historico.setHistorico("O funcionario nr " + usuarioLogado.getFuncionario().getCodigo() + " Nome "
					+ usuarioLogado.getFuncionario().getPessoa().getNome() + " Apagou o " + " o produto "
					+ produto.getReferencia() + " quantidade armazem" + produto.getQuantidadeArmazenada() + " qtd loja "
					+ produto.getQuantidade() + " Data: " + historico.getData());

			produto.setEstado(false);
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.merge(produto);
			Messages.create("Sucesso:: ").detail("Produto exluido.").add();
			novo();
			listar();
		} catch (RuntimeException erro) {
			Messages.create("Erro:: ").error().detail(
					"Ocorreu um erro ao tentar excluir um produto. TENTE DE NOVO. Se o erro persistir contacte o Administrador do Sistema UBI")
					.add();
			erro.printStackTrace();
		}
	}

	public void carregarProduto(ActionEvent event) {
		painelListar = false;
		painelCadastro = true;

		produto = (Produto) event.getComponent().getAttributes().get("produtoSelecionado");
		CategoriaDAO categoriaDAO = new CategoriaDAO();
		categorias = categoriaDAO.listar();

		controlo = produto.getQuantidadeArmazenada();
		quantidadeLoja = produto.getQuantidade();
		historico = new Historico();
		historico.setData(new Date());
		historico.setTipo("Produtos");
		historico.setHistorico("O funcionario nr " + usuarioLogado.getFuncionario().getCodigo() + " Nome "
				+ usuarioLogado.getFuncionario().getPessoa().getNome() + " Actualizou o " + " o produto "
				+ produto.getReferencia() + " quantidade armazem" + produto.getQuantidadeArmazenada() + " qtd loja "
				+ produto.getQuantidade() + " Data: " + historico.getData());
	}

	public void actualizarBottleStore() {
		// controlo = -100;
		Integer q = (int) getQuantidadePorAdicionar();

		if (q.equals(null)) {
			produto.setQuantidadeArmazenada(controlo);
			produto.setQuantidade(quantidadeLoja);
		} else if (q < 0) {
			produto.setQuantidadeArmazenada(controlo);
			produto.setQuantidade(quantidadeLoja);
			setQuantidadePorAdicionar((short) 0);
		} else if (getQuantidadePorAdicionar() > controlo) {
			Messages.create("Aviso!").warn()
					.detail("Não ha quantidade suficiente no estoque armazenado, por favor, reduza a quantidade.")
					.add();
			produto.setQuantidadeArmazenada(controlo);
			produto.setQuantidade(quantidadeLoja);
		} else {
			produto.setQuantidade((short) (quantidadeLoja + getQuantidadePorAdicionar()));
			produto.setQuantidadeArmazenada((short) (controlo - getQuantidadePorAdicionar()));
		}
		historico.setHistorico("O funcionario nr " + usuarioLogado.getFuncionario().getCodigo() + " Nome "
				+ usuarioLogado.getFuncionario().getPessoa().getNome() + " actualizou a loja adicionando "
				+ " o produto " + produto.getReferencia() + " quantidade " + getQuantidadePorAdicionar() + " Data: "
				+ historico.getData());
	}

	public void limpar() {
		produto.setQuantidadeArmazenada(controlo);
		produto.setQuantidade(quantidadeLoja);
		setQuantidadePorAdicionar((short) 0);
		historico = new Historico();
	}

	public void calcularPrecoComIva() {

		System.out.println("prec " + produto.getPreco());
		if (produto.getPreco().equals(null) || produto.getPreco() == new BigDecimal(0)) {
			produto.setPrecoIva(new BigDecimal(0));
		} else {
			BigDecimal Iva = produto.getPreco().multiply(new BigDecimal(0.17));
			produto.setPrecoIva(produto.getPreco().add(Iva));

			System.out
					.println("Preco Total " + produto.getPrecoIva() + " sem iva " + produto.getPreco() + " iva " + Iva);
		}

	}

	public void calcularPrecoSemIva() {
		System.out.println("prec " + produto.getPrecoIva());
		if (produto.getPrecoIva().equals(null) || produto.getPrecoIva() == new BigDecimal(0)) {
			produto.setPreco(new BigDecimal(0));
		} else {
			produto.setPreco(produto.getPrecoIva().divide(new BigDecimal(1.17), 2, RoundingMode.HALF_EVEN));

			System.out.println("Preco Total " + produto.getPrecoIva() + " sem iva " + produto.getPreco());
		}
	}

}
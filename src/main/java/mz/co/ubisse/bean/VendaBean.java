package mz.co.ubisse.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import mz.co.ubisse.converter.ConverterData;
import mz.co.ubisse.converter.ConverterExtenso;
import mz.co.ubisse.dao.ClienteDAO;
import mz.co.ubisse.dao.CotacaoDAO;
import mz.co.ubisse.dao.FacturaDAO;
import mz.co.ubisse.dao.ProdutoDAO;
import mz.co.ubisse.dao.VendaDAO;
import mz.co.ubisse.dao.VendaDinheiroDAO;
import mz.co.ubisse.domain.Cliente;
import mz.co.ubisse.domain.Cotacao;
import mz.co.ubisse.domain.Empresa;
import mz.co.ubisse.domain.Factura;
import mz.co.ubisse.domain.Historico;
import mz.co.ubisse.domain.ItemsVenda;
import mz.co.ubisse.domain.Pessoa;
import mz.co.ubisse.domain.Produto;
import mz.co.ubisse.domain.Usuario;
import mz.co.ubisse.domain.Venda;
import mz.co.ubisse.domain.VendaDinheiro;
import mz.co.ubisse.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VendaBean implements Serializable {
	private List<Produto> produtos;
	private List<ItemsVenda> itemsVendas;
	private List<Cliente> clientes;
	private List<Venda> vendas;
	private Produto produto;
	private Empresa empresaReselva;
	private Venda venda;
	private ItemsVenda itemsVenda;
	private Usuario usuario;
	private Pessoa pessoa;
	private Cliente cliente;
	private VendaDinheiro vendaDinheiro;
	private Factura factura;
	private Cotacao cotacao;
	private Short quantidade;
	private BigDecimal reservaSubtotal;
	private BigDecimal preco;
	private String tipoVenda;
	private Date dataControlo;
	private Integer ultimoCodigo;
	private Long codigoVenda;
	private Long codigoDocumento;

	private Boolean item;
	private Boolean finalizar;
	private Boolean botaoFinal;
	private Boolean botaoImprimir;
	private Boolean botaoCliente;
	private Boolean painelServico;
	private Boolean painelProduto;
	private Boolean botaoModoFuncionamento;
	private Boolean controloModoFuncionamento;
	private Boolean regimeIva;
	private Boolean inputCheck;
	private Boolean botaoSalvar;
	private Boolean painelFinalizar;
	private Boolean tipoDePagamento;
	private Boolean dialogoImprimir;

	private String modofuncionamento;
	private String isencao;
	private Long chave;
	private String moradaCompleta;
	private String contacto;
	private String numero;
	private String observacao;

	private boolean msg;

	private Historico historico;

	private AutenticacaoBean autenticacaoBean;

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<ItemsVenda> getItemsVendas() {
		return itemsVendas;
	}

	public void setItemsVendas(List<ItemsVenda> itemsVendas) {
		this.itemsVendas = itemsVendas;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Venda getVenda() {

		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public ItemsVenda getItemsVenda() {
		return itemsVenda;
	}

	public void setItemsVenda(ItemsVenda itemsVenda) {
		this.itemsVenda = itemsVenda;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Short getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Short quantidade) {
		this.quantidade = quantidade;
	}

	public String getTipoVenda() {
		return tipoVenda;
	}

	public void setTipoVenda(String tipoVenda) {
		this.tipoVenda = tipoVenda;
	}

	public Date getDataControlo() {
		return dataControlo;
	}

	public void setDataControlo(Date dataControlo) {
		this.dataControlo = dataControlo;
	}

	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Boolean getItem() {
		return item;
	}

	public void setItem(Boolean item) {
		this.item = item;
	}

	public Integer getUltimoCodigo() {
		return ultimoCodigo;
	}

	public void setUltimoCodigo(Integer ultimoCodigo) {
		this.ultimoCodigo = ultimoCodigo;
	}

	public Boolean getFinalizar() {
		return finalizar;
	}

	public void setFinalizar(Boolean finalizar) {
		this.finalizar = finalizar;
	}

	public Boolean getBotaoFinal() {
		return botaoFinal;
	}

	public void setBotaoFinal(Boolean botaoFinal) {
		this.botaoFinal = botaoFinal;
	}

	public Boolean getBotaoImprimir() {
		return botaoImprimir;
	}

	public void setBotaoImprimir(Boolean botaoImprimir) {
		this.botaoImprimir = botaoImprimir;
	}

	public Boolean getBotaoCliente() {
		return botaoCliente;
	}

	public void setBotaoCliente(Boolean botaoCliente) {
		this.botaoCliente = botaoCliente;
	}

	public Boolean getPainelServico() {
		return painelServico;
	}

	public void setPainelServico(Boolean painelServico) {
		this.painelServico = painelServico;
	}

	public Boolean getPainelProduto() {
		return painelProduto;
	}

	public void setPainelProduto(Boolean painelProduto) {
		this.painelProduto = painelProduto;
	}

	public Boolean getBotaoModoFuncionamento() {
		return botaoModoFuncionamento;
	}

	public void setBotaoModoFuncionamento(Boolean botaoModoFuncionamento) {
		this.botaoModoFuncionamento = botaoModoFuncionamento;
	}

	public Boolean getControloModoFuncionamento() {
		return controloModoFuncionamento;
	}

	public void setControloModoFuncionamento(Boolean controloModoFuncionamento) {
		this.controloModoFuncionamento = controloModoFuncionamento;
	}

	public Boolean getInputCheck() {
		return inputCheck;
	}

	public void setInputCheck(Boolean inputCheck) {
		this.inputCheck = inputCheck;
	}

	public Boolean getBotaoSalvar() {
		return botaoSalvar;
	}

	public void setBotaoSalvar(Boolean botaoSalvar) {
		this.botaoSalvar = botaoSalvar;
	}

	public Boolean getPainelFinalizar() {
		return painelFinalizar;
	}

	public void setPainelFinalizar(Boolean painelFinalizar) {
		this.painelFinalizar = painelFinalizar;
	}

	public Boolean getTipoDePagamento() {
		return tipoDePagamento;
	}

	public void setTipoDePagamento(Boolean tipoDePagamento) {
		this.tipoDePagamento = tipoDePagamento;
	}

	public Boolean getDialogoImprimir() {
		return dialogoImprimir;
	}

	public void setDialogoImprimir(Boolean dialogoImprimir) {
		this.dialogoImprimir = dialogoImprimir;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public VendaBean() {
		autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		usuario = autenticacaoBean.getUsuarioLogado();

		empresaReselva = usuario.getFuncionario().getPessoa().getEmpresa();
		moradaCompleta = empresaReselva.getMorada() + " - " + empresaReselva.getCidade();
		isencao = empresaReselva.getIsencao();

		System.out.println("isemcao " + isencao);

		if (empresaReselva.getTelefone().isEmpty()) {
			System.out.println("Telefone nullo");
			if (!empresaReselva.getCelular().isEmpty() && !empresaReselva.getCelular2().isEmpty()
					&& !empresaReselva.getCelular3().isEmpty()) {
				contacto = "Cell: +258 " + empresaReselva.getCelular() + "/" + empresaReselva.getCelular2() + "/"
						+ empresaReselva.getCelular3();
			}

			else if (empresaReselva.getCelular().isEmpty() && !empresaReselva.getCelular2().isEmpty()
					&& !empresaReselva.getCelular3().isEmpty()) {
				contacto = "Cell: +258 " + empresaReselva.getCelular2() + "/" + empresaReselva.getCelular3();
			} else if (!empresaReselva.getCelular().isEmpty() && empresaReselva.getCelular2().isEmpty()
					&& !empresaReselva.getCelular3().isEmpty()) {
				contacto = "Cell: +258 " + empresaReselva.getCelular() + "/" + empresaReselva.getCelular3();
			} else if (!empresaReselva.getCelular().isEmpty() && !empresaReselva.getCelular2().isEmpty()
					&& empresaReselva.getCelular3().isEmpty()) {
				contacto = "Cell: +258 " + empresaReselva.getCelular() + "/" + empresaReselva.getCelular2();
			} else if (!empresaReselva.getCelular().isEmpty() && empresaReselva.getCelular2().isEmpty()
					&& empresaReselva.getCelular3().isEmpty()) {
				contacto = "Cell: +258 " + empresaReselva.getCelular();
			} else if (empresaReselva.getCelular().isEmpty() && !empresaReselva.getCelular2().isEmpty()
					&& empresaReselva.getCelular3().isEmpty()) {
				contacto = "Cell: +258 " + empresaReselva.getCelular2();
			} else if (empresaReselva.getCelular().isEmpty() && empresaReselva.getCelular2().isEmpty()
					&& !empresaReselva.getCelular3().isEmpty()) {
				contacto = "Cell: +258 " + empresaReselva.getCelular3();
			}
		} else {
			if (!empresaReselva.getCelular().isEmpty() && !empresaReselva.getCelular2().isEmpty()
					&& !empresaReselva.getCelular3().isEmpty()) {
				System.out.println("Aqui");
				contacto = "Tel: +258 " + empresaReselva.getTelefone() + " Cell: +258 " + empresaReselva.getCelular()
						+ "/" + empresaReselva.getCelular2() + "/" + empresaReselva.getCelular3();
				System.out.println("Aqui 2");
			} else if (empresaReselva.getCelular().isEmpty() && !empresaReselva.getCelular2().isEmpty()
					&& !empresaReselva.getCelular3().isEmpty()) {
				System.out.println("Aqui 3");
				contacto = "Tel: +258 " + empresaReselva.getTelefone() + " Cell: +258 " + empresaReselva.getCelular2()
						+ "/" + empresaReselva.getCelular3();
			} else if (!empresaReselva.getCelular().isEmpty() && empresaReselva.getCelular2().isEmpty()
					&& !empresaReselva.getCelular3().isEmpty()) {
				System.out.println("Aqui 4");
				contacto = "Tel: +258 " + empresaReselva.getTelefone() + "Cell: +258 " + empresaReselva.getCelular()
						+ "/" + empresaReselva.getCelular3();
			} else if (!empresaReselva.getCelular().isEmpty() && !empresaReselva.getCelular2().isEmpty()
					&& empresaReselva.getCelular3().isEmpty()) {
				System.out.println("Aqui 5");
				contacto = "Tel: +258 " + empresaReselva.getTelefone() + " Cell: +258 " + empresaReselva.getCelular()
						+ "/" + empresaReselva.getCelular2();
			} else if (!empresaReselva.getCelular().isEmpty() && empresaReselva.getCelular2().isEmpty()
					&& empresaReselva.getCelular3().isEmpty()) {
				System.out.println("Aqui 6");
				contacto = "Tel: +258 " + empresaReselva.getTelefone() + " Cell: +258 " + empresaReselva.getCelular();
			} else if (empresaReselva.getCelular().isEmpty() && !empresaReselva.getCelular2().isEmpty()
					&& empresaReselva.getCelular3().isEmpty()) {
				System.out.println("Aqui 7");
				contacto = "Tel: +258 " + empresaReselva.getTelefone() + " Cell: +258 " + empresaReselva.getCelular2();
			} else if (empresaReselva.getCelular().isEmpty() && empresaReselva.getCelular2().isEmpty()
					&& !empresaReselva.getCelular3().isEmpty()) {
				System.out.println("Aqui 8");
				contacto = "Tel: +258 " + empresaReselva.getTelefone() + " Cell: +258 " + empresaReselva.getCelular3();
			}
		}

		/**
		 * Modo de funcionamento de venda se 'e simplesPd(Produtos) simplesSer(Servicos)
		 * ou composto(servicos e produtos)
		 **/
		regimeIva = usuario.getFuncionario().getPessoa().getEmpresa().isIvaInclusoTotal();
		modofuncionamento = usuario.getFuncionario().getPessoa().getEmpresa().getModoFuncionamento();
		chave = usuario.getFuncionario().getPessoa().getEmpresa().getCodigo();
		System.out.println("modo " + modofuncionamento);
		historico = new Historico();
		historico.setData(new Date());

		if (modofuncionamento.equals("simplesSer")) {
			botaoModoFuncionamento = false;
			painelServico = true;
			painelProduto = false;
		} else if (modofuncionamento.equals("simplesPd")) {
			botaoModoFuncionamento = false;
			painelServico = false;
			painelProduto = true;

		} else if (modofuncionamento.equals("composto")) {
			System.out.println("ver modo de funcionamento" + controloModoFuncionamento);
			botaoModoFuncionamento = true;
			controloModoFuncionamento = true;
			painelServico = false;
			painelProduto = true;
		}
	}

	public void mudarModoFuncionamento() {
		if (controloModoFuncionamento.equals(true)) {
			// Modo Produto
			painelServico = false;
			painelProduto = true;
		} else if (controloModoFuncionamento.equals(false)) {
			// Modo Servico
			produto = new Produto();
			painelServico = true;
			painelProduto = false;
		}
	}

	@PostConstruct
	public void novo() {
		try {
			msg = false;
			item = true;
			finalizar = true;
			botaoCliente = true;
			botaoImprimir = true;
			botaoCliente = true;
			// botaoSalvar = true;

			itemsVendas = new ArrayList<>();

			produto = new Produto();
			cliente = new Cliente();
			pessoa = new Pessoa();
			venda = new Venda();

			venda.setValorSubtotal(new BigDecimal("0.00"));
			venda.setValorPagoIva(new BigDecimal("0.00"));
			venda.setValorTotal(new BigDecimal("0.00"));

			carregarCliente();

			if (modofuncionamento.equals("simplesPd") || modofuncionamento.equals("composto")) {
				System.out.println("listando os produtos");
				ProdutoDAO produtoDAO = new ProdutoDAO();
				if (!FacesContext.getCurrentInstance().isPostback()) {
					produtos = produtoDAO.listarActivos("nome", chave);
				}
			}
		} catch (RuntimeException erro) {
			Messages.create("Erro::").error().detail(
					"Ocorreu um erro ao tentar carregar a tela de vendas. Volte a tentar novamente ou contacte a equipe Técnica ")
					.add();
			erro.printStackTrace();
		}
	}

	public void adicionar(ActionEvent evento) {
		try {
			// Modo de Factura
			if (painelServico == true) {
				if (produto.getDescricao() == null || getPreco() == null) {
					Messages.create("AVISO::").warn().detail("Informe o Serviço e o preço por favor.").add();
				} else {
					itemsVenda = new ItemsVenda();
					if (regimeIva.equals(false)) {
						produto.setPreco(preco);
						itemsVenda.setValorUnitario(produto.getPreco());
						itemsVenda.setValorParcial(produto.getPreco());
					} else {
						produto.setPrecoIva(preco);
						itemsVenda.setValorUnitario(produto.getPrecoIva());
						itemsVenda.setValorParcial(produto.getPrecoIva());
					}
					produto.setChaveEmpresa(usuario.getFuncionario().getPessoa().getEmpresa().getCodigo());
					ProdutoDAO produtoDAO = new ProdutoDAO();
					Long c = empresaReselva.getCodigo();
					produto.setChaveEmpresa(c);

					System.out.println("chave " + empresaReselva.getCodigo());
					produto = produtoDAO.merge(produto);

					itemsVenda.setProduto(produto);
					itemsVendas.add(itemsVenda);

					calcularAdicaoProduto();
					produto = new Produto();
				}

			} else if (painelProduto == true) {
				quantidade = getQuantidade();

				int achou = -1;
				for (int i = 0; i < itemsVendas.size(); i++) {
					if (itemsVendas.get(i).getProduto().equals(produto)) {
						achou = i;
					}
				}
				if (achou < 0) {

					itemsVenda = new ItemsVenda();
					itemsVenda.setProduto(produto);
					itemsVenda.setQuantidade(quantidade);

					if (regimeIva.equals(false)) {
						itemsVenda.setValorUnitario(produto.getPreco());
						itemsVenda.setValorParcial(produto.getPreco().multiply(new BigDecimal(quantidade)));
					} else {
						itemsVenda.setValorUnitario(produto.getPrecoIva());
						itemsVenda.setValorParcial(produto.getPrecoIva().multiply(new BigDecimal(quantidade)));
					}
					itemsVendas.add(itemsVenda);
					if (itemsVenda.getQuantidade() > produto.getQuantidade()) {
						Messages.create("Aviso").warn()
								.detail("A quantidade adicionada é maior que a quantidade disponível "
										+ "no estoque, diminua a quantidade do produto " + produto.getDescricao()
										+ " porque não será| possível efectuar "
										+ "a venda, salvo se for uma Guia De Entrega ou Cotação.")
								.add();
					}

				} else {
					itemsVenda = new ItemsVenda();
					ItemsVenda itemsVenda = itemsVendas.get(achou);
					itemsVenda.setQuantidade(new Short(itemsVenda.getQuantidade() + quantidade + ""));
					if (regimeIva.equals(false)) {
						itemsVenda.setValorParcial(
								produto.getPreco().multiply(new BigDecimal(itemsVenda.getQuantidade())));
						itemsVenda.setValorUnitario(produto.getPreco());
					} else {
						itemsVenda.setValorParcial(
								produto.getPrecoIva().multiply(new BigDecimal(itemsVenda.getQuantidade())));
						itemsVenda.setValorUnitario(produto.getPrecoIva());
					}

					if (itemsVenda.getQuantidade() > produto.getQuantidade()) {
						Messages.create("Aviso").warn()
								.detail("A quantidade adicionada é maior que a quantidade disponível "
										+ "no estoque, diminua a quantidade do produto " + produto.getDescricao()
										+ " porque não será| possível efectuar "
										+ "a venda, salvo se for uma Guia De Entrega ou Cotação.")
								.add();
					}
				}
				calcularAdicaoProduto();
			}
			if (!itemsVendas.isEmpty()) {
				botaoCliente = false;
				finalizar = true;
			} else {
				finalizar = false;
				botaoCliente = true;
			}
		} catch (RuntimeException erro) {
			Messages.create("Erro::").error().detail(" Ocorreu um erro ao tentar adicionar o Servico.").add();
			erro.printStackTrace();
		}
	}

	public void remover(ActionEvent evento) {
		ItemsVenda itemsVenda = (ItemsVenda) evento.getComponent().getAttributes().get("itemSelecionado");
		System.out.println("item " + itemsVenda);
		int achou = -1;
		BigDecimal valor = null;
		BigDecimal parcial = null;
		if (regimeIva.equals(false)) {
			parcial = itemsVenda.getProduto().getPreco();
		} else {
			parcial = itemsVenda.getProduto().getPrecoIva();
		}
		for (int i = 0; i < itemsVendas.size(); i++) {
			if (itemsVendas.get(i).getProduto().equals(itemsVenda.getProduto())) {
				achou = i;
				System.out.println("achou " + i);
				if (painelServico == true) {
					itemsVendas.remove(achou);
				}
			}
		}
		if (achou > -1) {
			if (itemsVenda.getValorParcial().equals(new BigDecimal("0.00"))) {
				System.out.println("achou 2 " + achou);
				itemsVendas.remove(achou);
			} else {

				if (painelProduto == true) {
					quantidade = 1;
					valor = itemsVenda.getValorParcial().divide(new BigDecimal(itemsVenda.getQuantidade()));
					itemsVenda.setProduto(itemsVenda.getProduto());
					itemsVenda.setQuantidade(new Short(itemsVenda.getQuantidade() - quantidade + ""));
					itemsVenda.setValorParcial(valor.multiply(new BigDecimal(itemsVenda.getQuantidade())));
				}

				if (itemsVenda.getValorParcial().equals(new BigDecimal("0.00"))) {
					System.out.println("achou 3 " + achou);
					itemsVendas.remove(achou);
				}
			}
		}
		if (msg) {

		}
		if (regimeIva.equals(false)) {
			venda.setValorSubtotal(venda.getValorSubtotal().subtract(parcial));
		} else {
			venda.setValorTotal(venda.getValorTotal().subtract(parcial));
		}
		calcularRemoverProduto();
		if (itemsVendas.isEmpty()) {
			botaoCliente = true;
		}
	}

	public void carregarCliente() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listar(chave);
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar carregar os Clientes");
			erro.printStackTrace();
		}

	}

	// Metodos de calculos
	public void calcularAdicaoProduto() {

		if (regimeIva.equals(false)) {
			venda.setPercentagemDesconto(new Short("0"));
			if (isencao.equals("isento")) {
				venda.setValorTotal(produto.getPreco());
			} else {
				if (painelServico == true) {
					venda.setValorSubtotal(venda.getValorSubtotal().add(produto.getPreco()));
				} else {
					venda.setValorSubtotal(
							venda.getValorSubtotal().add(produto.getPreco().multiply(new BigDecimal(quantidade))));
				}
				venda.setValorPagoIva(venda.getValorSubtotal().multiply(new BigDecimal(0.17)));
				venda.setValorTotal(venda.getValorPagoIva().add(venda.getValorSubtotal()));

				reservaSubtotal = venda.getValorSubtotal();
			}
		} else {

			venda.setPercentagemDesconto(new Short("0"));
			if (isencao.equals("isento")) {
				venda.setValorTotal(produto.getPrecoIva());
			} else {
				if (painelServico == true) {
					venda.setValorTotal(venda.getValorTotal().add(produto.getPrecoIva()));
				} else {
					venda.setValorTotal(
							venda.getValorTotal().add(produto.getPrecoIva().multiply(new BigDecimal(quantidade))));
				}
				venda.setValorSubtotal(venda.getValorTotal().divide(new BigDecimal(1.17), 2, RoundingMode.HALF_EVEN));
				venda.setValorPagoIva(venda.getValorTotal().subtract(venda.getValorSubtotal()));

				reservaSubtotal = venda.getValorSubtotal();
			}
		}

	}

	public void calcularRemoverProduto() {
		if (regimeIva.equals(false)) {
			venda.setPercentagemDesconto(new Short("0"));
			venda.setValorPagoIva(venda.getValorSubtotal().multiply(new BigDecimal(0.17)));
			venda.setValorTotal(venda.getValorPagoIva().add(venda.getValorSubtotal()));
			reservaSubtotal = venda.getValorSubtotal();
		} else {
			venda.setPercentagemDesconto(new Short("0"));

			venda.setValorSubtotal(venda.getValorTotal().divide(new BigDecimal(1.17), 2, RoundingMode.HALF_EVEN));
			venda.setValorPagoIva(venda.getValorTotal().subtract(venda.getValorSubtotal()));
			reservaSubtotal = venda.getValorSubtotal();
		}
	}

	public void calcularDesconto() {
		if (venda.getPercentagemDesconto().equals(null)) {
			venda.setPercentagemDesconto(new Short("0"));
		} else {
			double percentagem = venda.getPercentagemDesconto().doubleValue() / 100;
			System.out.println("Valor Subtotal :" + venda.getValorSubtotal().doubleValue());
			double valorPercentagem = reservaSubtotal.doubleValue() * percentagem;
			System.out.println("Percentagem " + percentagem);
			System.out.println("Valor em Percentagem " + valorPercentagem);
			venda.setValorSubtotal(new BigDecimal(reservaSubtotal.doubleValue() - valorPercentagem));
			venda.setValorPagoIva(venda.getValorSubtotal().multiply(new BigDecimal(0.17)));
			venda.setValorTotal(venda.getValorPagoIva().add(venda.getValorSubtotal()));
		}
	}

	public void calcularTroco() {

		if (venda.getValorPago() == null || itemsVendas.isEmpty()) {
			venda.setValorTroco(new BigDecimal(0));
		} else {
			venda.setValorTroco(
					new BigDecimal(venda.getValorPago().doubleValue() - venda.getValorTotal().doubleValue()));

		}
	}

	public void mostrarNasTex() {
		try {

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produto = produtoDAO.buscarPorProduto(produto.getCodigo());
			if (regimeIva.equals(false)) {
				preco = produto.getPreco();
			} else {
				preco = produto.getPrecoIva();
			}
			setQuantidade(new Short("1"));

		} catch (RuntimeException erro) {
			Messages.create("Erro Fatal::").error().detail("Ocorreu um erro ao gerar uma selecionar um Produto.").add();
			erro.printStackTrace();
		}
	}

	public void mostrarFinalizar() {

		System.out.println("tipo " + venda.getTipoPagamento());
		if (venda.getTipoPagamento().equals("Check")) {
			inputCheck = false;
			System.out.println(inputCheck);
		} else {
			inputCheck = true;
			System.out.println(inputCheck);
		}

	}

	public void finalizar() {
		venda.setTipoPagamento("Vista");
		if (venda.getValorTotal().signum() == 0) {
			Messages.create("Aviso! ").warn().detail("Informe um item para a venda.").add();
			painelFinalizar = false;
		}
		if (tipoVenda == "") {
			Messages.create("Aviso! ").warn().detail("Informe o Tipo de Venda por favor. Ex: Factura.").add();
			painelFinalizar = false;
		} else if (tipoVenda.equals("VD")) {
			tipoDePagamento = true;
			inputCheck = true;
		} else {
			inputCheck = true;
			tipoDePagamento = false;
		}
		if (venda.getCliente() == null) {
			painelFinalizar = false;
			System.out.println("cliente " + venda.getCliente());
			Messages.create("Aviso! ").warn().detail("Informe o cliente por favor.").add();
		}
		if (venda.getValorTotal().signum() != 0 && tipoVenda != "" && venda.getCliente() != null) {
			painelFinalizar = true;
		}

	}

	// Metodos Salvar
	@SuppressWarnings("static-access")
	public void salvar() {
		if (empresaReselva.getActivacaoFacturacao().isEmpty()) {
			observacao = ". Documento emitido para fins de Formação. Não tem validade fiscal.";
		}
		try {
			if (venda.getTipoPagamento().equals(null) || venda.getTipoPagamento().isEmpty()) {
				Messages.create("Aviso! ").warn().detail("Informe o tipo de Pagamento.").add();
				dialogoImprimir = false;
			}
			if (venda.getTipoPagamento().equals("Check") && venda.getDetalheTipoPagamento().isEmpty()) {
				Messages.create("Aviso! ").warn().detail("Informe o numero do check por favor").add();
				dialogoImprimir = false;
			} else {
				dialogoImprimir = true;
				VendaDAO vendaDAO = new VendaDAO();

				msg = false;
				dataControlo = new Date();
				botaoFinal = true;

				historico.setHistorico("O Funcionario " + usuario.getFuncionario().getCodigo());
				ConverterExtenso converterExtenso = new ConverterExtenso();
				venda.setValorExtenso(converterExtenso.valorPorExtenso(venda.getValorTotal().doubleValue()));
				venda.setHorario(dataControlo);
				venda.setFuncionario(usuario.getFuncionario());

				if (venda.getCliente() == null) {
					System.out.println("cliente " + venda.getCliente());
					Messages.create("Aviso! ").warn().detail("Informe o cliente por favor.").add();
				} else {

					Integer codigoSemLetras;

					if (getTipoVenda().equals("VD")) {

						VendaDinheiroDAO dinheiroDAO = new VendaDinheiroDAO();
						vendaDinheiro = dinheiroDAO.buscarPorUltimo(empresaReselva.getCodigo());
						vendaDinheiro.setObseracoes("");
						if (vendaDinheiro == null) {
							vendaDinheiro = new VendaDinheiro();
							vendaDinheiro.setCodigoVD("00000" + 1 + empresaReselva.getLetraCodigo());
						} else {
							numero = vendaDinheiro.getCodigoVD();
							codigoSemLetras = Integer.parseInt(numero.replaceAll("[^0-9]*", ""));

							if (codigoSemLetras.toString().length() == 1) {
								vendaDinheiro
										.setCodigoVD("00000" + (codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							} else if (codigoSemLetras.toString().length() == 2) {
								vendaDinheiro
										.setCodigoVD("0000" + (codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							} else if (codigoSemLetras.toString().length() == 3) {
								vendaDinheiro
										.setCodigoVD("000" + (codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							} else if (codigoSemLetras.toString().length() == 4) {
								vendaDinheiro
										.setCodigoVD("00" + (codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							} else if (codigoSemLetras.toString().length() == 5) {
								vendaDinheiro
										.setCodigoVD("0" + (codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							} else if (codigoSemLetras.toString().length() >= 6) {
								vendaDinheiro.setCodigoVD((codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							}
						}
						if (empresaReselva.getActivacaoFacturacao().isEmpty()) {
							vendaDinheiro.setObseracoes(observacao);
						}

						historico.setTipo("vd");
						vendaDinheiro.setEstado("Pago");
						vendaDinheiro.setChaveEmpresa(empresaReselva.getCodigo());

						vendaDinheiro = vendaDAO.save(venda, itemsVendas, vendaDinheiro, historico);
						venda = vendaDinheiro.getVenda();
						numero = vendaDinheiro.getCodigoVD();
						codigoSemLetras = Integer.parseInt(numero.replaceAll("[^0-9]*", ""));
						codigoDocumento = codigoSemLetras.longValue();
						Messages.create("Confirmação").detail("Venda Realizada com sucesso.").add();

					} else if (getTipoVenda().equals("Factura")) {
						factura = new Factura();
						FacturaDAO facturaDAO = new FacturaDAO();
						factura = facturaDAO.buscarPorUltimo(empresaReselva.getCodigo());
						factura.setObseracoes("");
						if (factura == null) {
							factura = new Factura();
							factura.setCodigoFT("00000" + 1 + empresaReselva.getLetraCodigo());
						} else {
							numero = factura.getCodigoFT();
							codigoSemLetras = Integer.parseInt(numero.replaceAll("[^0-9]*", ""));

							if (codigoSemLetras.toString().length() == 1) {
								factura.setCodigoFT("00000" + (codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							} else if (codigoSemLetras.toString().length() == 2) {
								factura.setCodigoFT("0000" + (codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							} else if (codigoSemLetras.toString().length() == 3) {
								factura.setCodigoFT("000" + (codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							} else if (codigoSemLetras.toString().length() == 4) {
								factura.setCodigoFT("00" + (codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							} else if (codigoSemLetras.toString().length() == 5) {
								factura.setCodigoFT("0" + (codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							} else if (codigoSemLetras.toString().length() >= 6) {
								factura.setCodigoFT((codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							}
						}
						if (empresaReselva.getActivacaoFacturacao().isEmpty()) {
							factura.setObseracoes(observacao);
						}

						historico.setTipo("Factura");
						ConverterData converterData = new ConverterData();
						Calendar cal = Calendar.getInstance();
						factura.setEstado("Não Pago");
						factura.setDataValidade(converterData.diasUteis(cal));
						factura.setDataFacturacao(LocalDate.now());
						factura.setSaldo(venda.getValorTotal());
						factura.setChaveEmpresa(empresaReselva.getCodigo());
						factura = vendaDAO.saveFactura(venda, itemsVendas, factura, historico);
						venda = factura.getVenda();
						numero = factura.getCodigoFT();
						codigoSemLetras = Integer.parseInt(numero.replaceAll("[^0-9]*", ""));
						codigoDocumento = codigoSemLetras.longValue();
						Messages.create("Confirmacao").detail("Facturado sucesso.").add();
					} else if (getTipoVenda().equals("Cotacao")) {
						cotacao = new Cotacao();
						CotacaoDAO cotacaoDAO = new CotacaoDAO();
						cotacao = cotacaoDAO.buscarPorUltimo(empresaReselva.getCodigo());

						if (cotacao.getCodigo() == null) {
							cotacao = new Cotacao();
							cotacao.setCodigoCT("00000" + 1 + empresaReselva.getLetraCodigo());
						} else {

							numero = cotacao.getCodigoCT();
							codigoSemLetras = Integer.parseInt(numero.replaceAll("[^0-9]*", ""));

							if (codigoSemLetras.toString().length() == 1) {
								cotacao.setCodigoCT("00000" + (codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							} else if (codigoSemLetras.toString().length() == 2) {
								cotacao.setCodigoCT("0000" + (codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							} else if (codigoSemLetras.toString().length() == 3) {
								cotacao.setCodigoCT("000" + (codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							} else if (codigoSemLetras.toString().length() == 4) {
								cotacao.setCodigoCT("00" + (codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							} else if (codigoSemLetras.toString().length() == 5) {
								cotacao.setCodigoCT("0" + (codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							} else if (codigoSemLetras.toString().length() >= 6) {
								cotacao.setCodigoCT((codigoSemLetras + 1) + empresaReselva.getLetraCodigo());
							}
						}

						cotacao.setChaveEmpresa(empresaReselva.getCodigo());
						cotacao.setData(LocalDate.now());
						historico.setTipo("Cotacao");
						cotacao.setEstado("Em aberto");
						cotacao = vendaDAO.saveCotacao(venda, itemsVendas, cotacao, historico);
						venda = cotacao.getVenda();
						numero = cotacao.getCodigoCT();
						codigoSemLetras = Integer.parseInt(numero.replaceAll("[^0-9]*", ""));
						codigoDocumento = codigoSemLetras.longValue();
						Messages.create("Confirmação").detail("Cotação Realizada com sucesso.").add();
					}
					botaoFinal = false;
					botaoImprimir = false;
					codigoVenda = venda.getCodigo();
					itemsVendas = new ArrayList<>();
					// novo();
				}
			}
		} catch (Exception erro) {
			VendaDAO vendaDAO = new VendaDAO();
			if (vendaDAO.mensagem == null) {
				System.out.println("mensagem " + vendaDAO.mensagem);
				botaoFinal = true;
				botaoImprimir = true;
				if (getTipoVenda().equals("VD")) {
					Messages.create("Erro Fatal !").fatal()
							.detail("Ocorreu um erro ao tentar salvar a VD. Tente novamente.").add();
				} else if (getTipoVenda().equals("Factura")) {
					Messages.create("Erro Fatal !").fatal()
							.detail("Ocorreu um erro ao tentar emitir a Factura. Tente novamente.").add();
				} else if (getTipoVenda().equals("Cotacao")) {
					Messages.create("Erro Fatal !").fatal()
							.detail("Ocorreu um erro ao tentar emitir a Cotacao. Tente novamente.").add();
				}
			} else {
				Messages.create("Erro Fatal !").fatal().detail(vendaDAO.mensagem).add();
				msg = true;
			}
		}
	}

	public void redicionar() throws IOException {
		Faces.redirect("./pages/venda/Venda.xhtml");
	}

	@SuppressWarnings("unused")
	public void imprimir() throws IOException {

		if (msg == false) {
			try {
				String caminho = null;
				String codigo = null;
				if (tipoVenda.equals("VD")) {
					codigo = vendaDinheiro.getCodigoVD();
					caminho = FacesContext.getCurrentInstance().getExternalContext()
							.getRealPath("/report/VDPadrao.jasper");
				} else if (tipoVenda.equals("Factura")) {
					codigo = factura.getCodigoFT();
					caminho = FacesContext.getCurrentInstance().getExternalContext()
							.getRealPath("/report/FacturaPadrao.jasper");

				} else if (tipoVenda.equals("Cotacao")) {
					codigo = cotacao.getCodigoCT();
					caminho = FacesContext.getCurrentInstance().getExternalContext()
							.getRealPath("/report/CTPadrao.jasper");
				} else {
					System.out.println("vazio");
				}

				Map<String, Object> parametros = new HashMap<>();
				parametros.put("REPORT_LOCALE", new Locale("pt", "MZN"));
				parametros.put("CODIGO_VENDA", codigoVenda);
				parametros.put("ID", numero);
				parametros.put("Logo", autenticacaoBean.getCaminhoLogo());
				parametros.put("MORADA", moradaCompleta);
				parametros.put("CONTACTO", contacto);

				Connection conexao = HibernateUtil.getConexao();

				JasperPrint jasperPrint;

				jasperPrint = JasperFillManager.fillReport(caminho, parametros, conexao);
				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
						.getExternalContext().getResponse();
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition",
						"attachment; filename=\"" + tipoVenda + numero + "Original.pdf" + "\"");
				ServletOutputStream stream = response.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

				FacesContext.getCurrentInstance().responseComplete();
			} catch (JRException e) {
				e.printStackTrace();
				Messages.create("Erro Fatal !").fatal().detail("erro na impressao. " + e).add();
			}

		} else {
			Messages.addFlashGlobalError("Não é  possível imprimir");
			System.out.println("A mensagem e :" + msg);
		}

	}

	@SuppressWarnings("unused")
	public void imprimir2() throws IOException {

		if (msg == false) {
			try {
				String caminho = null;
				String codigo = null;
				if (tipoVenda.equals("VD")) {
					codigo = vendaDinheiro.getCodigoVD();
					caminho = FacesContext.getCurrentInstance().getExternalContext()
							.getRealPath("/report/VDPadraoVerso.jasper");
				} else if (tipoVenda.equals("Factura")) {
					codigo = factura.getCodigoFT();
					caminho = FacesContext.getCurrentInstance().getExternalContext()
							.getRealPath("/report/FacturaPadraoVerso.jasper");

				} else if (tipoVenda.equals("Cotacao")) {
					codigo = cotacao.getCodigoCT();
					caminho = FacesContext.getCurrentInstance().getExternalContext()
							.getRealPath("/report/CTPadraoVerso.jasper");
				} else {
					System.out.println("vazio");
				}

				Map<String, Object> parametros = new HashMap<>();
				parametros.put("REPORT_LOCALE", new Locale("pt", "MZN"));
				parametros.put("CODIGO_VENDA", codigoVenda);
				parametros.put("ID", numero);
				parametros.put("Logo", autenticacaoBean.getCaminhoLogo());
				parametros.put("MORADA", moradaCompleta);
				parametros.put("CONTACTO", contacto);

				Connection conexao = HibernateUtil.getConexao();

				JasperPrint jasperPrint;

				jasperPrint = JasperFillManager.fillReport(caminho, parametros, conexao);
				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
						.getExternalContext().getResponse();
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition",
						"attachment; filename=\"" + tipoVenda + numero + "Verso.pdf" + "\"");
				ServletOutputStream stream = response.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

				FacesContext.getCurrentInstance().responseComplete();
			} catch (JRException e) {
				e.printStackTrace();
				Messages.create("Erro Fatal !").fatal().detail("erro na impressao. " + e).add();
			}

		} else {
			Messages.addFlashGlobalError("Não é  possível imprimir");
			System.out.println("A mensagem e :" + msg);
		}
	}

	public void salvarCliente() {
		try {
			item = false;
			pessoa.setEmpresa(usuario.getFuncionario().getPessoa().getEmpresa());
			ClienteDAO clienteDAO = new ClienteDAO();
			cliente.setEstado(true);
			cliente.setDataCadastro(new Date());
			Cliente cli = clienteDAO.salvarClienteVenda(cliente, pessoa);

			cliente = clienteDAO.buscar(cli.getCodigo());
			venda.setCliente(cliente);
			Messages.create("Sucesso").detail("Cliente Inserido.").add();
		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar um novo Cliente");
			e.printStackTrace();
		}
	}
}
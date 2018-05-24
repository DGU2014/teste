package mz.co.ubisse.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import mz.co.ubisse.dao.RequisicaoDAO;
import mz.co.ubisse.dao.VendaDAO;
import mz.co.ubisse.domain.RequisicaoProduto;
import mz.co.ubisse.domain.Venda;
import mz.co.ubisse.domain.VendaDinheiro;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RelatorioBean implements Serializable {

	private VendaDinheiro vendaDinheiro;
	private RequisicaoProduto requisicaoProduto;
	private List<RequisicaoProduto> requisicaoProdutos;

	private List<VendaDinheiro> dinheiros;

	private Date dataInicio;
	private Date dataFim;

	private Long codigo;
	private Integer pesquisa;

	private BigDecimal valorTotal;
	private BigDecimal valorTotalEntradas;
	private BigDecimal valorTotalLucro;

	public VendaDinheiro getVendaDinheiro() {
		return vendaDinheiro;
	}

	public void setVendaDinheiro(VendaDinheiro vendaDinheiro) {
		this.vendaDinheiro = vendaDinheiro;
	}

	public List<VendaDinheiro> getDinheiros() {
		return dinheiros;
	}

	public void setDinheiros(List<VendaDinheiro> dinheiros) {
		this.dinheiros = dinheiros;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Integer getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(Integer pesquisa) {
		this.pesquisa = pesquisa;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	
	public BigDecimal getValorTotalEntradas() {
		return valorTotalEntradas;
	}

	public void setValorTotalEntradas(BigDecimal valorTotalEntradas) {
		this.valorTotalEntradas = valorTotalEntradas;
	}

	public BigDecimal getValorTotalLucro() {
		return valorTotalLucro;
	}

	public void setValorTotalLucro(BigDecimal valorTotalLucro) {
		this.valorTotalLucro = valorTotalLucro;
	}

	public RequisicaoProduto getRequisicaoProduto() {
		return requisicaoProduto;
	}

	public void setRequisicaoProduto(RequisicaoProduto requisicaoProduto) {
		this.requisicaoProduto = requisicaoProduto;
	}

	public List<RequisicaoProduto> getRequisicaoProdutos() {
		return requisicaoProdutos;
	}

	public void setRequisicaoProdutos(List<RequisicaoProduto> requisicaoProdutos) {
		this.requisicaoProdutos = requisicaoProdutos;
	}

	@PostConstruct
	public void inicializar() {
		vendaDinheiro = new VendaDinheiro();
		valorTotal = new BigDecimal("0.00");
		valorTotalEntradas = new BigDecimal("0.00");
		valorTotalLucro = new BigDecimal("0.00");
		requisicaoProduto = new RequisicaoProduto();

	}

	public void listar() {
		try {
			RequisicaoDAO requisicaoDAO = new RequisicaoDAO();
			valorTotal = new BigDecimal("0.00");
			System.out.println("entrando no segundo " + vendaDinheiro.getData() + " ate " + dataFim);
		//	dinheiros = vendaDinheiroDAO.ListaEntreDatas(dataInicio, dataFim);
			requisicaoProdutos = requisicaoDAO.ListaEntreDatas(dataInicio, dataFim);
			System.out.println("tamanho " + dinheiros.size());
			for (VendaDinheiro vendaDinheiro : dinheiros) {
				valorTotal = valorTotal.add(vendaDinheiro.getVenda().getValorTotal());
				System.out.println(valorTotal);
			}
			for (RequisicaoProduto requisicaoProduto : requisicaoProdutos) {
				valorTotalEntradas = valorTotalEntradas.add(requisicaoProduto.getValorTotal());
			}
			valorTotalLucro = valorTotal.subtract(valorTotalEntradas);
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as vendas Diarias");
			erro.printStackTrace();
		}

	}

	public void carregarVenda(ActionEvent event) {
		vendaDinheiro = (VendaDinheiro) event.getComponent().getAttributes().get("vendaSelecionada");

		System.out.println("venda carregada nr" + vendaDinheiro.getVenda().getCodigo());
		setCodigo(vendaDinheiro.getVenda().getCodigo());
		System.out.println("venda carregada nr 2 " + getCodigo());

	}

	public Venda getPersonByQuery() {
		Long c = getCodigo();
		VendaDAO vendaDAO = new VendaDAO();
		return vendaDAO.readAll(c);
	}

	public void carregarAquisicao(ActionEvent event) {

		requisicaoProduto = (RequisicaoProduto) event.getComponent().getAttributes().get("aquisicaoSelecionada");

		setCodigo(requisicaoProduto.getCodigo());
		System.out.println("venda carregada nr 2 " + getCodigo());

	}

/*	public RequisicaoProduto getByQuery() {
		System.out.println("venda carregada nr 3 " + getCodigo());
		//Long c = 1L;
	//	System.out.println(c);
		//RequisicaoDAO requisicaoDAO = new RequisicaoDAO();
		//return requisicaoDAO.readAll(c);
		// return null;
	}*/
}

package mz.co.ubisse.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.event.ToggleEvent;

import mz.co.ubisse.dao.RequisicaoDAO;
import mz.co.ubisse.domain.ItemsRequisicao;
import mz.co.ubisse.domain.RequisicaoProduto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RelatorioEntradaBean implements Serializable {

	private RequisicaoProduto requisicaoProduto;
	private List<RequisicaoProduto> requisicaoProdutos;
	private List<ItemsRequisicao> itemsRequisicaos;

	private Date dataUnico;
	private Date dataInicio;
	private Date dataFim;

	private Long codigo;
	private Integer pesquisa;

	private BigDecimal valorTotal;
	private BigDecimal valorTotalEntradas;
	private BigDecimal valorTotalLucro;

	private Boolean dataComponente;
	private Boolean dataComponenteLabel;
	private Boolean dataInicioComponente;
	private Boolean dataFimComponente;
	private Boolean calendario2;

	private Boolean tabelaItem;

	private List<Long> controlo;

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

	public Boolean getDataComponente() {
		return dataComponente;
	}

	public void setDataComponente(Boolean dataComponente) {
		this.dataComponente = dataComponente;
	}

	public Boolean getDataComponenteLabel() {
		return dataComponenteLabel;
	}

	public void setDataComponenteLabel(Boolean dataComponenteLabel) {
		this.dataComponenteLabel = dataComponenteLabel;
	}

	public Boolean getDataInicioComponente() {
		return dataInicioComponente;
	}

	public void setDataInicioComponente(Boolean dataInicioComponente) {
		this.dataInicioComponente = dataInicioComponente;
	}

	public Boolean getDataFimComponente() {
		return dataFimComponente;
	}

	public void setDataFimComponente(Boolean dataFimComponente) {
		this.dataFimComponente = dataFimComponente;
	}

	public Date getDataUnico() {
		return dataUnico;
	}

	public void setDataUnico(Date dataUnico) {
		this.dataUnico = dataUnico;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Boolean getCalendario2() {
		return calendario2;
	}

	public void setCalendario2(Boolean calendario2) {
		this.calendario2 = calendario2;
	}

	public List<ItemsRequisicao> getItemsRequisicaos() {
		return itemsRequisicaos;
	}

	public void setItemsRequisicaos(List<ItemsRequisicao> itemsRequisicaos) {
		this.itemsRequisicaos = itemsRequisicaos;
	}

	public Boolean getTabelaItem() {
		return tabelaItem;
	}

	public void setTabelaItem(Boolean tabelaItem) {
		this.tabelaItem = tabelaItem;
	}

	public List<Long> getControlo() {
		return controlo;
	}

	public void setControlo(List<Long> controlo) {
		this.controlo = controlo;
	}

	@PostConstruct
	public void inicializar() {
		tabelaItem = false;
		controlo = new ArrayList<>();
		valorTotal = new BigDecimal("0.00");
		valorTotalEntradas = new BigDecimal("0.00");
		requisicaoProduto = new RequisicaoProduto();
		calendario2 = true;
		dataComponente = false;
		dataComponenteLabel = false;
		dataFimComponente = false;
		dataInicioComponente = false;

	}

	public void adicionarComponentes() {
		valorTotalEntradas = new BigDecimal("0.00");
		if (pesquisa.equals(1)) {
			dataUnico = null;
			// listarComponente = true;
			requisicaoProdutos = new ArrayList<>();
			dataFim = null;
			dataInicio = null;
			dataComponente = true;
			dataFimComponente = false;
			dataInicioComponente = false;
			dataComponenteLabel = true;

		} else if (pesquisa.equals(2)) {
			dataUnico = null;
			// listarComponente = true;
			dataComponente = true;
			dataComponenteLabel = false;
			dataFimComponente = true;
			dataInicioComponente = true;
			requisicaoProdutos = new ArrayList<>();
			// vendaDinheiro = new VendaDinheiro();

		}

	}

	public void listar() {
		try {
			Instant instant = dataUnico.toInstant();
			LocalDate dt1 = instant.atZone(ZoneId.systemDefault()).toLocalDate();

			RequisicaoDAO requisicaoDAO = new RequisicaoDAO();
			if (pesquisa.equals(1)) {
				System.out.println("entrando no primeiro yy: " + dataUnico);
				valorTotalEntradas = new BigDecimal("0.00");
				requisicaoProdutos = requisicaoDAO.listarDiaValido(dt1);
				for (RequisicaoProduto requisicaoProduto : requisicaoProdutos) {
					valorTotalEntradas = valorTotalEntradas.add(requisicaoProduto.getValorTotal());
				}

				System.out.println("Valor Total e " + valorTotalEntradas);
			} else if (pesquisa.equals(2)) {

				calendario2 = false;
				System.out.println("calendario "+calendario2);
				Instant instant2;
				LocalDate dt2 = null;
				if (dataFim != null) {
					instant2 = dataFim.toInstant();
					dt2 = instant2.atZone(ZoneId.systemDefault()).toLocalDate();
				}

				if (dataUnico != null && dataFim != null) {
					valorTotalEntradas = new BigDecimal("0.00");
					requisicaoProdutos = requisicaoDAO.listarEntreDiaValido(dt1, dt2);
					for (RequisicaoProduto requisicaoProduto : requisicaoProdutos) {
						valorTotalEntradas = valorTotalEntradas.add(requisicaoProduto.getValorTotal());
					}
				}
			}
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as vendas Diarias");
			erro.printStackTrace();
		}
	}

	public void carregarItensEntrada(ToggleEvent event) {

		tabelaItem = false;
		System.out.println("Dentro");
		requisicaoProduto = (RequisicaoProduto) event.getData();
		setCodigo(requisicaoProduto.getCodigo());
		RequisicaoDAO requisicaoDAO = new RequisicaoDAO();

		if (controlo.contains(codigo)) {
			controlo.remove(codigo);
		} else {
			itemsRequisicaos = requisicaoDAO.carregarItem(codigo);
			controlo.add(codigo);
		}
	}

	public void carregar(ActionEvent event) {
		requisicaoProduto = (RequisicaoProduto) event.getComponent().getAttributes().get("aquisicaoSelecionada");
		setCodigo(requisicaoProduto.getCodigo());
		RequisicaoDAO requisicaoDAO = new RequisicaoDAO();
		itemsRequisicaos = requisicaoDAO.carregarItem(codigo);

		System.out.println("Lista carregada " + itemsRequisicaos);
	}

	/*
	 * public RequisicaoProduto getPersonByQuery() { Long c = getCodigo();
	 * RequisicaoDAO requisicaoDAO = new RequisicaoDAO(); return
	 * requisicaoDAO.readAll(c); }
	 */
	/*
	 * public void carregarVenda(ActionEvent event) { requisicaoProduto =
	 * (RequisicaoProduto)
	 * event.getComponent().getAttributes().get("aquisicaoSelecionada");
	 * 
	 * setCodigo(vendaDinheiro.getVenda().getCodigo());
	 * System.out.println("venda carregada nr 2 " + getCodigo());
	 * 
	 * }
	 * 
	 * public void carregarAquisicao(ActionEvent event) {
	 * 
	 * requisicaoProduto = (RequisicaoProduto)
	 * event.getComponent().getAttributes().get("aquisicaoSelecionada");
	 * 
	 * setCodigo(requisicaoProduto.getCodigo());
	 * System.out.println("requisicao carregada nr 3" + getCodigo());
	 * 
	 * }
	 * 
	 * public RequisicaoProduto getByQuery() { Long c = 1L; RequisicaoDAO
	 * requisicaoDAO = new RequisicaoDAO(); return requisicaoDAO.readAll(c); }
	 */
}

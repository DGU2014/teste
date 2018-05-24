package mz.co.ubisse.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javax.persistence.Convert;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.event.ToggleEvent;

import mz.co.ubisse.converter.ConverterData;
import mz.co.ubisse.converter.LocalDateConverter;
import mz.co.ubisse.dao.CotacaoDAO;
import mz.co.ubisse.dao.FacturaDAO;
import mz.co.ubisse.dao.VendaDAO;
import mz.co.ubisse.domain.Cotacao;
import mz.co.ubisse.domain.Empresa;
import mz.co.ubisse.domain.Factura;
import mz.co.ubisse.domain.Historico;
import mz.co.ubisse.domain.ItemsVenda;
import mz.co.ubisse.domain.Usuario;
import mz.co.ubisse.domain.Venda;
import mz.co.ubisse.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class CotacaoBean implements Serializable {

	private Cotacao cotacao;
	private Usuario usuario;
	private Empresa empresa;
	private Factura factura;
	private Historico historico;
	private List<Cotacao> cotacaos;
	private List<ItemsVenda> itemsVendas;

	private Date dataUnico;
	private Date dataInicio;
	private Date dataFim;

	private Long codigo;
	private Integer pesquisa;
	private Boolean dataComponente;
	private Boolean dataComponenteLabel;
	private Boolean dataInicioComponente;
	private Boolean dataFimComponente;
	private Boolean listarComponente;
	private Boolean calendario2;
	private Boolean tabelaItem;
	private String contacto;

	private List<Long> controlo;
	private BigDecimal valorTotal;

	private AutenticacaoBean autenticacaoBean;

	public List<ItemsVenda> getItemsVendas() {
		return itemsVendas;
	}

	public void setItemsVendas(List<ItemsVenda> itemsVendas) {
		this.itemsVendas = itemsVendas;
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

	public Boolean getDataComponente() {
		return dataComponente;
	}

	public void setDataComponente(Boolean dataComponente) {
		this.dataComponente = dataComponente;
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

	public Boolean getListarComponente() {
		return listarComponente;
	}

	public void setListarComponente(Boolean listarComponente) {
		this.listarComponente = listarComponente;
	}

	public Boolean getDataComponenteLabel() {
		return dataComponenteLabel;
	}

	public void setDataComponenteLabel(Boolean dataComponenteLabel) {
		this.dataComponenteLabel = dataComponenteLabel;
	}

	public Boolean getCalendario2() {
		return calendario2;
	}

	public void setCalendario2(Boolean calendario2) {
		this.calendario2 = calendario2;
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

	public Cotacao getCotacao() {
		return cotacao;
	}

	public void setCotacao(Cotacao cotacao) {
		this.cotacao = cotacao;
	}

	public List<Cotacao> getCotacaos() {
		return cotacaos;
	}

	public void setCotacaos(List<Cotacao> cotacaos) {
		this.cotacaos = cotacaos;
	}

	public CotacaoBean() {
		autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		usuario = autenticacaoBean.getUsuarioLogado();
		empresa = usuario.getFuncionario().getPessoa().getEmpresa();
	}

	@PostConstruct
	public void inicializar() {

		tabelaItem = false;
		controlo = new ArrayList<>();
		cotacao = new Cotacao();
		valorTotal = new BigDecimal("0.00");
		calendario2 = true;
		dataComponente = false;
		dataComponenteLabel = false;
		dataFimComponente = false;
		dataInicioComponente = false;
		listarComponente = false;

		if (empresa.getTelefone().isEmpty()) {
			if (!empresa.getCelular().isEmpty() && !empresa.getCelular2().isEmpty()
					&& !empresa.getCelular3().isEmpty()) {
				contacto = "Cell: +258 " + empresa.getCelular() + "/" + empresa.getCelular2() + "/"
						+ empresa.getCelular3();
			}

			else if (empresa.getCelular().isEmpty() && !empresa.getCelular2().isEmpty()
					&& !empresa.getCelular3().isEmpty()) {
				contacto = "Cell: +258 " + empresa.getCelular2() + "/" + empresa.getCelular3();
			} else if (!empresa.getCelular().isEmpty() && empresa.getCelular2().isEmpty()
					&& !empresa.getCelular3().isEmpty()) {
				contacto = "Cell: +258 " + empresa.getCelular() + "/" + empresa.getCelular3();
			} else if (!empresa.getCelular().isEmpty() && !empresa.getCelular2().isEmpty()
					&& empresa.getCelular3().isEmpty()) {
				contacto = "Cell: +258 " + empresa.getCelular() + "/" + empresa.getCelular2();
			} else if (!empresa.getCelular().isEmpty() && empresa.getCelular2().isEmpty()
					&& empresa.getCelular3().isEmpty()) {
				contacto = "Cell: +258 " + empresa.getCelular();
			} else if (empresa.getCelular().isEmpty() && !empresa.getCelular2().isEmpty()
					&& empresa.getCelular3().isEmpty()) {
				contacto = "Cell: +258 " + empresa.getCelular2();
			} else if (empresa.getCelular().isEmpty() && empresa.getCelular2().isEmpty()
					&& !empresa.getCelular3().isEmpty()) {
				contacto = "Cell: +258 " + empresa.getCelular3();
			}
		} else {
			if (!empresa.getCelular().isEmpty() && !empresa.getCelular2().isEmpty()
					&& !empresa.getCelular3().isEmpty()) {
				contacto = "Tel: +258 " + empresa.getTelefone() + " Cell: +258 " + empresa.getCelular() + "/"
						+ empresa.getCelular2() + "/" + empresa.getCelular3();
			} else if (empresa.getCelular().isEmpty() && !empresa.getCelular2().isEmpty()
					&& !empresa.getCelular3().isEmpty()) {
				contacto = "Tel: +258 " + empresa.getTelefone() + " Cell: +258 " + empresa.getCelular2() + "/"
						+ empresa.getCelular3();
			} else if (!empresa.getCelular().isEmpty() && empresa.getCelular2().isEmpty()
					&& !empresa.getCelular3().isEmpty()) {
				contacto = "Tel: +258 " + empresa.getTelefone() + "Cell: +258 " + empresa.getCelular() + "/"
						+ empresa.getCelular3();
			} else if (!empresa.getCelular().isEmpty() && !empresa.getCelular2().isEmpty()
					&& empresa.getCelular3().isEmpty()) {
				contacto = "Tel: +258 " + empresa.getTelefone() + " Cell: +258 " + empresa.getCelular() + "/"
						+ empresa.getCelular2();
			} else if (!empresa.getCelular().isEmpty() && empresa.getCelular2().isEmpty()
					&& empresa.getCelular3().isEmpty()) {
				contacto = "Tel: +258 " + empresa.getTelefone() + " Cell: +258 " + empresa.getCelular();
			} else if (empresa.getCelular().isEmpty() && !empresa.getCelular2().isEmpty()
					&& empresa.getCelular3().isEmpty()) {
				contacto = "Tel: +258 " + empresa.getTelefone() + " Cell: +258 " + empresa.getCelular2();
			} else if (empresa.getCelular().isEmpty() && empresa.getCelular2().isEmpty()
					&& !empresa.getCelular3().isEmpty()) {
				contacto = "Tel: +258 " + empresa.getTelefone() + " Cell: +258 " + empresa.getCelular3();
			}
		}
	}

	@Convert(converter = LocalDateConverter.class)
	public void listar() {

		Instant instant = dataUnico.toInstant();
		LocalDate dt1 = instant.atZone(ZoneId.systemDefault()).toLocalDate();

		try {
			CotacaoDAO cotacaoDAO = new CotacaoDAO();
			if (pesquisa.equals(1) && dataFim == null) {
				valorTotal = new BigDecimal("0.00");
				cotacaos = cotacaoDAO.listarDiaValido(dt1, empresa.getCodigo());

				for (Cotacao cotacao : cotacaos) {
					valorTotal = valorTotal.add(cotacao.getVenda().getValorTotal());
				}
			} else if (pesquisa.equals(2)) {

				calendario2 = false;
				Instant instant2;
				LocalDate dt2 = null;
				if (dataFim != null) {
					instant2 = dataFim.toInstant();
					dt2 = instant2.atZone(ZoneId.systemDefault()).toLocalDate();
				}

				if (dataUnico != null && dataFim != null) {
					valorTotal = new BigDecimal("0.00");
					cotacaos = cotacaoDAO.listarEntreDiaValido(dt1, dt2, empresa.getCodigo());
					for (Cotacao cotacao : cotacaos) {
						valorTotal = valorTotal.add(cotacao.getVenda().getValorTotal());
					}

				}
			}
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as vendas Diarias");
			erro.printStackTrace();
		}
	}

	public void carregarVendaDinheiro(ToggleEvent event) {

		tabelaItem = false;
		System.out.println("Dentro");
		cotacao = (Cotacao) event.getData();
		setCodigo(cotacao.getVenda().getCodigo());
		System.out.println("Item" + cotacao.getCodigo());

		VendaDAO vendaDAO = new VendaDAO();

		if (controlo.contains(codigo)) {
			controlo.remove(codigo);
		} else {
			itemsVendas = vendaDAO.carregarItem(codigo);
			controlo.add(codigo);
		}
	}

	public void carregarVenda(ActionEvent event) {
		cotacao = (Cotacao) event.getComponent().getAttributes().get("vendaSelecionada");
		setCodigo(cotacao.getVenda().getCodigo());
		VendaDAO vendaDAO = new VendaDAO();
		try {
			itemsVendas = vendaDAO.carregarItem(codigo);
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os items de venda");
			erro.printStackTrace();
		}
	}

	public void carregarCotacao(ActionEvent event) {
		System.out.println("entrando no carregamento");
		cotacao = (Cotacao) event.getComponent().getAttributes().get("vendaSelecionada");
		System.out.println("cotacao " + cotacao);
	}

	public void adicionarComponentes() {
		valorTotal = new BigDecimal("0.00");
		if (pesquisa.equals(1)) {
			dataUnico = null;
			dataFim = null;
			listarComponente = true;
			cotacaos = new ArrayList<>();
			dataComponente = true;
			dataFimComponente = false;
			dataInicioComponente = false;
			dataComponenteLabel = true;

		} else if (pesquisa.equals(2)) {
			dataUnico = null;
			dataFim = null;
			listarComponente = true;
			dataComponente = true;
			dataComponenteLabel = false;
			dataFimComponente = true;
			dataInicioComponente = true;
			cotacaos = new ArrayList<>();
			cotacao = new Cotacao();

		}

	}

	public void imprimirOriginal() throws IOException, JRException {
		try {

			String caminho = null;
			String moradaCompleta = empresa.getMorada() + " - " + empresa.getCidade();
			caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/report/CTPadrao.jasper");

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("REPORT_LOCALE", new Locale("pt", "MZN"));
			parametros.put("CODIGO_VENDA", cotacao.getVenda().getCodigo());
			parametros.put("ID", cotacao.getCodigoCT());
			parametros.put("Logo", autenticacaoBean.getCaminhoLogo());
			parametros.put("MORADA", moradaCompleta);
			parametros.put("CONTACTO", contacto);

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint jasperPrint = JasperFillManager.fillReport(caminho, parametros, conexao);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"attachment; filename=\"" + "Cotacao" + cotacao.getCodigoCT() + "Original.pdf" + "\"");
			ServletOutputStream stream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

			FacesContext.getCurrentInstance().responseComplete();

		} catch (JRException e) {
			Messages.addFlashGlobalError("Não é  possível imprimir" + e);
		}
	}

	public void imprimirVerso() throws IOException, JRException {
		try {

			String caminho = null;
			String moradaCompleta = empresa.getMorada() + " - " + empresa.getCidade();
			caminho = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/report/CTPadraoVerso.jasper");

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("REPORT_LOCALE", new Locale("pt", "MZN"));
			parametros.put("CODIGO_VENDA", cotacao.getVenda().getCodigo());
			parametros.put("ID", cotacao.getCodigoCT());
			parametros.put("Logo", autenticacaoBean.getCaminhoLogo());
			parametros.put("MORADA", moradaCompleta);
			parametros.put("CONTACTO", contacto);

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint jasperPrint = JasperFillManager.fillReport(caminho, parametros, conexao);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"attachment; filename=\"" + "Cotacao" + cotacao.getCodigoCT() + "Verso.pdf" + "\"");
			ServletOutputStream stream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

			FacesContext.getCurrentInstance().responseComplete();

		} catch (JRException e) {
			Messages.addFlashGlobalError("Não é  possível imprimir" + e);
		}
	}

	public void converterEmFactura() {
		historico = new Historico();
		FacturaDAO facturaDAO = new FacturaDAO();
		factura = facturaDAO.buscarPorUltimo(empresa.getCodigo());

		if (factura == null) {
			System.out.println("vazio");
			factura = new Factura();
			factura.setCodigoFT("00000" + 1 + empresa.getLetraCodigo());
			System.out.println("codigo da Factura 2" + factura.getCodigoFT());
		} else {
			System.out.println("n vazio");
			System.out.println("codigo da Factura 3" + factura.getCodigoFT());
			String numero = factura.getCodigoFT();
			Integer codigoSemLetras = Integer.parseInt(numero.replaceAll("[^0-9]*", ""));

			if (codigoSemLetras.toString().length() == 1) {
				factura.setCodigoFT("00000" + (codigoSemLetras + 1) + empresa.getLetraCodigo());
			} else if (codigoSemLetras.toString().length() == 2) {
				factura.setCodigoFT("0000" + (codigoSemLetras + 1) + empresa.getLetraCodigo());
			} else if (codigoSemLetras.toString().length() == 3) {
				factura.setCodigoFT("000" + (codigoSemLetras + 1) + empresa.getLetraCodigo());
			} else if (codigoSemLetras.toString().length() == 4) {
				factura.setCodigoFT("00" + (codigoSemLetras + 1) + empresa.getLetraCodigo());
			} else if (codigoSemLetras.toString().length() == 5) {
				factura.setCodigoFT("0" + (codigoSemLetras + 1) + empresa.getLetraCodigo());
			} else if (codigoSemLetras.toString().length() >= 6) {
				factura.setCodigoFT((codigoSemLetras + 1) + empresa.getLetraCodigo());
			}
		}
		historico.setTipo("CFactura");
		historico.setData(new Date());
		ConverterData converterData = new ConverterData();
		Calendar cal = Calendar.getInstance();
		factura.setEstado("Não Pago");
		factura.setDataValidade(converterData.diasUteis(cal));
		factura.setDataFacturacao(LocalDate.now());
		factura.setSaldo(cotacao.getVenda().getValorTotal());
		factura.setChaveEmpresa(empresa.getCodigo());
		factura.setChaveEmpresa(cotacao.getChaveEmpresa());
		factura.setVenda(cotacao.getVenda());

		cotacao.setEstado("Facturado");
		try {
			CotacaoDAO cotacaoDAO = new CotacaoDAO();
			cotacaoDAO.converterEmFactura(cotacao, factura, historico);
			Messages.create("Sucesso").detail("Facturado. Entre no menu factura Por Favor").add();
		} catch (RuntimeException erro) {
			Messages.create("Erro::").error().detail(
					"Erro durante a conversão. Tente de Novo. Se o Erro persistir contacte o Administrador do Sistema UBI")
					.add();
			erro.printStackTrace();
		}
	}

}

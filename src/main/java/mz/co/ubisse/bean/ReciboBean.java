package mz.co.ubisse.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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

import mz.co.ubisse.converter.LocalDateConverter;
import mz.co.ubisse.dao.ReciboDAO;
import mz.co.ubisse.domain.Empresa;
import mz.co.ubisse.domain.ItemsVenda;
import mz.co.ubisse.domain.Recibo;
import mz.co.ubisse.domain.Usuario;
import mz.co.ubisse.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ReciboBean implements Serializable {

	private Recibo recibo;
	private Usuario usuario;
	private Empresa empresa;
	private List<Recibo> recibos;
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

	private List<Long> controlo;
	private BigDecimal valorTotal;
	private String moradaCompleta;
	private String contacto;
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

	public Recibo getRecibo() {
		return recibo;
	}

	public void setRecibo(Recibo recibo) {
		this.recibo = recibo;
	}

	public List<Recibo> getRecibos() {
		return recibos;
	}

	public void setRecibos(List<Recibo> recibos) {
		this.recibos = recibos;
	}

	@PostConstruct
	public void inicializar() {
		tabelaItem = false;
		controlo = new ArrayList<>();
		recibo = new Recibo();
		valorTotal = new BigDecimal("0.00");
		calendario2 = true;
		dataComponente = false;
		dataComponenteLabel = false;
		dataFimComponente = false;
		dataInicioComponente = false;
		listarComponente = false;

		autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		usuario = autenticacaoBean.getUsuarioLogado();
		empresa = usuario.getFuncionario().getPessoa().getEmpresa();
		moradaCompleta = empresa.getMorada() + " - " + empresa.getCidade();

		if (empresa.getTelefone().isEmpty()) {
			System.out.println("Telefone nullo");
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
				System.out.println("Aqui");
				contacto = "Tel: +258 " + empresa.getTelefone() + " Cell: +258 " + empresa.getCelular() + "/"
						+ empresa.getCelular2() + "/" + empresa.getCelular3();
				System.out.println("Aqui 2");
			} else if (empresa.getCelular().isEmpty() && !empresa.getCelular2().isEmpty()
					&& !empresa.getCelular3().isEmpty()) {
				System.out.println("Aqui 3");
				contacto = "Tel: +258 " + empresa.getTelefone() + " Cell: +258 " + empresa.getCelular2() + "/"
						+ empresa.getCelular3();
			} else if (!empresa.getCelular().isEmpty() && empresa.getCelular2().isEmpty()
					&& !empresa.getCelular3().isEmpty()) {
				System.out.println("Aqui 4");
				contacto = "Tel: +258 " + empresa.getTelefone() + "Cell: +258 " + empresa.getCelular() + "/"
						+ empresa.getCelular3();
			} else if (!empresa.getCelular().isEmpty() && !empresa.getCelular2().isEmpty()
					&& empresa.getCelular3().isEmpty()) {
				System.out.println("Aqui 5");
				contacto = "Tel: +258 " + empresa.getTelefone() + " Cell: +258 " + empresa.getCelular() + "/"
						+ empresa.getCelular2();
			} else if (!empresa.getCelular().isEmpty() && empresa.getCelular2().isEmpty()
					&& empresa.getCelular3().isEmpty()) {
				System.out.println("Aqui 6");
				contacto = "Tel: +258 " + empresa.getTelefone() + " Cell: +258 " + empresa.getCelular();
			} else if (empresa.getCelular().isEmpty() && !empresa.getCelular2().isEmpty()
					&& empresa.getCelular3().isEmpty()) {
				System.out.println("Aqui 7");
				contacto = "Tel: +258 " + empresa.getTelefone() + " Cell: +258 " + empresa.getCelular2();
			} else if (empresa.getCelular().isEmpty() && empresa.getCelular2().isEmpty()
					&& !empresa.getCelular3().isEmpty()) {
				System.out.println("Aqui 8");
				contacto = "Tel: +258 " + empresa.getTelefone() + " Cell: +258 " + empresa.getCelular3();
			}
		}
	}

	@Convert(converter = LocalDateConverter.class)
	public void listar() {

		Instant instant = dataUnico.toInstant();
		LocalDate dt1 = instant.atZone(ZoneId.systemDefault()).toLocalDate();

		try {
			ReciboDAO reciboDAO = new ReciboDAO();
			if (pesquisa.equals(1) && dataFim == null) {
				System.out.println();
				valorTotal = new BigDecimal("0.00");
				recibos = reciboDAO.listarDiaValido(dt1, empresa.getCodigo());
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
					recibos = reciboDAO.listarEntreDiaValido(dt1, dt2, empresa.getCodigo());

				}
			}
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as vendas Diarias");
			erro.printStackTrace();
		}
	}

	public void adicionarComponentes() {
		valorTotal = new BigDecimal("0.00");
		if (pesquisa.equals(1)) {
			dataUnico = null;
			dataFim = null;
			listarComponente = true;
			recibos = new ArrayList<>();
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
			recibos = new ArrayList<>();
			recibo = new Recibo();

		}

	}

	public void imprimirOriginal() throws IOException {

		try {
			String caminho = null;

			if (recibo.getControlo() == 'c') {
				caminho = FacesContext.getCurrentInstance().getExternalContext()
						.getRealPath("/report/RPadraoCredito.jasper");
			} else if (recibo.getControlo() == 'd') {
				caminho = FacesContext.getCurrentInstance().getExternalContext()
						.getRealPath("/report/RPadraoDebido.jasper");
			} else if (recibo.getControlo() == 'f') {
				caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/report/RPadrao.jasper");
			}

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("REPORT_LOCALE", new Locale("pt", "MZN"));
			parametros.put("RECIBO", recibo.getCodigoRC());
			parametros.put("Logo", autenticacaoBean.getCaminhoLogo());
			parametros.put("MORADA", moradaCompleta);
			parametros.put("CONTACTO", contacto);

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint jasperPrint;

			jasperPrint = JasperFillManager.fillReport(caminho, parametros, conexao);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"attachment; filename=\"" + "Recibo" + recibo.getCodigoRC() + "Original.pdf" + "\"");
			ServletOutputStream stream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

			FacesContext.getCurrentInstance().responseComplete();
		} catch (JRException e) {
			e.printStackTrace();
			Messages.create("Erro Fatal !").fatal()
					.detail("erro na impressao. Se o erro persistir contacte o Administrador do Sistema UBI" + e).add();
		}

	}

	public void imprimirVerso() throws IOException {

		try {
			String caminho = null;

			if (recibo.getControlo() == 'c') {
				caminho = FacesContext.getCurrentInstance().getExternalContext()
						.getRealPath("/report/RPadraoCreditoVerso.jasper");
			} else if (recibo.getControlo() == 'd') {
				caminho = FacesContext.getCurrentInstance().getExternalContext()
						.getRealPath("/report/RPadraoDebidoVerso.jasper");
			} else if (recibo.getControlo() == 'f') {
				caminho = FacesContext.getCurrentInstance().getExternalContext()
						.getRealPath("/report/RPadraoVerso.jasper");
			}

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("REPORT_LOCALE", new Locale("pt", "MZN"));
			parametros.put("RECIBO", recibo.getCodigoRC());
			parametros.put("Logo", autenticacaoBean.getCaminhoLogo());
			parametros.put("MORADA", moradaCompleta);
			parametros.put("CONTACTO", contacto);

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint jasperPrint;

			jasperPrint = JasperFillManager.fillReport(caminho, parametros, conexao);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"attachment; filename=\"" + "Recibo" + recibo.getCodigoRC() + "Verso.pdf" + "\"");
			ServletOutputStream stream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

			FacesContext.getCurrentInstance().responseComplete();
		} catch (JRException e) {
			e.printStackTrace();
			Messages.create("Erro Fatal !").fatal()
					.detail("erro na impressao. Se o erro persistir contacte o Administrador do Sistema UBI" + e).add();
		}

	}

	// Imprimir
	public void carregarRecibo(ActionEvent event) {
		recibo = (Recibo) event.getComponent().getAttributes().get("vendaSelecionada");
	}

}

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
import org.primefaces.event.ToggleEvent;

import mz.co.ubisse.converter.LocalDateConverter;
import mz.co.ubisse.dao.GuiaDeRemessaDAO;
import mz.co.ubisse.dao.VendaDAO;
import mz.co.ubisse.dao.VendaDinheiroDAO;
import mz.co.ubisse.domain.Empresa;
import mz.co.ubisse.domain.GuiaDeRemessa;
import mz.co.ubisse.domain.ItemsVenda;
import mz.co.ubisse.domain.Usuario;
import mz.co.ubisse.domain.VendaDinheiro;
import mz.co.ubisse.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperRunManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VendaDinheiroBean implements Serializable {

	private VendaDinheiro vendaDinheiro;
	private List<VendaDinheiro> vendaDinheiros;
	private List<ItemsVenda> itemsVendas;
	private Usuario usuario;
	private Empresa empresa;
	private AutenticacaoBean autenticacaoBean;

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

	public VendaDinheiro getVendaDinheiro() {
		return vendaDinheiro;
	}

	public void setVendaDinheiro(VendaDinheiro vendaDinheiro) {
		this.vendaDinheiro = vendaDinheiro;
	}

	public List<VendaDinheiro> getDinheiros() {
		return vendaDinheiros;
	}

	public void setDinheiros(List<VendaDinheiro> dinheiros) {
		this.vendaDinheiros = dinheiros;
	}

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

	public List<VendaDinheiro> getVendaDinheiros() {
		return vendaDinheiros;
	}

	public void setVendaDinheiros(List<VendaDinheiro> vendaDinheiros) {
		this.vendaDinheiros = vendaDinheiros;
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

	public VendaDinheiroBean() {
		autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		usuario = autenticacaoBean.getUsuarioLogado();
		empresa = usuario.getFuncionario().getPessoa().getEmpresa();
	}

	@PostConstruct
	public void inicializar() {
		tabelaItem = false;
		controlo = new ArrayList<>();
		vendaDinheiro = new VendaDinheiro();
		valorTotal = new BigDecimal("0.00");
		calendario2 = true;
		dataComponente = false;
		dataComponenteLabel = false;
		dataFimComponente = false;
		dataInicioComponente = false;
		listarComponente = false;

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
			VendaDinheiroDAO vendaDinheiroDAO = new VendaDinheiroDAO();
			if (pesquisa.equals(1) && dataFim == null) {
				System.out.println("dia " + dt1);
				valorTotal = new BigDecimal("0.00");
				vendaDinheiros = vendaDinheiroDAO.listarDiaValido(dt1, empresa.getCodigo());

				for (VendaDinheiro vendaDinheiro : vendaDinheiros) {
					System.out.println(" dentro do for " + vendaDinheiro.getData());
					valorTotal = valorTotal.add(vendaDinheiro.getVenda().getValorTotal());
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
					vendaDinheiros = vendaDinheiroDAO.listarEntreDiaValido(dt1, dt2, empresa.getCodigo());
					for (VendaDinheiro vendaDinheiro : vendaDinheiros) {
						valorTotal = valorTotal.add(vendaDinheiro.getVenda().getValorTotal());
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
		vendaDinheiro = (VendaDinheiro) event.getData();
		setCodigo(vendaDinheiro.getVenda().getCodigo());
		System.out.println("Item" + vendaDinheiro.getCodigo());

		VendaDAO vendaDAO = new VendaDAO();

		if (controlo.contains(codigo)) {
			controlo.remove(codigo);
		} else {
			itemsVendas = vendaDAO.carregarItem(codigo);
			controlo.add(codigo);
		}
	}

	public void carregarVenda(ActionEvent event) {
		vendaDinheiro = (VendaDinheiro) event.getComponent().getAttributes().get("vendaSelecionada");
		setCodigo(vendaDinheiro.getVenda().getCodigo());
		VendaDAO vendaDAO = new VendaDAO();
		try {
			itemsVendas = vendaDAO.carregarItem(codigo);
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os items de venda");
			erro.printStackTrace();
		}
	}

	public void salvarGuia() throws JRException, IOException {
		GuiaDeRemessa guiaDeRemessa = new GuiaDeRemessa();
		guiaDeRemessa.setData(LocalDate.now());
		guiaDeRemessa.setVendaDinheiro(vendaDinheiro);
		GuiaDeRemessaDAO guiaDeRemessaDAO = new GuiaDeRemessaDAO();
		try {
			guiaDeRemessaDAO.salvar(guiaDeRemessa);

			String caminho = Faces.getRealPath("/report/GPadrao.jasper");

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("CODIGO_VENDA", vendaDinheiro.getVenda().getCodigo());

			Connection conexao = HibernateUtil.getConexao();

			byte[] bytes = JasperRunManager.runReportToPdf(caminho, parametros, conexao);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream stream = response.getOutputStream();
			stream.write(bytes, 0, bytes.length);

			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os items de venda");
			erro.printStackTrace();
		}
	}

	public void adicionarComponentes() {
		valorTotal = new BigDecimal("0.00");
		if (pesquisa.equals(1)) {
			dataUnico = null;
			dataFim = null;
			listarComponente = true;
			vendaDinheiros = new ArrayList<>();
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
			vendaDinheiros = new ArrayList<>();
			vendaDinheiro = new VendaDinheiro();

		}

	}

	public void carregarVD(ActionEvent event) {
		vendaDinheiro = (VendaDinheiro) event.getComponent().getAttributes().get("vd");
		System.out.println("venda dinheiro " + vendaDinheiro.getCodigo());
	}

	public void cancelar() {
		vendaDinheiro.setObseracoes("Cancelado: " + vendaDinheiro.getObseracoes());
		vendaDinheiro.setEstado("Cancelado");
		VendaDinheiroDAO vendaDinheiroDAO = new VendaDinheiroDAO();
		vendaDinheiroDAO.merge(vendaDinheiro);
		Messages.create("Info").detail("Cancelado com Sucesso.").add();
		listar();
	}

	public void imprimir() throws IOException, JRException {
		try {
			String caminho = null;
			String moradaCompleta = empresa.getMorada() + " - " + empresa.getCidade();
			caminho = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/report/VDPadraoVerso.jasper");

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("REPORT_LOCALE", new Locale("pt", "MZN"));
			parametros.put("CODIGO_VENDA", vendaDinheiro.getVenda().getCodigo());
			parametros.put("ID", vendaDinheiro.getCodigoVD());
			parametros.put("Logo", autenticacaoBean.getCaminhoLogo());
			parametros.put("MORADA", moradaCompleta);
			parametros.put("CONTACTO", contacto);

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint jasperPrint = JasperFillManager.fillReport(caminho, parametros, conexao);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"attachment; filename=\"" + "VD" + vendaDinheiro.getCodigoVD() + "Verso.pdf" + "\"");
			ServletOutputStream stream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

			FacesContext.getCurrentInstance().responseComplete();

		} catch (JRException e) {
			Messages.addFlashGlobalError("Não é  possível imprimir" + e);
		}
	}

	public void imprimirOriginal() throws IOException, JRException {
		try {
			String caminho = null;
			String moradaCompleta = empresa.getMorada() + " - " + empresa.getCidade();
			caminho = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/report/VDPadrao.jasper");

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("REPORT_LOCALE", new Locale("pt", "MZN"));
			parametros.put("CODIGO_VENDA", vendaDinheiro.getVenda().getCodigo());
			parametros.put("ID", vendaDinheiro.getCodigoVD());
			parametros.put("Logo", autenticacaoBean.getCaminhoLogo());
			parametros.put("MORADA", moradaCompleta);
			parametros.put("CONTACTO", contacto);

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint jasperPrint = JasperFillManager.fillReport(caminho, parametros, conexao);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"attachment; filename=\"" + "VD" + vendaDinheiro.getCodigoVD() + "Original.pdf" + "\"");
			ServletOutputStream stream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

			FacesContext.getCurrentInstance().responseComplete();

		} catch (JRException e) {
			Messages.addFlashGlobalError("Não é  possível imprimir" + e);
		}
	}

}

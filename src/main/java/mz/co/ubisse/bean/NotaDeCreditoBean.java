package mz.co.ubisse.bean;

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
import javax.faces.event.ActionEvent;
import javax.persistence.Convert;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import mz.co.ubisse.converter.LocalDateConverter;
import mz.co.ubisse.dao.NotaDeCreditoDAO;
import mz.co.ubisse.domain.ItemsVenda;
import mz.co.ubisse.domain.NotaDeCredito;
import mz.co.ubisse.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class NotaDeCreditoBean implements Serializable {

	private NotaDeCredito notaDeCredito;
	private List<NotaDeCredito> notaDeCreditos;
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

	public NotaDeCredito getNotaDeCredito() {
		return notaDeCredito;
	}

	public void setNotaDeCredito(NotaDeCredito notaDeCredito) {
		this.notaDeCredito = notaDeCredito;
	}

	public List<NotaDeCredito> getNotaDeCreditos() {
		return notaDeCreditos;
	}

	public void setNotaDeCreditos(List<NotaDeCredito> notaDeCreditos) {
		this.notaDeCreditos = notaDeCreditos;
	}

	@PostConstruct
	public void inicializar() {
		tabelaItem = false;
		controlo = new ArrayList<>();
		notaDeCredito = new NotaDeCredito();
		valorTotal = new BigDecimal("0.00");
		calendario2 = true;
		dataComponente = false;
		dataComponenteLabel = false;
		dataFimComponente = false;
		dataInicioComponente = false;
		listarComponente = false;

	}

	@Convert(converter = LocalDateConverter.class)
	public void listar() {

		Instant instant = dataUnico.toInstant();
		LocalDate dt1 = instant.atZone(ZoneId.systemDefault()).toLocalDate();

		try {
			NotaDeCreditoDAO notaDeCreditoDAO = new NotaDeCreditoDAO();
			if (pesquisa.equals(1) && dataFim == null) {
				System.out.println();
				valorTotal = new BigDecimal("0.00");
				notaDeCreditos = notaDeCreditoDAO.listarDiaValido(dt1);
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
					notaDeCreditos = notaDeCreditoDAO.listarEntreDiaValido(dt1, dt2);

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
			notaDeCreditos = new ArrayList<>();
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
			notaDeCreditos = new ArrayList<>();
			notaDeCredito = new NotaDeCredito();

		}

	}

	// Imprimir
	public void imprimir(ActionEvent event) throws JRException {
		notaDeCredito = (NotaDeCredito) event.getComponent().getAttributes().get("vendaSelecionada");
		try {

			String caminho = Faces.getRealPath("/report/NCPadrao.jasper");
			String caminho2 = Faces.getRealPath("/report/NCPadraoVerso.jasper");
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("REPORT_LOCALE", new Locale("pt", "MZN"));
			parametros.put("CODIGO_VENDA", notaDeCredito.getFactura().getVenda().getCodigo());

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			JasperPrintManager.printReport(relatorio, true);
			JasperPrint relatorio2 = JasperFillManager.fillReport(caminho2, parametros, conexao);
			JasperPrintManager.printReport(relatorio2, true);
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os items de venda");
			erro.printStackTrace();
		}

	}

}

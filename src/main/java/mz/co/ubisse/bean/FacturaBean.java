package mz.co.ubisse.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.UnselectEvent;

import mz.co.ubisse.converter.LocalDateConverter;
import mz.co.ubisse.dao.FacturaDAO;
import mz.co.ubisse.dao.GuiaDeRemessaDAO;
import mz.co.ubisse.dao.NotaDeCreditoDAO;
import mz.co.ubisse.dao.ReciboDAO;
import mz.co.ubisse.dao.VendaDAO;
import mz.co.ubisse.domain.Empresa;
import mz.co.ubisse.domain.Factura;
import mz.co.ubisse.domain.GuiaDeRemessa;
import mz.co.ubisse.domain.ItemsDebido;
import mz.co.ubisse.domain.ItemsVenda;
import mz.co.ubisse.domain.NotaDeCredito;
import mz.co.ubisse.domain.NotaDeDebido;
import mz.co.ubisse.domain.Recibo;
import mz.co.ubisse.domain.Usuario;
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
public class FacturaBean implements Serializable {

	private Factura factura;
	private Recibo recibo;
	private NotaDeCredito notaDeCredito;
	private NotaDeDebido notaDeDebido;
	private Usuario usuario;
	private Empresa empresa;
	private List<Factura> facturas;
	private List<ItemsVenda> itemsVendas;
	private List<ItemsVenda> itemsVendasCheckbox;
	private List<ItemsVenda> itemsVendasDebido;
	private List<ItemsDebido> itemsDebidos;
	private List<NotaDeDebido> notaDeDebidos;
	private List<NotaDeCredito> notaDeCreditos;
	private List<Recibo> recibos;

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
	private Boolean parcialTotal;
	private Boolean facturaUnica;

	private List<Long> controlo;
	private BigDecimal valorTotal;
	private BigDecimal valorTotalRecibo;
	private String observacao;
	private String tipoPagamento;
	private String detalhePagamento;
	private String moradaCompleta;
	private String contacto;

	private AutenticacaoBean autenticacaoBean;

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Recibo getRecibo() {
		return recibo;
	}

	public void setRecibo(Recibo recibo) {
		this.recibo = recibo;
	}

	public NotaDeCredito getNotaDeCredito() {
		return notaDeCredito;
	}

	public void setNotaDeCredito(NotaDeCredito notaDeCredito) {
		this.notaDeCredito = notaDeCredito;
	}

	public NotaDeDebido getNotaDeDebido() {
		return notaDeDebido;
	}

	public void setNotaDeDebido(NotaDeDebido notaDeDebido) {
		this.notaDeDebido = notaDeDebido;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public List<ItemsVenda> getItemsVendas() {
		return itemsVendas;
	}

	public void setItemsVendas(List<ItemsVenda> itemsVendas) {
		this.itemsVendas = itemsVendas;
	}

	public List<ItemsDebido> getItemsDebidos() {
		return itemsDebidos;
	}

	public void setItemsDebidos(List<ItemsDebido> itemsDebidos) {
		this.itemsDebidos = itemsDebidos;
	}

	public List<NotaDeDebido> getNotaDeDebidos() {
		return notaDeDebidos;
	}

	public void setNotaDeDebidos(List<NotaDeDebido> notaDeDebidos) {
		this.notaDeDebidos = notaDeDebidos;
	}

	public List<NotaDeCredito> getNotaDeCreditos() {
		return notaDeCreditos;
	}

	public void setNotaDeCreditos(List<NotaDeCredito> notaDeCreditos) {
		this.notaDeCreditos = notaDeCreditos;
	}

	public List<Recibo> getRecibos() {
		return recibos;
	}

	public void setRecibos(List<Recibo> recibos) {
		this.recibos = recibos;
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

	public List<ItemsVenda> getItemsVendasCheckbox() {
		return itemsVendasCheckbox;
	}

	public void setItemsVendasCheckbox(List<ItemsVenda> itemsVendasCheckbox) {
		this.itemsVendasCheckbox = itemsVendasCheckbox;
	}

	public List<ItemsVenda> getItemsVendasDebido() {
		return itemsVendasDebido;
	}

	public void setItemsVendasDebido(List<ItemsVenda> itemsVendasDebido) {
		this.itemsVendasDebido = itemsVendasDebido;
	}

	public Boolean getParcialTotal() {
		return parcialTotal;
	}

	public void setParcialTotal(Boolean parcialTotal) {
		this.parcialTotal = parcialTotal;
	}

	public Boolean getFacturaUnica() {
		return facturaUnica;
	}

	public void setFacturaUnica(Boolean facturaUnica) {
		this.facturaUnica = facturaUnica;
	}

	public BigDecimal getValorTotalRecibo() {
		return valorTotalRecibo;
	}

	public void setValorTotalRecibo(BigDecimal valorTotalRecibo) {
		this.valorTotalRecibo = valorTotalRecibo;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public String getDetalhePagamento() {
		return detalhePagamento;
	}

	public void setDetalhePagamento(String detalhePagamento) {
		this.detalhePagamento = detalhePagamento;
	}

	public FacturaBean() {
		autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
		usuario = autenticacaoBean.getUsuarioLogado();
		empresa = usuario.getFuncionario().getPessoa().getEmpresa();
		moradaCompleta = empresa.getMorada() + " - " + empresa.getCidade();

		if (empresa.getActivacaoFacturacao().isEmpty()) {
			observacao = ". Documento emitido para fins de Formação. Não tem validade fiscal.";
		}

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

	@PostConstruct
	public void inicializar() {
		tabelaItem = false;
		controlo = new ArrayList<>();
		factura = new Factura();
		valorTotal = new BigDecimal("0.00");
		calendario2 = true;
		dataComponente = false;
		dataComponenteLabel = false;
		dataFimComponente = false;
		dataInicioComponente = false;
		listarComponente = false;
		facturaUnica = false;

	}

	@Convert(converter = LocalDateConverter.class)
	public void listar() {

		Instant instant = dataUnico.toInstant();
		LocalDate dt1 = instant.atZone(ZoneId.systemDefault()).toLocalDate();

		try {
			FacturaDAO facturaDAO = new FacturaDAO();
			if (pesquisa.equals(1) && dataFim == null) {
				System.out.println();
				valorTotal = new BigDecimal("0.00");
				facturas = facturaDAO.listarDiaValido(dt1, empresa.getCodigo());

				for (Factura factura : facturas) {
					valorTotal = valorTotal.add(factura.getVenda().getValorTotal());
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

					facturas = facturaDAO.listarEntreDiaValido(dt1, dt2, empresa.getCodigo());
					for (Factura factura : facturas) {
						valorTotal = valorTotal.add(factura.getVenda().getValorTotal());
					}

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
			facturas = new ArrayList<>();
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
			facturas = new ArrayList<>();
			factura = new Factura();

		}

	}

	/** Carregamentos **/
	public void carregarFactura(ToggleEvent event) {

		tabelaItem = false;
		System.out.println("Dentro");
		factura = (Factura) event.getData();
		setCodigo(factura.getVenda().getCodigo());

		VendaDAO vendaDAO = new VendaDAO();

		if (controlo.contains(codigo)) {
			controlo.remove(codigo);
		} else {
			itemsVendas = vendaDAO.carregarItem(codigo);
			controlo.add(codigo);
		}
	}

	public void carregarFactura(ActionEvent evento) {
		setValorTotalRecibo(factura.getSaldo());
		System.out.println("saldo " + getValorTotalRecibo());
		notaDeDebido = new NotaDeDebido();
	}

	public void carregarFacturaComRelacionados(ActionEvent event) {
		facturaUnica = true;
		factura = (Factura) event.getComponent().getAttributes().get("facturaComRelacionadosSelecionada");
		setCodigo(factura.getVenda().getCodigo());
		VendaDAO vendaDAO = new VendaDAO();
		FacturaDAO facturaDAO = new FacturaDAO();
		try {
			itemsVendas = vendaDAO.carregarItem(codigo);
			notaDeCreditos = facturaDAO.listarPorFactura(factura);
			notaDeDebidos = facturaDAO.listarPorFacturaDebido(factura);
			recibos = facturaDAO.listarPorFacturaRecibo(factura);
			System.out.println("factura sim ou nao " + facturaUnica);
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Items ou documentos relacionados da Factura");
			erro.printStackTrace();
		}
	}

	public void carregarVenda(ActionEvent event) {
		factura = (Factura) event.getComponent().getAttributes().get("vendaSelecionada");
		setCodigo(factura.getVenda().getCodigo());
		VendaDAO vendaDAO = new VendaDAO();
		System.out.println("Carregar venda");
		try {
			System.out.println("Carregar venda 2");
			itemsVendas = vendaDAO.carregarItem(codigo);
			System.out.println("Items " + itemsVendas);
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os items de venda");
			erro.printStackTrace();
		}
	}

	public void carregarRecibo(ActionEvent evento) {
		recibo = (Recibo) evento.getComponent().getAttributes().get("reciboSelecionado");
	}

	public void carregarNC(ActionEvent evento) {
		notaDeCredito = (NotaDeCredito) evento.getComponent().getAttributes().get("ncSelecionado");
	}

	public void carregarND(ActionEvent evento) {
		System.out.println("dentro");
		notaDeDebido = (NotaDeDebido) evento.getComponent().getAttributes().get("ndSelecionado");
		System.out.println("valor nota" + notaDeDebido);
	}

	/** Fim Carregamentos **/

	/** Salvar **/
	public void salvarGuia() throws JRException, IOException {
		GuiaDeRemessa guiaDeRemessa = new GuiaDeRemessa();
		guiaDeRemessa.setData(LocalDate.now());
		guiaDeRemessa.setFactura(factura);
		GuiaDeRemessaDAO guiaDeRemessaDAO = new GuiaDeRemessaDAO();
		try {
			guiaDeRemessaDAO.salvar(guiaDeRemessa);

			String caminho = Faces.getRealPath("/report/GFacturaPadrao.jasper");

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("CODIGO_VENDA", factura.getVenda().getCodigo());

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

	public void pagarFactura() throws IOException {
		System.out.println("area de pagamento");
		if (factura.getEstado().equals("Expirado")) {
			Messages.create("Aviso !").warn().detail("Esta Factura expirou, não é permitido o seu pagamento. ").add();
		} else {
			if (getTipoPagamento().isEmpty()) {
				Messages.create("Aviso! ").warn().detail("Informe o tipo de Pagamento.").add();
			} else if (getTipoPagamento() == "Check" && getDetalhePagamento().isEmpty()) {
				Messages.create("Aviso! ").warn().detail("Informe o numero do check por favor").add();
			} else {
				try {
					calculaRecibo();

					if (factura.getSaldo().doubleValue() > new BigDecimal(0).doubleValue()) {
						factura.setEstado("Em Aberto");
					} else {
						factura.setEstado("Pago");

					}

					factura.setDataPagamento(new Date());

					recibo.setTipoPagamento(getTipoPagamento());
					recibo.setDetalheTipoPagamento(getDetalhePagamento());
					recibo.setObservacoes(observacao);
					recibo.setData(LocalDate.now());

					if (notaDeDebidos.size() == 0 && notaDeCreditos.size() == 0) {
						recibo.setValorPagoFactura(recibo.getValorTotal());
						recibo.setControlo('f');
					} else if (notaDeDebidos.size() > 0) {
						notaDeDebido = new NotaDeDebido();
						for (NotaDeDebido notaDeDebid : notaDeDebidos) {
							System.out.println("eee" + notaDeDebido.getDataDeEmissao());
							notaDeDebido = notaDeDebid;

						}
						System.out.println("valor debido " + notaDeDebido.getValorTotal() + " f " + factura.getSaldo());
						if (notaDeDebido.getValorTotal().doubleValue() == getValorTotalRecibo().doubleValue()) {
							recibo.setValorPagoFactura(new BigDecimal(0.00));
							recibo.setValorPagoDebido(getValorTotalRecibo());
						} else {
							recibo.setValorPagoFactura(factura.getVenda().getValorTotal());
							recibo.setValorPagoDebido(notaDeDebido.getValorTotal());
						}

						recibo.setControlo('d');
					} else if (notaDeCreditos.size() > 0) {
						recibo.setValorPagoFactura(recibo.getValorTotal());
						recibo.setControlo('c');
					}

					FacturaDAO facturaDAO = new FacturaDAO();
					facturaDAO.merge(factura);
					recibo.setEstado("Final");
					recibo.setFactura(factura);
					ReciboDAO reciboDAO = new ReciboDAO();
					reciboDAO.salvar(recibo);

					Messages.create("Confirmação:: ").detail("Pago com Sucesso.").add();
					notaDeCreditos = facturaDAO.listarPorFactura(factura);
					notaDeDebidos = facturaDAO.listarPorFacturaDebido(factura);
					recibos = facturaDAO.listarPorFacturaRecibo(factura);

					// imprimirRecibo();
				} catch (RuntimeException erro) {
					System.out.println("area de pagamento erro");
					Messages.create("Erro::").error()
							.detail("Ocorreu um erro ao tentar pagar a Factura, tente novamente.").add();
					erro.printStackTrace();
				}
			}
		}
	}

	public void cancelarFactura() {

		try {
			FacturaDAO facturaDAO = new FacturaDAO();
			factura.setEstado("Cancelado");
			if (factura.getSaldo().equals(null)) {
				System.out.println("saldo nullo");
			}
			factura.setObseracoes("CANCELADO : " + factura.getObseracoes());
			factura.setSaldo(new BigDecimal(0));
			facturaDAO.merge(factura);
			Messages.create("Info").detail("Cancelado com Sucesso.").add();
			// imprimirRecibo();

		} catch (RuntimeException erro) {
			Messages.create("Erro::").error().detail("Ocorreu um erro ao tentar cancelar a Factura, tente novamente.")
					.add();
			erro.printStackTrace();
		}
	}

	public void salvarNotaDeCredito() throws IOException {
		try {
			
			System.out.println("tamanho do arry " + itemsVendasCheckbox.size());
			for (ItemsVenda long1 : itemsVendasCheckbox) {
				System.out.println(long1.getProduto() + " quantidade");
			}
			if (itemsVendasCheckbox.isEmpty()) {
				Messages.create("Aviso !").warn().detail("Selecione os items por cancelar. Por Favor ").add();
			} else {

				for (Recibo recibo1 : recibos) {
					if (recibo1.getFactura().getCodigo() == factura.getCodigo()) {
						recibo = recibo1;
					}
				}
				if (recibo.getEstado().equals("Final")) {
					Messages.create("Aviso !").warn().detail("Factura paga cancele o pagamanto. Por Favor ").add();
				} else {
					calculaNotaDeCredito();
					FacturaDAO facturaDAO = new FacturaDAO();
					factura.setEstado("Final");
					if (factura.getSaldo().equals(null)) {
						System.out.println("saldo nullo");
					}
					notaDeCredito.setCodigoNC("00000" + 1 + empresa.getLetraCodigo());
					System.out.println("ultimo codigo da nc " + notaDeCredito.getCodigoNC());
					System.out.println("saldo " + factura.getSaldo() + " nota de cdt " + notaDeCredito.getValorTotal());
					factura.setSaldo(factura.getSaldo().subtract(notaDeCredito.getValorTotal()));
					notaDeCredito = facturaDAO.validar(factura, itemsVendasCheckbox, notaDeCredito);
					notaDeCredito.setFactura(factura);

					notaDeCreditos = facturaDAO.listarPorFactura(notaDeCredito.getFactura());
					// notaDeDebidos = facturaDAO.listarPorFacturaDebido(factura);
					// recibos = facturaDAO.listarPorFacturaRecibo(factura);

					// imprimirNC();
				}

			}
		} catch (RuntimeException erro) {
			Messages.create("Erro::").error()
					.detail("Ocorreu um erro ao tentar cancelar imprimir a nota de credito, tente novamente.").add();
			erro.printStackTrace();
		}

	}

	public void salvarNotaDeDebido() throws IOException {
		try {
			System.out.println("Observacoes" + notaDeDebido.getObseracoes());
			if (itemsVendasDebido.isEmpty()) {
				Messages.create("Aviso !").warn().detail("Selecione os items por cancelar. Por Favor ").add();
			}
			if (notaDeDebido.getObseracoes() == null) {
				System.out.println("sem motivo||||||||||||");
				Messages.create("Aviso !").warn().detail("Indique o motivo da emissão da nota de débito. Por Favor ")
						.add();
			} else {
				calculaNotaDeDebido();
				FacturaDAO facturaDAO = new FacturaDAO();
				factura.setEstado("Final");
				if (factura.getSaldo().equals(null)) {
					System.out.println("saldo nullo");
				}
				notaDeDebido.setObseracoes(observacao);
				factura.setSaldo(factura.getSaldo().add(notaDeDebido.getValorTotal()));
				notaDeDebido = facturaDAO.validar(factura, itemsVendasDebido, itemsDebidos, notaDeDebido);

				System.out.println("nota de debido recuperado " + notaDeDebido.getCodigo());

				// notaDeCreditos = facturaDAO.listarPorFactura(factura);
				notaDeDebidos = facturaDAO.listarPorFacturaDebido(factura);
				// recibos = facturaDAO.listarPorFacturaRecibo(factura);

				imprimirND();
			}
		} catch (RuntimeException erro) {
			Messages.create("Erro::").error()
					.detail("Ocorreu um erro ao tentar gerar a nota de debido, tente novamente.").add();
			erro.printStackTrace();
		}

	}

	/** Fim Salvar **/
	/** Cancelar **/
	public void cancelarRecibo() {
		ReciboDAO reciboDAO = new ReciboDAO();
		FacturaDAO facturaDAO = new FacturaDAO();
		try {
			recibo.setEstado("Cancelado");
			factura.setSaldo(factura.getSaldo().add(recibo.getValorTotal()));
			reciboDAO.merge(recibo);
			facturaDAO.merge(factura);
			Messages.create("Sucesso:::").detail("recibo cancelado com sucesso.").add();
		} catch (RuntimeException erro) {
			Messages.create("Erro::").error().detail("Ocorreu um erro ao tentar cancelar o recibo, tente novamente.")
					.add();
			erro.printStackTrace();
		}

	}

	/** Fim Cnacelar **/

	public void controloNotaDeCredito(CellEditEvent event) {
		Object newValue = event.getNewValue();
		ItemsVenda itemsVenda = (ItemsVenda) ((DataTable) event.getComponent()).getRowData();

		System.out.println(itemsVenda.getQuantidade());

		if (newValue != null) {
			if ((Short) newValue > itemsVenda.getQuantidade()) {
				System.out.println("Maior ");
				Messages.create("Aviso!").warn().detail("A quantidade cancelada 'e maior que a facturada.").add();
			} else if ((Short) newValue < 0) {
				System.out.println("N permitido ");
				Messages.create("Aviso!").warn().detail("Valor nao permitido.").add();
			} else {
				System.out.println("Permitido ");
			}

		}
	}

	public void controloNotaDeDebido(CellEditEvent event) {
		// Object newValue = event.getNewValue();
		// ItemsVenda itemsVenda = (ItemsVenda) ((DataTable)
		// event.getComponent()).getRowData();

		// System.out.println(itemsVenda.getQuantidade());

		/*
		 * if (newValue != null) { if ((Short) newValue > itemsVenda.getQuantidade()) {
		 * System.out.println("Maior "); Messages.create("Aviso!").warn().
		 * detail("A quantidade cancelada 'e maior que a facturada.").add(); } else if
		 * ((Short) newValue < 0) { System.out.println("N permitido ");
		 * Messages.create("Aviso!").warn().detail("Valor nao permitido.").add(); } else
		 * { System.out.println("Permitido "); }
		 * 
		 * }
		 */
	}

	/** Calculos **/
	public void calculaNotaDeCredito() {
		notaDeCredito = new NotaDeCredito();
		Integer codigoSemLetras;
		FacturaDAO facturaDAO = new FacturaDAO();
		//notaDeCredito = facturaDAO.buscarPorUltimoNC(empresa.getCodigo());
		if (notaDeCredito == null) {
			System.out.println("vazio");
			notaDeCredito = new NotaDeCredito();
			notaDeCredito.setCodigoNC("00000" + 1 + empresa.getLetraCodigo());
			System.out.println("codigo da notaDeCredito 2" + notaDeCredito.getCodigoNC());
		} else {
			System.out.println("n vazio");
			System.out.println("codigo da recibo 3" + recibo.getCodigoRC());
			String numero = recibo.getCodigoRC();
			codigoSemLetras = Integer.parseInt(numero.replaceAll("[^0-9]*", ""));

			if (codigoSemLetras.toString().length() == 1) {
				notaDeCredito.setCodigoNC("00000" + (codigoSemLetras + 1) + empresa.getLetraCodigo());
			} else if (codigoSemLetras.toString().length() == 2) {
				notaDeCredito.setCodigoNC("0000" + (codigoSemLetras + 1) + empresa.getLetraCodigo());
			} else if (codigoSemLetras.toString().length() == 3) {
				notaDeCredito.setCodigoNC("000" + (codigoSemLetras + 1) + empresa.getLetraCodigo());
			} else if (codigoSemLetras.toString().length() == 4) {
				notaDeCredito.setCodigoNC("00" + (codigoSemLetras + 1) + empresa.getLetraCodigo());
			} else if (codigoSemLetras.toString().length() == 5) {
				notaDeCredito.setCodigoNC("0" + (codigoSemLetras + 1) + empresa.getLetraCodigo());
			} else if (codigoSemLetras.toString().length() >= 6) {
				notaDeCredito.setCodigoNC((codigoSemLetras + 1) + empresa.getLetraCodigo());
			}
		}

		notaDeCredito.setDataDeEmissao(LocalDate.now());
		notaDeCredito.setValorTotal(new BigDecimal("0.00"));

		for (int i = 0; i < itemsVendasCheckbox.size(); i++) {
			ItemsVenda itemVenda = itemsVendasCheckbox.get(i);

			System.out.println("item nr " + itemVenda.getCodigo());
			if (itemVenda.getQuantidadeCancelada() == 0) {
				itemVenda.setQuantidadeCancelada(itemVenda.getQuantidade());
			}
			System.out.println(
					"unitario " + itemVenda.getValorUnitario() + " cancelada " + itemVenda.getQuantidadeCancelada());

			notaDeCredito.setValorTotal(notaDeCredito.getValorTotal()
					.add(itemVenda.getValorUnitario().multiply(new BigDecimal(itemVenda.getQuantidadeCancelada()))));

		}
		notaDeCredito.setValorSubtotal(
				notaDeCredito.getValorTotal().divide(new BigDecimal(1.17), 2, RoundingMode.HALF_EVEN));
		notaDeCredito.setValorPagoIva(notaDeCredito.getValorTotal().subtract(notaDeCredito.getValorSubtotal()));
	}

	public void calculaNotaDeDebido() {
		itemsDebidos = new ArrayList<>();
		ItemsDebido itemsDebido = new ItemsDebido();
		notaDeDebido.setValorTotal(new BigDecimal("0.00"));
		notaDeDebido.setDataDeEmissao(LocalDate.now());

		for (int i = 0; i < itemsVendasDebido.size(); i++) {
			ItemsVenda itemVenda = itemsVendasDebido.get(i);

			notaDeDebido.setValorTotal(notaDeDebido.getValorTotal().add(itemVenda.getValorParcial()));

			String produto = itemVenda.getProduto().getDescricao();
			System.out.println("produto " + produto);
			itemsDebido.setProduto(produto);
			itemsDebido.setQuantidade(itemVenda.getQuantidade());
			itemsDebido.setValorParcial(itemVenda.getValorParcial());
			itemsDebidos.add(itemsDebido);
		}
		notaDeDebido
				.setValorSubtotal(notaDeDebido.getValorTotal().divide(new BigDecimal(1.17), 2, RoundingMode.HALF_EVEN));
		notaDeDebido.setValorPagoIva(notaDeDebido.getValorTotal().subtract(notaDeDebido.getValorSubtotal()));
	}

	public void calculaRecibo() {
		Integer codigoSemLetras;
		FacturaDAO facturaDAO = new FacturaDAO();
		recibo = facturaDAO.buscarPorUltimoRecibo(empresa.getCodigo());
		if (recibo == null) {
			System.out.println("vazio");
			recibo = new Recibo();
			recibo.setCodigoRC("00000" + 1 + empresa.getLetraCodigo());
			System.out.println("codigo da recibo 2" + recibo.getCodigoRC());
		} else {
			System.out.println("n vazio");
			System.out.println("codigo da recibo 3" + recibo.getCodigoRC());
			String numero = recibo.getCodigoRC();
			codigoSemLetras = Integer.parseInt(numero.replaceAll("[^0-9]*", ""));

			if (codigoSemLetras.toString().length() == 1) {
				recibo.setCodigoRC("00000" + (codigoSemLetras + 1) + empresa.getLetraCodigo());
			} else if (codigoSemLetras.toString().length() == 2) {
				recibo.setCodigoRC("0000" + (codigoSemLetras + 1) + empresa.getLetraCodigo());
			} else if (codigoSemLetras.toString().length() == 3) {
				recibo.setCodigoRC("000" + (codigoSemLetras + 1) + empresa.getLetraCodigo());
			} else if (codigoSemLetras.toString().length() == 4) {
				recibo.setCodigoRC("00" + (codigoSemLetras + 1) + empresa.getLetraCodigo());
			} else if (codigoSemLetras.toString().length() == 5) {
				recibo.setCodigoRC("0" + (codigoSemLetras + 1) + empresa.getLetraCodigo());
			} else if (codigoSemLetras.toString().length() >= 6) {
				recibo.setCodigoRC((codigoSemLetras + 1) + empresa.getLetraCodigo());
			}
		}
		recibo.setChaveEmpresa(empresa.getCodigo());
		recibo.setValorTotal(getValorTotalRecibo());
		recibo.setValorSubtotal(recibo.getValorTotal().divide(new BigDecimal(1.17), 2, RoundingMode.HALF_EVEN));
		recibo.setValorPagoIva(recibo.getValorTotal().subtract(recibo.getValorSubtotal()));
		System.out.println("saldo factura " + factura.getSaldo() + " saldo recibo " + getValorTotalRecibo());
		factura.setSaldo(factura.getSaldo().subtract(getValorTotalRecibo()));
		System.out.println("saldo da factura actualizado " + factura.getSaldo());
	}

	/** Fim Calculos **/

	/** Imprimir **/
	public void imprimirNC() {
		try {

			String caminho = Faces.getRealPath("/report/NCPadrao.jasper");
			String caminho2 = Faces.getRealPath("/report/NCPadraoVerso.jasper");
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("REPORT_LOCALE", new Locale("pt", "MZN"));
			parametros.put("CODIGO_VENDA", factura.getVenda().getCodigo());

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

	public void imprimirND() {
		try {
			System.out.println("nota de debido recuperado " + notaDeDebido.getCodigo());
			String caminho = Faces.getRealPath("/report/NDPadrao.jasper");
			String caminho2 = Faces.getRealPath("/report/NDPadraoVerso.jasper");

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("REPORT_LOCALE", new Locale("pt", "MZN"));
			parametros.put("NOTA", notaDeDebido.getCodigo());

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

	public void imprimir() throws IOException {

		try {
			String caminho = null;

			if (notaDeDebidos.size() == 0 && notaDeCreditos.size() == 0) {
				caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/report/RPadrao.jasper");

			} else if (notaDeDebidos.size() > 0) {
				caminho = FacesContext.getCurrentInstance().getExternalContext()
						.getRealPath("/report/RPadraoDebido.jasper");

			} else if (notaDeCreditos.size() > 0) {
				caminho = FacesContext.getCurrentInstance().getExternalContext()
						.getRealPath("/report/RPadraoCredito.jasper");
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

	public void imprimir2() throws IOException {

		try {
			String caminho = null;

			if (notaDeDebidos.size() == 0 && notaDeCreditos.size() == 0) {
				caminho = FacesContext.getCurrentInstance().getExternalContext()
						.getRealPath("/report/RPadraoVerso.jasper");

			} else if (notaDeDebidos.size() > 0) {
				caminho = FacesContext.getCurrentInstance().getExternalContext()
						.getRealPath("/report/RPadraoDebidoVerso.jasper");

			} else if (notaDeCreditos.size() > 0) {
				caminho = FacesContext.getCurrentInstance().getExternalContext()
						.getRealPath("/report/RPadraoCreditoVerso.jasper");
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

	public void imprimirFactura() throws IOException, JRException {

		String caminho = Faces.getRealPath("/report/FacturaPadrao.jasper");
		String caminho2 = Faces.getRealPath("/report/FacturaPadraoVerso.jasper");

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("REPORT_LOCALE", new Locale("pt", "MZN"));
		parametros.put("CODIGO_VENDA", factura.getVenda().getCodigo());

		Connection conexao = HibernateUtil.getConexao();

		JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
		JasperPrintManager.printReport(relatorio, true);
		JasperPrint relatorio2 = JasperFillManager.fillReport(caminho2, parametros, conexao);
		JasperPrintManager.printReport(relatorio2, true);

	}

	public void onRowSelect(SelectEvent event) {
		System.out.println("selecionado");
	}

	public void onRowUnselect(UnselectEvent event) {
		System.out.println("tirando a selecao");
	}

	public void selecionado() {
		System.out.println("ssss");
	}
	/** Fim Imprimir **/

}

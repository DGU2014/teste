package mz.co.ubisse.bean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import mz.co.ubisse.dao.FacturaDAO;
import mz.co.ubisse.dao.ReciboDAO;
import mz.co.ubisse.dao.VendaDAO;
import mz.co.ubisse.domain.Factura;
import mz.co.ubisse.domain.Recibo;
import mz.co.ubisse.domain.Venda;
import mz.co.ubisse.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class Factura2Bean implements Serializable {

	private Factura factura;
	private Recibo recibo;
	private List<Factura> facturas;
	private List<Venda> vendas;

	private Long codigo;

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

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@PostConstruct
	public void listar() {
		try {
			FacturaDAO facturaDAO = new FacturaDAO();
			facturas = facturaDAO.listar("codigo");
			System.out.println("Facturas Listadas");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar listar as Facturas");
			erro.printStackTrace();
		}
	}

	public void pagarFactura() throws IOException {
		factura.setEstado("Pago");
		factura.setDataPagamento(new Date());
		recibo = new Recibo();
		try {

			FacturaDAO facturaDAO = new FacturaDAO();
			facturaDAO.merge(factura);

			recibo.setFactura(factura);
			ReciboDAO reciboDAO = new ReciboDAO();
			reciboDAO.salvar(recibo);

			imprimirRecibo();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar efectuar o pagamento");
			erro.printStackTrace();
		}
	}

	public void carregarFactura(ActionEvent evento) {
		factura = (Factura) evento.getComponent().getAttributes().get("facturaSelecionada");
	}

	public void imprimirRecibo() throws IOException {
		try {
			String caminho = Faces.getRealPath("/report/Recibo.jasper");
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("FACTURA", factura.getCodigo());
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
		} catch (JRException e) {
			e.printStackTrace();
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar o recibo");
		}

	}
	public void carregarVenda(ActionEvent event) {
		factura=  (Factura) event.getComponent().getAttributes().get("vendaSelecionada");
		
		setCodigo(factura.getVenda().getCodigo());
	

	}
	public Venda getItemsDaVenda() {
		System.out.println("venda carregada nr 3 " + getCodigo());
		Long c = getCodigo();
		System.out.println("venda carregada nr 4 " + c);
		VendaDAO vendaDAO = new VendaDAO();

		return vendaDAO.readAll(c);
	}

}

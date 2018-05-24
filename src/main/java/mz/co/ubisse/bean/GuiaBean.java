package mz.co.ubisse.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import mz.co.ubisse.domain.Factura;
import mz.co.ubisse.domain.GuiaDeRemessa;
import mz.co.ubisse.domain.VendaDinheiro;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class GuiaBean implements Serializable {

	private GuiaDeRemessa guiaDeRemessa;
	private Factura factura;
	private VendaDinheiro vendaDinheiro;

	public GuiaDeRemessa getGuiaDeRemessa() {
		return guiaDeRemessa;
	}

	public void setGuiaDeRemessa(GuiaDeRemessa guiaDeRemessa) {
		this.guiaDeRemessa = guiaDeRemessa;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public VendaDinheiro getVendaDinheiro() {
		return vendaDinheiro;
	}

	public void setVendaDinheiro(VendaDinheiro vendaDinheiro) {
		this.vendaDinheiro = vendaDinheiro;
	}

	public GuiaBean() {
		guiaDeRemessa = new GuiaDeRemessa();
	}

	public void salvar(ActionEvent evento) {

		
	}
}

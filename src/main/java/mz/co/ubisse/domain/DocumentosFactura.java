package mz.co.ubisse.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mz.co.ubisse.converter.LocalDateConverter;

@SuppressWarnings("serial")
@Entity
@Table(name = "documentos_da_factura")
public class DocumentosFactura extends GenericDomain{

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Factura factura;
	@Column(nullable = true, precision = 15, scale = 2)
	private BigDecimal valorTotal;
	@Column(nullable = true, name = "valor_subtotal", precision = 15, scale = 2)
	private BigDecimal valorSubtotal;
	@Column(nullable = true, name = "valor_pago_iva", precision = 15, scale = 2)
	private BigDecimal valorPagoIva;
	@Column(name = "observacoes", nullable = true, length = 400)
	private String obseracoes;
	@Convert(converter = LocalDateConverter.class)
	@Column(name = "data_emissao", nullable = false)
	private LocalDate dataDeEmissao;
	@Column(name="tipo_de_documento", nullable=false, length=15)
	private String tipoDeDocumento;
	@Column(nullable = true, length = 30)
	private String estado;
	
	public Factura getFactura() {
		return factura;
	}
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public BigDecimal getValorSubtotal() {
		return valorSubtotal;
	}
	public void setValorSubtotal(BigDecimal valorSubtotal) {
		this.valorSubtotal = valorSubtotal;
	}
	public BigDecimal getValorPagoIva() {
		return valorPagoIva;
	}
	public void setValorPagoIva(BigDecimal valorPagoIva) {
		this.valorPagoIva = valorPagoIva;
	}
	public String getObseracoes() {
		return obseracoes;
	}
	public void setObseracoes(String obseracoes) {
		this.obseracoes = obseracoes;
	}
	public LocalDate getDataDeEmissao() {
		return dataDeEmissao;
	}
	public void setDataDeEmissao(LocalDate dataDeEmissao) {
		this.dataDeEmissao = dataDeEmissao;
	}
	public String getTipoDeDocumento() {
		return tipoDeDocumento;
	}
	public void setTipoDeDocumento(String tipoDeDocumento) {
		this.tipoDeDocumento = tipoDeDocumento;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

}

package mz.co.ubisse.util;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import mz.co.ubisse.dao.FacturaDAO;
import mz.co.ubisse.domain.Factura;
import mz.co.ubisse.domain.NotaDeCredito;
import mz.co.ubisse.domain.Recibo;

public class FacturaDAOTest {

	@Test
	@Ignore
	public void listarRecibo() {

		FacturaDAO facturaDAO = new FacturaDAO();

		ArrayList<Recibo> recibos;
		Factura factura = new Factura();
		factura.setCodigo(65L);
		recibos = (ArrayList<Recibo>) facturaDAO.listarPorFacturaRecibo(factura);

		for (Recibo recibo : recibos) {
			System.out.println(recibo.getFactura());
		}
	}

	@Test
	@Ignore
	public void nc() {
		FacturaDAO facturaDAO = new FacturaDAO();
		NotaDeCredito notaDeCredito = new NotaDeCredito();
	facturaDAO.buscarPorUltimoNC(3L);

	}

}

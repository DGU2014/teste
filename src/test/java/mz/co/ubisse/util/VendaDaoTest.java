package mz.co.ubisse.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import mz.co.ubisse.converter.LocalDateConverter;
import mz.co.ubisse.dao.ClienteDAO;
import mz.co.ubisse.dao.CotacaoDAO;
import mz.co.ubisse.dao.FacturaDAO;
import mz.co.ubisse.dao.VendaDAO;
import mz.co.ubisse.dao.VendaDinheiroDAO;
import mz.co.ubisse.domain.Cliente;
import mz.co.ubisse.domain.Cotacao;
import mz.co.ubisse.domain.Factura;
import mz.co.ubisse.domain.ItemsVenda;
import mz.co.ubisse.domain.Pessoa;
import mz.co.ubisse.domain.Venda;
import mz.co.ubisse.domain.VendaDinheiro;

public class VendaDaoTest {

	@SuppressWarnings({ "unused", "unchecked" })
	@Test
	@Ignore
	public void historico() {
		List<Venda> vendas = new ArrayList<>();
		List<VendaDinheiro> vendaDinheiros;
		VendaDAO vendaDAO = new VendaDAO();
		vendas = vendaDAO.buscarHorario(4L);
		Long cdg = new Long(vendas.get(0) + "");
		System.out.println("codigo 1 " + cdg);

		Venda venda = vendaDAO.buscar(cdg);
		VendaDinheiroDAO vendaDinheiroDAO = new VendaDinheiroDAO();
		VendaDinheiro vendaDinheiro = new VendaDinheiro();
		vendaDinheiro = vendaDinheiroDAO.buscarPorVenda(venda.getCodigo());
		vendaDinheiros = vendaDinheiroDAO.listarV(venda.getCodigo());

		for (VendaDinheiro vendaDinheiro1 : vendaDinheiros) {
			cdg = vendaDinheiro1.getCodigo();
		}
		System.out.println("codigo 2 " + cdg);
	}

	@Test
	@Ignore
	public void listar() {
		try {

			List<VendaDinheiro> vendaDinheiros = new ArrayList<>();

			System.out.println("Sucesso");
			for (VendaDinheiro vendaDinheiro : vendaDinheiros) {
				System.out.println("Sucesso" + vendaDinheiro.getVenda().getCliente().getPessoa().getNome());

			}
		} catch (Exception erro) {

			erro.printStackTrace();
		}

	}

	@Test
	@Ignore
	public void itemVenda() {
		ArrayList<ItemsVenda> itemsVendas;
		VendaDAO vendaDAO = new VendaDAO();
		itemsVendas = (ArrayList<ItemsVenda>) vendaDAO.carregarItem(24L);

		for (ItemsVenda itemsVenda : itemsVendas) {
			System.out.println(" item " + itemsVenda.getProduto().getDescricao());
		}
		/*
		 * List<Venda> vendas; // ArrayList<Venda> vendas; Long c = 7L; VendaDAO
		 * vendaDAO = new VendaDAO();
		 * 
		 * Venda venda = new Venda(); venda = vendaDAO.readAll(c);
		 */

		// System.out.println("codigo da venda " + venda.getItemsVendas());
	}

	@Test
	@Ignore
	public void listarPorDia() {
		try {
			List<Factura> vendaDinheiros = new ArrayList<>();
			FacturaDAO facturaDAO = new FacturaDAO();
			//vendaDinheiros = facturaDAO.listarDiaValido(LocalDate.now());
			System.out.println("Sucesso" + vendaDinheiros.size() + " da data " + LocalDate.now());
			for (Factura factura : vendaDinheiros) {
				System.out.println("Sucesso" + factura.getCodigo());

				// System.out.println("f " +
				// vendaDinheiro.getVenda().getFuncionario().getPessoa().getApelido());
			}
		} catch (Exception erro) {

			erro.printStackTrace();
		}

	}

	@SuppressWarnings({ "static-access", "unused" })
	@Test
	@Ignore
	public void conveerter() {
		LocalDate date = null;
		LocalDateConverter converter = new LocalDateConverter();
		System.out.println("con " + date.now().format(DateTimeFormatter.ofPattern("yyyy-dd-MM")));
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	@Test
	@Ignore
	public void data() throws ParseException {
		Date date = new Date();
		System.out.println("dat " + date);
		LocalDate localDate = null;
		System.out.println("Data :");

		System.out.println("Data e :" + localDate.of(2015, date.getMonth(), date.getDay()));
	}

	@Test
	@Ignore
	public void salvarClienteVenda() {
		Pessoa pessoa = new Pessoa();
		Cliente cliente = new Cliente();

		cliente.setDataCadastro(new Date());
		pessoa.setNome("teste");

		ClienteDAO clienteDAO = new ClienteDAO();

		cliente = clienteDAO.salvarClienteVenda(cliente, pessoa);
		System.out.println("cliente " + cliente.getPessoa().getNome());
	}

	@Test
	public void ultimo() {

		CotacaoDAO cotacaoDAO = new CotacaoDAO();
		
		Cotacao cotacao = cotacaoDAO.buscarPorUltimo(2L);
	/*	VendaDinheiroDAO dinheiroDAO = new VendaDinheiroDAO();

		VendaDinheiro ab = dinheiroDAO.buscarPorUltimo(8L);
		System.out.println("dinheiro " + ab.getCodigoVD());
		String codigoCompleto;

		if (ab.getCodigoVD() == null) {
			codigoCompleto = "00000" + 1+"D";
			
			System.out.println("codigo "+codigoCompleto+"."+LocalDate.now().getYear());
		}else {
			String numero = ab.getCodigoVD();
			int numero2 = Integer.parseInt(numero.replaceAll("[^0-9]*", ""));
			int ac = numero2 + 1;

			System.out.println(ac);

			String s = "ubisse";
			String finals = s + ac;

			System.out.println(finals);
			
			Long nr = 1058L;
			String a = "00000";
			
			System.out.println(a + " " + nr.toString().length());
			if (nr.toString().length() == 1) {
				System.out.println(a);
			} else if (nr.toString().length() == 2) {
				a = "0000".concat(nr.toString());
				System.out.println(a);
			} else if (nr.toString().length() == 3) {
				a = "000" + nr;
				System.out.println(a);
			} else if (nr.toString().length() == 4) {
				a = "00" + nr;
				System.out.println(a);
			} else if (nr.toString().length() == 5) {
				a = "0" + nr;
				System.out.println(a);
			} else if (nr.toString().length() == 6) {
				a = "" + nr;
				System.out.println(a);
			}
		}*/

	

	}

	@Test
	@Ignore
	public void stringVenda() {

		String numero = "DTX345";
		int numero2 = Integer.parseInt(numero.replaceAll("[^0-9]*", ""));
		System.out.println(numero);

		int a = numero2 + 1;

		System.out.println(a);

		String s = "ubisse";
		String finals = s + a;

		System.out.println(finals);

	}
}
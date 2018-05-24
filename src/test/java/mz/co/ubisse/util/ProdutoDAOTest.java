package mz.co.ubisse.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;

import mz.co.ubisse.dao.FuncionarioDAO;
import mz.co.ubisse.dao.ProdutoDAO;
import mz.co.ubisse.dao.RequisicaoDAO;
import mz.co.ubisse.domain.Categoria;
import mz.co.ubisse.domain.Empresa;
import mz.co.ubisse.domain.Funcionario;
import mz.co.ubisse.domain.Produto;
import mz.co.ubisse.domain.RequisicaoProduto;

public class ProdutoDAOTest {

	@Test
	@Ignore
	public void salvar() {
		Produto produto = new Produto();
		Categoria categoria = new Categoria();
		categoria.setCodigo(1L);
		produto.setPreco(new BigDecimal(110000));
		produto.setCategoria(categoria);
		produto.setNome("Teste");
		produto.setReferencia("dff00");
		produto.setQuantidade((short) 2);

		ProdutoDAO produtoDAO = new ProdutoDAO();
		produtoDAO.merge(produto);
		System.out.println("Salvo com sucesso");
	}

	@SuppressWarnings("unused")
	@Test
	@Ignore
	public void listar() throws ParseException {

		Calendar cal = Calendar.getInstance();
		cal.set(DateTime.now().getYear(), DateTime.now().getMonthOfYear(), 28);

		DateTime dateTime = new DateTime();
		List<Produto> produtos = new ArrayList<>();
		ProdutoDAO produtoDAO = new ProdutoDAO();
		produtos = produtoDAO.listarEstoqueNegativo();

		DateFormat formatter = new SimpleDateFormat("yy-MM-dd");
		Date date = (Date) formatter
				.parse(dateTime.getYear() + "-" + dateTime.getMonthOfYear() + "-" + dateTime.getMonthOfYear());

		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();
		System.out.println("Localdate " + localDate.format(formatador).toString());

		for (Produto produto : produtos) {

			if (produto.getDataControloEstoque().toString().equals(localDate.format(formatador).toString())) {
				System.out.println("1 " + produto.getDataControloEstoque());
			} else {
				System.out.println("2 " + produto.getDataControloEstoque());
			}
		}
	}

	@SuppressWarnings("unused")
	@Test
	@Ignore
	public void itemVenda() {
		RequisicaoDAO dao = new RequisicaoDAO();

		Long c = 4L;
		ArrayList<RequisicaoProduto> requisicaoProdutos;

		RequisicaoProduto requisicaoProduto = new RequisicaoProduto();
		requisicaoProduto = dao.readAll(c);
		System.out.println(" items " + requisicaoProduto);

	}

	@Test
	@Ignore
	public void salva() {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		Produto produto = new Produto();
		Empresa empresa = new Empresa();
		empresa.setCodigo(1L);
		produto.setDescricao("test");
		produto.setPreco(new BigDecimal(12));
		//produto.setEmpresa(empresa);
		produtoDAO.merge(produto);
	}

	@SuppressWarnings("unused")
	@Test
	public void empresa() {

		List<Funcionario> produtos = new ArrayList<>();
		ProdutoDAO produtoDAO = new ProdutoDAO();

		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		produtos = funcionarioDAO.listarFuncionario(3L);

		for (Funcionario produto : produtos) {
			System.out.println(produto.getPessoa().getNome());
		}

	}
}

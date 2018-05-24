package mz.co.ubisse.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import mz.co.ubisse.domain.Factura;
import mz.co.ubisse.domain.ItemsDebido;
import mz.co.ubisse.domain.ItemsVenda;
import mz.co.ubisse.domain.NotaDeCredito;
import mz.co.ubisse.domain.NotaDeDebido;
import mz.co.ubisse.domain.Produto;
import mz.co.ubisse.domain.Recibo;
import mz.co.ubisse.util.HibernateUtil;

public class FacturaDAO extends GenericDAO<Factura> {

	@SuppressWarnings("unchecked")
	public List<Factura> listarDiaValido(LocalDate dt1, Long empresa) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery(
					"select f from Factura f inner join fetch f.venda v inner join fetch v.funcionario inner join fetch "
							+ "v.cliente c Where f.dataFacturacao = :data AND f.chaveEmpresa = :empresa order by f.codigo desc");
			consulta.setParameter("data", dt1);
			consulta.setParameter("empresa", empresa);
			List<Factura> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Factura> listarEntreDiaValido(LocalDate dt1, LocalDate dt2, Long empresa) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery(
					"select f from Factura f inner join fetch f.venda v inner join fetch v.funcionario inner join fetch v.cliente "
							+ "c Where f.dataFacturacao BETWEEN :dataI AND :dataF AND f.chaveEmpresa = :empresa order by f.codigo desc");
			consulta.setParameter("dataI", dt1);
			consulta.setParameter("dataF", dt2);
			consulta.setParameter("empresa", empresa);

			List<Factura> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	public NotaDeCredito validar(Factura factura, List<ItemsVenda> itemsVendas, NotaDeCredito notaDeCredito) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();

			for (int i = 0; i < itemsVendas.size(); i++) {
				ItemsVenda itemVenda = itemsVendas.get(i);
				itemVenda.setEstado(false);
				itemVenda.setValorParcialCancelado(
						itemVenda.getValorUnitario().multiply(new BigDecimal(itemVenda.getQuantidadeCancelada())));

				sessao.update(itemVenda);

				Produto produto = itemVenda.getProduto();
				int quantidade = produto.getQuantidade() + itemVenda.getQuantidadeCancelada();
				produto.setQuantidade(new Short(quantidade + ""));
				sessao.update(produto);

			}
			sessao.update(factura);

			notaDeCredito.setFactura(factura);
			sessao.save(notaDeCredito);
			transacao.commit();
			return notaDeCredito;

		} catch (RuntimeException e) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw e;
		} finally {
			sessao.close();
		}

	}

	public NotaDeDebido validar(Factura factura, List<ItemsVenda> itemsVendas, List<ItemsDebido> itemsDebidos,
			NotaDeDebido notaDeDebido) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();

			for (int i = 0; i < itemsVendas.size(); i++) {
				ItemsVenda itemVenda = itemsVendas.get(i);
				Produto produto = itemVenda.getProduto();
				int quantidade = produto.getQuantidade() + itemVenda.getQuantidadeCancelada();
				produto.setQuantidade(new Short(quantidade + ""));
				sessao.update(produto);
			}

			sessao.update(factura);

			notaDeDebido.setFactura(factura);
			sessao.save(notaDeDebido);
			for (int i = 0; i < itemsDebidos.size(); i++) {
				ItemsDebido itemsDebido = itemsDebidos.get(i);
				itemsDebido.setNotaDeDebido(notaDeDebido);
				sessao.save(itemsDebido);
			}
			transacao.commit();
			return notaDeDebido;

		} catch (RuntimeException e) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw e;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<NotaDeCredito> listarPorFactura(Factura factura) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery(
					"select nc from NotaDeCredito nc join fetch nc.factura f Where nc.factura = :factura order by nc.codigo desc");
			consulta.setParameter("factura", factura);

			List<NotaDeCredito> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<NotaDeDebido> listarPorFacturaDebido(Factura factura) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery(
					"select nd from NotaDeDebido nd join fetch nd.factura f Where nd.factura = :factura order by nd.codigo desc");
			consulta.setParameter("factura", factura);

			List<NotaDeDebido> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Recibo> listarPorFacturaRecibo(Factura factura) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery(
					"select r from Recibo r join fetch r.factura f Where r.factura = :factura order by r.codigo desc");
			consulta.setParameter("factura", factura);

			List<Recibo> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	public Factura buscarPorUltimo(Long chave) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery("select ft from Factura ft Where ft.chaveEmpresa = :chave AND "
					+ "ft.codigo =(select max(f.codigo) from Factura f Where f.chaveEmpresa=:chave)");
			consulta.setParameter("chave", chave);

			Factura resultado = (Factura) consulta.uniqueResult();

			System.out.println("resultado " + resultado);
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	public Recibo buscarPorUltimoRecibo(Long chave) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery("select r from Recibo r Where r.chaveEmpresa = :chave AND "
					+ "r.codigo =(select max(rc.codigo) from Recibo rc Where rc.chaveEmpresa=:chave)");
			consulta.setParameter("chave", chave);

			Recibo resultado = (Recibo) consulta.uniqueResult();

			System.out.println("resultado " + resultado);
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	public Long buscarPorUltimoNC(Long chave) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery(
					"select n from NotaDeCredito n inner join fetch n.factura f Where f.chaveEmpresa = :chave");
			consulta.setParameter("chave", chave);

			Long resultado = (Long) consulta.uniqueResult();

			System.out.println("resultado " + resultado);
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

}

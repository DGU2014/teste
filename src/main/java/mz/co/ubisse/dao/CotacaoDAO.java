package mz.co.ubisse.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import mz.co.ubisse.domain.Cotacao;
import mz.co.ubisse.domain.Factura;
import mz.co.ubisse.domain.Historico;
import mz.co.ubisse.domain.ItemsVenda;
import mz.co.ubisse.domain.Venda;
import mz.co.ubisse.util.HibernateUtil;

public class CotacaoDAO extends GenericDAO<Cotacao> {

	public Cotacao buscarPorVenda(Long venda) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Cotacao.class);
			consulta.add(Restrictions.eq("venda.codigo", venda));
			Cotacao resultado = (Cotacao) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Cotacao> listarV(Long venda) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Cotacao.class);
			consulta.add(Restrictions.eq("venda.codigo", venda));
			List<Cotacao> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Cotacao> listarDia(LocalDate dia) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Query consulta = sessao.createQuery(
					"select c from cotacao c inner join fetch c.venda v inner join fetch v.funcionario inner join fetch v.cliente f inner join fetch f.pessoa p inner join fetch p.contacto  inner join fetch p.endereco e inner join fetch e.distrito Where c.data = :data");
			// "select vd from Cotacao vd inner join fetch vd.venda v
			// inner join fetch v.funcionario f Where vd.data = :data"
			consulta.setParameter("data", dia);

			List<Cotacao> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Cotacao> listarDiaValido(LocalDate dia, Long empresa) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao
					.createQuery("select vd from Cotacao vd inner join fetch vd.venda v inner join fetch v.funcionario"
							+ " inner join fetch v.cliente c Where vd.data = :data AND vd.chaveEmpresa = :empresa order by vd.codigo desc");
			consulta.setParameter("data", dia);
			consulta.setParameter("empresa", empresa);

			List<Cotacao> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Cotacao> listarEntreDiaValido(LocalDate dataInicio, LocalDate dataFim, Long empresa) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery(
					"select vd from Cotacao vd inner join fetch vd.venda v inner join fetch v.funcionario inner join fetch"
							+ " v.cliente c Where vd.data BETWEEN :dataI AND :dataF AND vd.chaveEmpresa = :empresa order by vd.codigo desc");
			consulta.setParameter("dataI", dataInicio);
			consulta.setParameter("dataF", dataFim);
			consulta.setParameter("empresa", empresa);

			List<Cotacao> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Cotacao> ListaEntreDatas(LocalDate dataInicio, LocalDate dataFim) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Cotacao.class);
			consulta.add(Restrictions.ge("data", dataInicio));
			consulta.add(Restrictions.le("data", dataFim));
			List<Cotacao> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	public Venda readAll(Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Query query = sessao
				.createQuery("select v from Venda v inner join fetch v.itemsVendas where v.codigo = :codigo");
		query.setParameter("codigo", codigo);

		Venda result = null;
		try {
			result = (Venda) query.uniqueResult();
		} catch (NoResultException e) {
			// no result found
		}

		return result;

	}

	public Integer buscarUltimo() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Cotacao.class);
			consulta.setProjection(Projections.max("codigoVD"));
			Integer resultado = (Integer) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	public Cotacao buscarPorUltimo() {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Cotacao result = null;
		try {
			Query query = sessao.createQuery("select max(v.codigoVD) from Cotacao v");

			result = (Cotacao) query.uniqueResult();
		} catch (NoResultException e) {
			// no result found
		}

		return result;
	}

	public Cotacao buscarPorUltimo(Long chave) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			System.out.println("dentro da busca" + chave);
			Cotacao resultado;

			Query consulta = sessao.createQuery("select ct from Cotacao ct Where ct.chaveEmpresa = :chave AND "
					+ "ct.codigo =(select max(c.codigo) from Cotacao c Where c.chaveEmpresa = :chave)");
			consulta.setParameter("chave", chave);
			resultado = (Cotacao) consulta.uniqueResult();
			System.out.println("consulta " + resultado);
			if (resultado == null) {
				System.out.println("consulta 1");
				resultado = new Cotacao();
				return resultado;
			} else {
				System.out.println("consulta 2");
				return resultado;
			}

		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	public void converterEmFactura(Cotacao cotacao, Factura factura, Historico historico) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();

			sessao.save(factura);

			sessao.update(cotacao);

			historico.setHistorico(historico.getHistorico() + "Cotaçao nr" + cotacao.getCodigoCT()
					+ " convertida em  Factura numero " + factura.getCodigoFT());

			sessao.save(historico);

			transacao.commit();

		} catch (

		RuntimeException e) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw e;
		} finally {
			sessao.close();
		}

	}
}
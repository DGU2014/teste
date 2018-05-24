package mz.co.ubisse.dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import mz.co.ubisse.domain.Recibo;
import mz.co.ubisse.util.HibernateUtil;

public class ReciboDAO extends GenericDAO<Recibo> {

	public Recibo buscarPorVenda(Long venda) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Recibo.class);
			consulta.add(Restrictions.eq("venda.codigo", venda));
			Recibo resultado = (Recibo) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Recibo> listarV(Long venda) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Recibo.class);
			consulta.add(Restrictions.eq("venda.codigo", venda));
			List<Recibo> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Recibo> listarDia(LocalDate dia) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {

			Query consulta = sessao
					.createQuery("select r from Recibo r inner join fetch r.factura f Where r.data = :data ");
			consulta.setParameter("data", dia);

			List<Recibo> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	// Valido consulta = sessao.createQuery(
	// "select f from Factura f inner join fetch f.venda v inner join fetch
	// v.funcionario inner join fetch "
	// + "v.cliente c Where f.dataFacturacao = :data AND f.chaveEmpresa = :empresa
	// order by f.codigo desc");
	@SuppressWarnings("unchecked")
	public List<Recibo> listarDiaValido(LocalDate dia, Long empresa) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery(
					"select r from Recibo r inner join fetch r.factura f inner join fetch f.venda v inner join fetch "
							+ "v.cliente c WHERE r.data = :data AND r.chaveEmpresa = :empresa order by r.codigo desc");
			consulta.setParameter("data", dia);
			consulta.setParameter("empresa", empresa);

			List<Recibo> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	// Valido consulta = sessao.createQuery(
	// "select f from Factura f inner join fetch f.venda v inner join fetch
	// v.funcionario inner join fetch "
	// + "v.cliente c Where f.dataFacturacao = :data AND f.chaveEmpresa = :empresa
	// order by f.codigo desc");
	@SuppressWarnings("unchecked")
	public List<Recibo> listarEntreDiaValido(LocalDate dataInicio, LocalDate dataFim, Long empresa) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery(
					"select r from Recibo r inner join fetch r.factura f inner join fetch f.venda v inner join fetch "
							+ "v.cliente c Where r.data BETWEEN :dataI AND :dataF AND r.chaveEmpresa = :empresa order by r.codigo desc");
			consulta.setParameter("dataI", dataInicio);
			consulta.setParameter("dataF", dataFim);
			consulta.setParameter("empresa", empresa);

			List<Recibo> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Recibo> ListaEntreDatas(LocalDate dataInicio, LocalDate dataFim) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Recibo.class);
			consulta.add(Restrictions.ge("data", dataInicio));
			consulta.add(Restrictions.le("data", dataFim));
			List<Recibo> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

}
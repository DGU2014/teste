package mz.co.ubisse.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import mz.co.ubisse.domain.Venda;
import mz.co.ubisse.domain.VendaDinheiro;
import mz.co.ubisse.util.HibernateUtil;

public class VendaDinheiroDAO extends GenericDAO<VendaDinheiro> {

	public VendaDinheiro buscarPorVenda(Long venda) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(VendaDinheiro.class);
			consulta.add(Restrictions.eq("venda.codigo", venda));
			VendaDinheiro resultado = (VendaDinheiro) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<VendaDinheiro> listarV(Long venda) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(VendaDinheiro.class);
			consulta.add(Restrictions.eq("venda.codigo", venda));
			List<VendaDinheiro> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<VendaDinheiro> listarDia(LocalDate dia) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Query consulta = sessao.createQuery(
					"select vd from VendaDinheiro vd inner join fetch vd.venda v inner join fetch v.funcionario inner join fetch v.cliente f inner join fetch f.pessoa p inner join fetch p.contacto  inner join fetch "
					+ "p.endereco e inner join fetch e.distrito Where vd.data = :data order by vd.codigo desc");
			// "select vd from VendaDinheiro vd inner join fetch vd.venda v
			// inner join fetch v.funcionario f Where vd.data = :data"
			consulta.setParameter("data", dia);

			List<VendaDinheiro> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<VendaDinheiro> listarDiaValido(LocalDate dataInicio, Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			System.out.println("empresa " + codigo);
			Query consulta = sessao.createQuery(
					"select vd from VendaDinheiro vd inner join fetch vd.venda v inner join fetch v.cliente inner join fetch "
							+ "v.funcionario f Where vd.data = :dataI AND vd.chaveEmpresa = :empresa order by vd.codigo desc");
			consulta.setParameter("dataI", dataInicio);
			consulta.setParameter("empresa", codigo);

			List<VendaDinheiro> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<VendaDinheiro> listarEntreDiaValido(LocalDate dataInicio, LocalDate dataFim, Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery(
					"select vd from VendaDinheiro vd inner join fetch vd.venda v inner join fetch v.cliente inner join fetch "
							+ "v.funcionario f Where vd.data BETWEEN :dataI AND :dataF AND vd.chaveEmpresa =:empresa order by vd.codigo desc");
			consulta.setParameter("dataI", dataInicio);
			consulta.setParameter("dataF", dataFim);
			consulta.setParameter("empresa", codigo);

			List<VendaDinheiro> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<VendaDinheiro> ListaEntreDatas(LocalDate dataInicio, LocalDate dataFim) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(VendaDinheiro.class);
			consulta.add(Restrictions.ge("data", dataInicio));
			consulta.add(Restrictions.le("data", dataFim));
			List<VendaDinheiro> resultado = consulta.list();
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
				.createQuery("select v from Venda v inner join fetch v.itemsVendas where v.codigo = :codigo ");
		query.setParameter("codigo", codigo);

		Venda result = null;
		try {
			result = (Venda) query.uniqueResult();
		} catch (NoResultException e) {
			// no result found
		}

		return result;

	}

	public VendaDinheiro buscarUltimo() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = HibernateUtil.getSession().createCriteria(VendaDinheiro.class);
			consulta.addOrder(Order.desc("codigo"));
			consulta.createAlias("Venda", "v");
			VendaDinheiro resultado = (VendaDinheiro) consulta.list().get(0);
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	public VendaDinheiro buscarPorUltimo(Long chave) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery("select vd from VendaDinheiro vd Where vd.chaveEmpresa = :chave AND "
					+ "vd.codigo =(select max(v.codigo) from VendaDinheiro v Where v.chaveEmpresa=:chave)");
			consulta.setParameter("chave", chave);

			VendaDinheiro resultado = (VendaDinheiro) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}
}
package mz.co.ubisse.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import mz.co.ubisse.domain.Historico;
import mz.co.ubisse.domain.Produto;
import mz.co.ubisse.util.HibernateUtil;

public class ProdutoDAO extends GenericDAO<Produto> {

	public Produto buscarPorProduto(Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Produto.class);
			consulta.add(Restrictions.eq("codigo", codigo));

			Produto resultado = (Produto) consulta.uniqueResult();

			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Produto> listarActivos() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Produto.class);
			consulta.add(Restrictions.eq("estado", true));
			List<Produto> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Produto> listarActivos(String campoOrdenacao, Long chave) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery(
					"select p from Produto p inner join fetch p.categoria c Where p.estado = :estado AND "
					+ "p.chaveEmpresa = :chave order by c.nome asc");
			consulta.setParameter("estado", true);
			consulta.setParameter("chave", chave);
			List<Produto> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Produto> listarEstoqueNegativo() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query consulta = sessao
					// .createQuery("select from Produto p Where p.estado = :estado And
					// p.quantidade<=p.estoqueMinimo");
					.createQuery(
							"select p from Produto p inner join fetch p.categoria c Where "
							+ "p.estado = :estado And p.quantidade<=p.estoqueMinimo");
			consulta.setParameter("estado", true);

			List<Produto> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Produto> listarEstoqueNegativoArmazem() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query consulta = sessao.createQuery(
					"select p from Produto p inner join fetch p.categoria c Where p.estado = :estado And "
					+ "p.quantidadeArmazenada<=p.estoqueMinimoArmazem ");
			consulta.setParameter("estado", true);

			List<Produto> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Produto> listarEstoqueNegativo(Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query consulta = sessao
					// .createQuery("select from Produto p Where p.estado = :estado And
					// p.quantidade<=p.estoqueMinimo");
					.createQuery("select p from Produto p inner join fetch p.categoria inner join fetch "
							+ "p.empresa e Where p.estado = :estado And p.quantidade<=p.estoqueMinimo And e.codigo = :codigo");
			consulta.setParameter("estado", true);
			consulta.setParameter("codigo", codigo);

			List<Produto> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Produto> listarEstoqueNegativoArmazem(Long empresa) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query consulta = sessao.createQuery("select p from Produto p inner join fetch p.categoria inner join fetch "
					+ "p.empresa e Where p.estado = :estado And p.quantidadeArmazenada<=p.estoqueMinimoArmazem And e.codigo = :empresa");
			consulta.setParameter("estado", true);
			consulta.setParameter("empresa", empresa);

			List<Produto> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	public Produto merge(Produto produto, Historico historico) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();

			sessao.save(historico);
			Produto retorno = (Produto) sessao.merge(produto);
			transacao.commit();
			return retorno;
		} catch (RuntimeException e) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw e;
		} finally {
			sessao.close();
		}

	}
}

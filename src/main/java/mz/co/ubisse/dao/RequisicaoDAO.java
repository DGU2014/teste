package mz.co.ubisse.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import mz.co.ubisse.domain.Historico;
import mz.co.ubisse.domain.ItemsRequisicao;
import mz.co.ubisse.domain.Produto;
import mz.co.ubisse.domain.RequisicaoProduto;
import mz.co.ubisse.util.HibernateUtil;

public class RequisicaoDAO extends GenericDAO<RequisicaoProduto> {

	public void salvar(RequisicaoProduto requisicaoProduto, List<ItemsRequisicao> itemsRequisicaos) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			sessao.save(requisicaoProduto);
			for (int i = 0; i < itemsRequisicaos.size(); i++) {
				ItemsRequisicao itemsRequisicao = itemsRequisicaos.get(i);

				itemsRequisicao.setRequisicaoProduto(requisicaoProduto);

				sessao.save(itemsRequisicao);

				Produto produto = itemsRequisicao.getProduto();
				produto.setQuantidadeArmazenada(
						new Short(produto.getQuantidadeArmazenada() + itemsRequisicao.getQuantidade() + ""));
				sessao.update(produto);
			}

			transacao.commit();
		} catch (RuntimeException e) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw e;
		} finally {
			sessao.close();
		}

	}

	public void salvar(RequisicaoProduto requisicaoProduto, List<ItemsRequisicao> itemsRequisicaos,
			Historico historico) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			sessao.save(requisicaoProduto);
			sessao.save(historico);
			for (int i = 0; i < itemsRequisicaos.size(); i++) {
				ItemsRequisicao itemsRequisicao = itemsRequisicaos.get(i);

				itemsRequisicao.setRequisicaoProduto(requisicaoProduto);

				sessao.save(itemsRequisicao);

				Produto produto = itemsRequisicao.getProduto();
				produto.setQuantidadeArmazenada(
						new Short(produto.getQuantidadeArmazenada() + itemsRequisicao.getQuantidade() + ""));
				sessao.update(produto);
			}

			transacao.commit();
		} catch (RuntimeException e) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw e;
		} finally {
			sessao.close();
		}

	}

	public RequisicaoProduto readAll(Long codigo) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Query query = sessao
				.createQuery("select v from RequisicaoProduto v join fetch v.itemsRequisicao where v.codigo = :codigo");
		query.setParameter("codigo", codigo);

		RequisicaoProduto result = null;
		try {
			result = (RequisicaoProduto) query.uniqueResult();
		} catch (NoResultException e) {
			// no result found
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	public List<RequisicaoProduto> ListaEntreDatas(Date dataInicio, Date dataFim) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(RequisicaoProduto.class);
			consulta.add(Restrictions.ge("dataRequisicao", dataInicio));
			consulta.add(Restrictions.le("dataRequisicao", dataFim));
			List<RequisicaoProduto> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<RequisicaoProduto> listarDiaValido(LocalDate dia) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery(
					"select r from RequisicaoProduto r inner join fetch r.fornecedor inner join fetch r.funcionario f Where r.data = :data");
			consulta.setParameter("data", dia);

			List<RequisicaoProduto> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<RequisicaoProduto> listarEntreDiaValido(LocalDate dataInicio, LocalDate dataFim) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery(
					"select r from RequisicaoProduto  r inner join fetch r.fornecedor inner join fetch r.funcionario f Where r.data BETWEEN :dataI AND :dataF");
			consulta.setParameter("dataI", dataInicio);
			consulta.setParameter("dataF", dataFim);

			List<RequisicaoProduto> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<ItemsRequisicao> carregarItem(Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query query = sessao.createQuery(
					"select i from ItemsRequisicao i join fetch i.produto join fetch i.requisicaoProduto r where r.codigo = :codigo");
			query.setParameter("codigo", codigo);
			List<ItemsRequisicao> result = query.list();
			return result;
		} catch (NoResultException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

}

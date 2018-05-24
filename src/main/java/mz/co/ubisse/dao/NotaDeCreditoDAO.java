package mz.co.ubisse.dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import mz.co.ubisse.domain.Factura;
import mz.co.ubisse.domain.NotaDeCredito;
import mz.co.ubisse.util.HibernateUtil;

public class NotaDeCreditoDAO extends GenericDAO<NotaDeCredito> {

	public NotaDeCredito validar(NotaDeCredito notaDeCredito, Factura factura) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();

			sessao.update(factura);
			sessao.merge(notaDeCredito);

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
	
	@SuppressWarnings("unchecked")
	public List<NotaDeCredito> listarDiaValido(LocalDate dia) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao
					.createQuery("select nc from NotaDeCredito nc inner join fetch nc.factura f Where nc.dataDeEmissao = :data");
			consulta.setParameter("data", dia);

			List<NotaDeCredito> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<NotaDeCredito> listarEntreDiaValido(LocalDate dataInicio, LocalDate dataFim) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery(
					"select nc from NotaDeCredito nc inner join fetch nc.factura f Where nc.dataDeEmissao BETWEEN :dataI AND :dataF");
			consulta.setParameter("dataI", dataInicio);
			consulta.setParameter("dataF", dataFim);

			List<NotaDeCredito> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

}

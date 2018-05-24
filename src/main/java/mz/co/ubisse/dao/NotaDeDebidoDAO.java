package mz.co.ubisse.dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import mz.co.ubisse.domain.NotaDeDebido;
import mz.co.ubisse.util.HibernateUtil;

public class NotaDeDebidoDAO extends GenericDAO<NotaDeDebido> {

	@SuppressWarnings("unchecked")
	public List<NotaDeDebido> listarDiaValido(LocalDate dia) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao
					.createQuery("select nd from NotaDeDebido nd inner join fetch nd.factura f Where nd.dataDeEmissao = :data");
			consulta.setParameter("data", dia);

			List<NotaDeDebido> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<NotaDeDebido> listarEntreDiaValido(LocalDate dataInicio, LocalDate dataFim) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query consulta = sessao.createQuery(
					"select nd from NotaDeDebido nd inner join fetch nd.factura f Where nd.dataDeEmissao BETWEEN :dataI AND :dataF");
			consulta.setParameter("dataI", dataInicio);
			consulta.setParameter("dataF", dataFim);

			List<NotaDeDebido> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

}

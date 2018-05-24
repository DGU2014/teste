package mz.co.ubisse.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import mz.co.ubisse.domain.Empresa;
import mz.co.ubisse.util.HibernateUtil;

public class EmpresaDAO extends GenericDAO<Empresa> {

	public Empresa salva(Empresa empresa) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			Empresa retorno = (Empresa) sessao.save(empresa);
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

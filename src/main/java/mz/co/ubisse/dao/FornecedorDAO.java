package mz.co.ubisse.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import mz.co.ubisse.domain.Fornecedor;
import mz.co.ubisse.domain.Pessoa;
import mz.co.ubisse.util.HibernateUtil;

public class FornecedorDAO extends GenericDAO<Fornecedor> {
	@SuppressWarnings("unchecked")
	public List<Fornecedor> listarFornecedores() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query consulta = sessao
					.createQuery("select f from Fornecedor f inner join fetch f.pessoa p where f.estado = :estado");
			consulta.setParameter("estado", true);
			List<Fornecedor> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	public void save(Pessoa pessoa, Fornecedor fornecedor) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();

			if (!(pessoa.getCodigo() == null)) {
				sessao.update(pessoa);
			} else {
				sessao.save(pessoa);
			}
			if (!(fornecedor.getCodigo() == null)) {
				sessao.update(fornecedor);
			} else {
				fornecedor.setPessoa(pessoa);
				sessao.save(fornecedor);
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

}

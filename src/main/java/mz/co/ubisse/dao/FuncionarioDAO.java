package mz.co.ubisse.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import mz.co.ubisse.domain.Funcionario;
import mz.co.ubisse.domain.Pessoa;
import mz.co.ubisse.util.HibernateUtil;

public class FuncionarioDAO extends GenericDAO<Funcionario> {

	@SuppressWarnings("unchecked")
	public List<Funcionario> listarFuncionario() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query consulta = sessao
					.createQuery("select f from Funcionario f inner join fetch f.pessoa p where f.estado = :estado");
			consulta.setParameter("estado", true);

			List<Funcionario> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	public void save(Pessoa pessoa, Funcionario funcionario) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();

			if (!(pessoa.getCodigo() == null)) {
				sessao.update(pessoa);
			} else {
				sessao.save(pessoa);
			}
			if (!(funcionario.getCodigo() == null)) {
				sessao.update(funcionario);
			} else {
				funcionario.setPessoa(pessoa);
				sessao.save(funcionario);
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

	@SuppressWarnings("unchecked")
	public List<Funcionario> listarFuncionario(Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query consulta = sessao.createQuery(
					"select f from Funcionario f inner join fetch f.pessoa p inner join fetch "
					+ "p.empresa e  where f.estado = :estado And e.codigo = :codigo");
			consulta.setParameter("estado", true);
			consulta.setParameter("codigo", codigo);
			List<Funcionario> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}
}

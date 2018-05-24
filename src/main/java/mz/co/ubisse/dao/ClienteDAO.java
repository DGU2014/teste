package mz.co.ubisse.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import mz.co.ubisse.domain.Cliente;
import mz.co.ubisse.domain.Pessoa;
import mz.co.ubisse.util.HibernateUtil;

public class ClienteDAO extends GenericDAO<Cliente> {

	@SuppressWarnings("unchecked")
	public List<Cliente> listarClientes(Long chave) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query consulta = sessao.createQuery(
					"select c from Cliente c inner join fetch c.pessoa p inner join fetch p.empresa e where c.estado = :estado AND e.codigo = :chave");
			consulta.setParameter("estado", true);
			consulta.setParameter("chave", chave);
			List<Cliente> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	public Cliente salvarClienteVenda(Cliente cliente, Pessoa pessoa) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();

			sessao.save(pessoa);

			cliente.setPessoa(pessoa);
			Cliente retorno = (Cliente) sessao.merge(cliente);
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

	public void save(Pessoa pessoa, Cliente cliente) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();

			if (!(pessoa.getCodigo() == null)) {
				sessao.update(pessoa);
			} else {
				sessao.save(pessoa);
			}
			if (!(cliente.getCodigo() == null)) {
				sessao.update(cliente);
			} else {
				cliente.setPessoa(pessoa);
				sessao.save(cliente);
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
	public List<Cliente> listar(Long chave) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query consulta = sessao.createQuery(
					"select c from Cliente c inner join fetch c.pessoa p inner join fetch p.empresa e Where e.codigo = :chave");
			consulta.setParameter("chave", chave);
			List<Cliente> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

}

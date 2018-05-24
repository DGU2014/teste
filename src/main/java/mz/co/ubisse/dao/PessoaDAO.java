package mz.co.ubisse.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import mz.co.ubisse.domain.Cliente;
import mz.co.ubisse.domain.Fornecedor;
import mz.co.ubisse.domain.Funcionario;
import mz.co.ubisse.domain.Pessoa;
import mz.co.ubisse.util.HibernateUtil;

public class PessoaDAO extends GenericDAO<Pessoa> {

	public Pessoa buscarCliente(Cliente cliente) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Pessoa.class);
			consulta.add(Restrictions.idEq(cliente.getPessoa().getCodigo()));
			Pessoa resultado = (Pessoa) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	public Pessoa buscarFornecedor(Fornecedor fornecedor) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Pessoa.class);
			consulta.add(Restrictions.idEq(fornecedor.getPessoa().getCodigo()));
			Pessoa resultado = (Pessoa) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	public Pessoa buscarFuncionario(Funcionario funcionario) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Pessoa.class);
			consulta.add(Restrictions.idEq(funcionario.getPessoa().getCodigo()));
			Pessoa resultado = (Pessoa) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

}
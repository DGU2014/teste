package mz.co.ubisse.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import mz.co.ubisse.domain.Categoria;
import mz.co.ubisse.util.HibernateUtil;

public class CategoriaDAO extends GenericDAO<Categoria> {

	@SuppressWarnings("unchecked")
	public List<Categoria> listarPorEmpresa(Long chave) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Categoria.class);
			consulta.add(Restrictions.eq("chaveEmpresa", chave));
			List<Categoria> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}
}

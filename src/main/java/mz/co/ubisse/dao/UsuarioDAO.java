package mz.co.ubisse.dao;

import java.util.List;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import mz.co.ubisse.domain.Funcionario;
import mz.co.ubisse.domain.Usuario;
import mz.co.ubisse.util.HibernateUtil;

public class UsuarioDAO extends GenericDAO<Usuario> {
	public Usuario autenticar(String nome, String senha) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Usuario.class);

			consulta.add(Restrictions.eq("usuario", nome));

			SimpleHash hash = new SimpleHash("md5", senha);
			consulta.add(Restrictions.eq("senha", hash.toHex()));

			Usuario resultado = (Usuario) consulta.uniqueResult();

			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}

	public List<Usuario> listarUsuarios(Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query consulta = sessao.createQuery(
					"select u from Usuario u inner join fetch u.funcionario f inner join fetch f.pessoa p inner join fetch "
							+ "p.empresa e  where u.estado = :estado And e.codigo = :codigo");
			consulta.setParameter("estado", true);
			consulta.setParameter("codigo", codigo);
			List<Usuario> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}
	}
}

package mz.co.ubisse.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import mz.co.ubisse.domain.Cotacao;
import mz.co.ubisse.domain.Factura;
import mz.co.ubisse.domain.GuiaDeRemessa;
import mz.co.ubisse.domain.Historico;
import mz.co.ubisse.domain.ItemsVenda;
import mz.co.ubisse.domain.Produto;
import mz.co.ubisse.domain.Servico;
import mz.co.ubisse.domain.Venda;
import mz.co.ubisse.domain.VendaDinheiro;
import mz.co.ubisse.util.HibernateUtil;

public class VendaDAO extends GenericDAO<Venda> {

	public static String mensagem = null;

	public VendaDinheiro save(Venda venda, List<ItemsVenda> itemVendas, VendaDinheiro vendaDinheiro,
			Historico historico) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {

			transacao = sessao.beginTransaction();

			sessao.save(venda);
			for (int i = 0; i < itemVendas.size(); i++) {
				ItemsVenda itemVenda = itemVendas.get(i);
				itemVenda.setVenda(venda);
				itemVenda.setEstado(true);

				sessao.save(itemVenda);

				Produto produto = itemVenda.getProduto();
				if (produto.getEstado() == true) {

					int quantidade = produto.getQuantidade() - itemVenda.getQuantidade();
					if (quantidade >= 0) {
						produto.setQuantidade(new Short(quantidade + ""));
					} else {
						mensagem = "Quantidade do produto " + produto.getNome() + " referencia "
								+ produto.getReferencia() + " insuficiente no Estoque";
						throw new RuntimeException("Quantidade do produto " + produto.getNome() + " referencia "
								+ produto.getReferencia() + " insuficiente no Estoque");
					}
					System.out.println("qtd " + produto.getQuantidade());
					System.out.println("mensagem 1" + mensagem);
					sessao.update(produto);
				}

			}
			vendaDinheiro.setData(LocalDate.now());
			vendaDinheiro.setVenda(venda);
			sessao.save(vendaDinheiro);

			historico.setHistorico(
					historico.getHistorico() + " efectou uma venda a dinheiro numero " + vendaDinheiro.getCodigo());
			sessao.save(historico);

			transacao.commit();
			return vendaDinheiro;

		} catch (RuntimeException e) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw e;
		} finally {
			sessao.close();
		}

	}

	public Factura saveFactura(Venda venda, List<ItemsVenda> itemsVendas, Factura factura, Historico historico) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();

			sessao.save(venda);
			for (int i = 0; i < itemsVendas.size(); i++) {
				ItemsVenda itemVenda = itemsVendas.get(i);
				itemVenda.setVenda(venda);
				itemVenda.setEstado(true);

				sessao.save(itemVenda);

			}

			factura.setVenda(venda);
			sessao.save(factura);

			historico.setHistorico(historico.getHistorico() + " emitiu a  Factura numero " + factura.getCodigo());
			sessao.save(historico);

			transacao.commit();
			return factura;

		} catch (

		RuntimeException e) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw e;
		} finally {
			sessao.close();
		}
	}

	public Cotacao saveCotacao(Venda venda, List<ItemsVenda> itemsVendas, Cotacao cotacao, Historico historico) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();

			sessao.save(venda);
			for (int i = 0; i < itemsVendas.size(); i++) {
				ItemsVenda itemVenda = itemsVendas.get(i);
				itemVenda.setVenda(venda);
				itemVenda.setEstado(true);

				sessao.save(itemVenda);

			}
			cotacao.setVenda(venda);
			cotacao.setDataValidade(new Date());
			sessao.save(cotacao);

			historico.setHistorico(historico.getHistorico() + " emitiu a Cotação  numero " + cotacao.getCodigo());
			sessao.save(historico);
			transacao.commit();
			return cotacao;

		} catch (RuntimeException e) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw e;
		} finally {
			sessao.close();
		}

	}

	public void saveCotacaoServico(Venda venda, List<ItemsVenda> itemsVendas, Cotacao cotacao, List<Servico> servicos,
			Historico historico) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();

			sessao.save(venda);
			for (int i = 0; i < itemsVendas.size(); i++) {
				ItemsVenda itemVenda = itemsVendas.get(i);
				itemVenda.setVenda(venda);
				itemVenda.setEstado(true);

				sessao.save(itemVenda);

			}
			cotacao.setVenda(venda);
			cotacao.setDataValidade(new Date());
			sessao.save(cotacao);
			for (int j = 0; j < servicos.size(); j++) {
				Servico servico = servicos.get(j);
				servico.setVenda(venda);
				sessao.save(servico);
			}
			historico.setHistorico(historico.getHistorico() + " emitiu a Cotação  numero " + cotacao.getCodigo());
			sessao.save(historico);

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

	public void saveFacturaServico(Venda venda, List<ItemsVenda> itemsVendas, Factura factura, List<Servico> servicos,
			Historico historico) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();

			sessao.save(venda);
			for (int i = 0; i < itemsVendas.size(); i++) {
				ItemsVenda itemVenda = itemsVendas.get(i);
				itemVenda.setVenda(venda);
				sessao.save(itemVenda);
			}

			factura.setVenda(venda);
			sessao.save(factura);
			for (int j = 0; j < servicos.size(); j++) {
				Servico servico = servicos.get(j);
				servico.setVenda(venda);
				sessao.save(servico);
			}
			historico.setHistorico(historico.getHistorico() + " emitiu a  Factura numero " + factura.getCodigo());
			sessao.save(historico);
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

	public void saveVdServico(Venda venda, List<ItemsVenda> itemsVendas, VendaDinheiro vendaDinheiro,
			List<Servico> servicos, Historico historico) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();

			sessao.save(venda);
			for (int i = 0; i < itemsVendas.size(); i++) {
				ItemsVenda itemVenda = itemsVendas.get(i);
				itemVenda.setVenda(venda);
				itemVenda.setEstado(true);

				sessao.save(itemVenda);

			}
			vendaDinheiro.setVenda(venda);
			sessao.save(vendaDinheiro);
			for (int j = 0; j < servicos.size(); j++) {
				Servico servico = servicos.get(j);
				servico.setVenda(venda);
				sessao.save(servico);
			}
			historico.setHistorico(
					historico.getHistorico() + " efectou uma venda a dinheiro numero " + vendaDinheiro.getCodigo());
			sessao.save(historico);

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

	@SuppressWarnings("rawtypes")
	public List buscarHorario(Long funcionario) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query query = sessao
					.createQuery("select MAX(v.codigo) from Venda v Where v.funcionario.codigo = :funcionario");
			query.setParameter("funcionario", funcionario);

			List lista = query.list();
			return lista;

		} catch (RuntimeException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	public void saveCotacao(Venda venda, List<ItemsVenda> itemsVendas, GuiaDeRemessa guiaDeRemessa,
			Historico historico) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();

			sessao.save(venda);
			for (int i = 0; i < itemsVendas.size(); i++) {
				ItemsVenda itemVenda = itemsVendas.get(i);
				itemVenda.setVenda(venda);
				itemVenda.setEstado(true);

				sessao.save(itemVenda);

			}

			sessao.save(guiaDeRemessa);
			historico.setHistorico(
					historico.getHistorico() + " emitiu a  Guia de entrega numero " + guiaDeRemessa.getCodigo());
			sessao.save(historico);
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

	public Venda readAll(Long codigo) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Venda result = null;

		try {

			Query query = sessao.createQuery("select v from Venda v join fetch v.itemsVendas where v.codigo = :codigo");
			query.setParameter("codigo", codigo);

			result = (Venda) query.uniqueResult();

			// sessao.close();
		} catch (NoResultException e) {
			// sessao.close();
		}

		return result;

	}

	public Venda readAll(Long codigo, List<Long> controlo) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Venda result = null;

		if (controlo.contains(codigo) != true) {
			try {

				Query query = sessao
						.createQuery("select v from Venda v join fetch v.itemsVendas where v.codigo = :codigo");
				query.setParameter("codigo", codigo);

				result = (Venda) query.uniqueResult();

				// sessao.close();
			} catch (NoResultException e) {
				// sessao.close();
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<ItemsVenda> carregarItem(Long codigo) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Query query = sessao.createQuery(
					"select v from ItemsVenda v join fetch v.produto join fetch v.venda v2 where v2.codigo = :codigo");
			query.setParameter("codigo", codigo);
			List<ItemsVenda> result = query.list();
			return result;
		} catch (NoResultException e) {
			throw e;
		} finally {
			sessao.close();
		}

	}

	public void save(Venda venda, List<ItemsVenda> itemsVendas, Historico historico) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();

			sessao.save(venda);
			for (int i = 0; i < itemsVendas.size(); i++) {
				ItemsVenda itemVenda = itemsVendas.get(i);
				itemVenda.setVenda(venda);
				itemVenda.setEstado(true);

				sessao.save(itemVenda);
				Produto produto = itemVenda.getProduto();
				int quantidade = produto.getQuantidade() - itemVenda.getQuantidade();
				if (quantidade >= 0) {
					produto.setQuantidade(new Short(quantidade + ""));
				} else {
					mensagem = "Quantidade do produto " + produto.getNome() + " referencia " + produto.getReferencia()
							+ " insuficiente no Estoque";
					throw new RuntimeException("Quantidade do produto " + produto.getNome() + " referencia "
							+ produto.getReferencia() + " insuficiente no Estoque");
				}
				System.out.println("qtd " + produto.getQuantidade());
				sessao.update(produto);
			}

			historico.setHistorico(historico.getHistorico() + " efectou uma venda");
			sessao.save(historico);

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
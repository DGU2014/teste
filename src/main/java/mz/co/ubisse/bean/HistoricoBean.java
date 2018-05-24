package mz.co.ubisse.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import mz.co.ubisse.dao.HistoricoDAO;
import mz.co.ubisse.domain.Historico;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class HistoricoBean implements Serializable {

	private List<Historico> historicos;

	public List<Historico> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<Historico> historicos) {
		this.historicos = historicos;
	}

	@PostConstruct
	public void listar() {
		try {
			HistoricoDAO historicoDAO = new HistoricoDAO();
			historicos = historicoDAO.listar("codigo");
			System.out.println("Historico Listado com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao Listar o Historico");
			erro.printStackTrace();
		}
	}
}

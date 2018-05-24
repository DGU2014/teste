package mz.co.ubisse.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import mz.co.ubisse.bean.AutenticacaoBean;
import mz.co.ubisse.domain.Usuario;

@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		String paginaAtual = Faces.getViewId();

		boolean ehPaginaDeAutenticacao = paginaAtual.contains("login.xhtml");

		if (!ehPaginaDeAutenticacao) {
			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
			if (autenticacaoBean == null) {
				Faces.navigate("login.xhtml");
				return;
			}
			Usuario usuario = autenticacaoBean.getUsuarioLogado();
			if (usuario == null) {
				Faces.navigate("login.xhtml");
				return;
			}
		}

	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}

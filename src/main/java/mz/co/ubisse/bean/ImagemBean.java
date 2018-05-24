package mz.co.ubisse.bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@RequestScoped
public class ImagemBean {

	@ManagedProperty("#{param.logo}")
	private String caminho;

	private StreamedContent foto;

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public StreamedContent getFoto() throws IOException {
		// String pastaRaiz = new File("/resources/images").getCanonicalPath();
		// File directorioRaiz = new File(pastaRaiz + "/" + "ubiLogo/branco.png");

		
		if (caminho == null || caminho.isEmpty()) {
			Path path = Paths.get(FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/images/ubiLogo/branco.png"));

			System.out.println("caminho 1 " + path);
			InputStream stream = Files.newInputStream(path);
			foto = new DefaultStreamedContent(stream);
		} else {
			Path path = Paths.get(caminho);
			InputStream stream = Files.newInputStream(path);
			foto = new DefaultStreamedContent(stream);
		}
		return foto;
	}

	public void setFoto(StreamedContent foto) {
		this.foto = foto;
	}
}

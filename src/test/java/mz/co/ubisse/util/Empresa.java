package mz.co.ubisse.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.junit.Ignore;
import org.junit.Test;
import org.omnifaces.util.Faces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

public class Empresa {

	@Test
	public void criarDirectorio() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		System.out.println(context);
		System.out.println(context.getApplication());
		System.out.println(context.getCurrentPhaseId());
		System.out.println(context.getELContext());
	/*	ApplicationContext ctx = new ClassPathXmlApplicationContext();
		Resource template = ctx.getResource("http://localhost/resource/path/myTemplate.txt");

		File file = File.createTempFile("testo", "txt");
		file.deleteOnExit();
		
		System.out.println(file.getPath());
		
		Resource resource = ctx.getResource("file:/resources/images/ubiLogo");
		System.out.println(resource);
		System.out.println(template);
*/
		/*
		 * FacesContext context = FacesContext.getCurrentInstance(); ServletContext sc =
		 * (ServletContext) context.getExternalContext().getContext();
		 * sc.getRealPath("/resources/images/ubiLogo");
		 * 
		 * String pastaRaiz = sc.toString();
		 * 
		 * String caminho
		 * =FacesContext.getCurrentInstance().getExternalContext().getRealPath(
		 * "/resources/images/");
		 * 
		 * // InputStream pastaRaiz
		 * =getClass().getResourceAsStream("/resources/images/ubiLogo/1.png");
		 * System.out.println(pastaRaiz);
		 */
		// Path path = Paths.get(pastaRaiz.toString());
		// System.out.println(path);
		// File directorioRaiz = new File(pastaRaiz + "/" + "ubiLogo");

	}

}

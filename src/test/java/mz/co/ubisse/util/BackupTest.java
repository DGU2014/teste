package mz.co.ubisse.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;

public class BackupTest {

	// Constantes da classe

	@SuppressWarnings("unused")
	private static String VERSION = "5.7.14";

	private static String SEPARATOR = File.separator;

	private static String MYSQL_PATH =

			"C:" + SEPARATOR +

					"wamp" + SEPARATOR + "bin" + SEPARATOR +

					"MySQL" + SEPARATOR +

					"mysql5.7.14" + SEPARATOR +

					"bin" + SEPARATOR;

	// Lista dos bancos de dados a serem "backupeados"; se desejar adicionar
	// mais,

	// basta colocar o nome separado por espaços dos outros nomes

	private static String DATABASES =

			"ubisse_ubi_v1";

	private List<String> dbList = new ArrayList<String>();

	@Test
	@Ignore
	public void gerar() {

		DateTime dateTime = new DateTime();
		System.out.println("data detetime " + dateTime.getMillis());
		String command = MYSQL_PATH + "mysqldump.exe";

		String[] databases = DATABASES.split(" ");

		for (int i = 0; i < databases.length; i++)

			dbList.add(databases[i]);

		System.out.println("Iniciando backups...\n\n");

		// Contador

		int i = 1;

		// Tempo

		long time1, time2, time;

		// Início

		time1 = System.currentTimeMillis();

		for (String dbName : dbList) {

			ProcessBuilder pb = new ProcessBuilder(

					command,

					"--user=root",

					"--password=",

					dbName,

					"--result-file=" + "C:" + SEPARATOR + "Backup" + SEPARATOR + dateTime.getMillisOfDay() + "-"
							+ dateTime.getDayOfMonth() + "-" + dateTime.getMonthOfYear() + "-" + dateTime.getYear()
							+ dbName + ".sql");

			try {

				System.out.println(

						"Backup do banco de dados (" + i + "): " + dbName + " ...");

				pb.start();

				pb = new ProcessBuilder(command,

						"--user=root",

						"--password=",

						dbName,

						"--result-file=" + "C:" + SEPARATOR + "Backup" + SEPARATOR + dateTime.getMillisOfDay() + "-"
								+ dateTime.getDayOfMonth() + "-" + dateTime.getMonthOfYear() + "-" + dateTime.getYear()
								+ dbName + ".sql");

			}

			catch (Exception e) {

				e.printStackTrace();

			}

			i++;

		}

		// Fim

		time2 = System.currentTimeMillis();

		// Tempo total da operação

		time = time2 - time1;

		// Avisa do sucesso

		System.out.println("\nBackups realizados com sucesso.\n\n");

		System.out.println("Tempo total de processamento: " + time + " ms\n");

		System.out.println("Finalizando...");

		try {

			// Paralisa por 2 segundos

			Thread.sleep(2000);
		

		}

		catch (Exception e) {
		}

		// Termina o aplicativo

		//System.exit(0);

	}
	@SuppressWarnings("unused")
	@Test
public void iniciar(){
	DateTime hora = new DateTime();
	DateTime DataCorrente = new DateTime();
	
	long tim = System.currentTimeMillis();

	boolean a= true;
	while (a=true) {
		long pegar =System.currentTimeMillis();
		
		if (pegar>tim) {
			gerar();
			tim = System.currentTimeMillis()+100000;
			System.out.println("gerado");
		}
		System.out.println("passei");
	}
	
}
}

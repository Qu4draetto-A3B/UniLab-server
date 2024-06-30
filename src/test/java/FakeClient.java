/*
 * Interdisciplinary Workshop B
 * Climate Monitoring
 * A.A. 2023-2024
 *
 * Authors:
 * - Iuri Antico, 753144, VA
 * - Beatrice Balzarini, 752257, VA
 * - Michael Bernasconi, 752259, VA
 * - Gabriele Borgia, 753262, VA
 *
 * Some rights reserved.
 * See LICENSE file for additional information.
 */

import lombok.extern.log4j.Log4j2;
import org.a3b.commons.ServicesCM;
import org.a3b.commons.magazzeno.*;
import org.a3b.serverCM.ServerCM;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;

@Log4j2
public class FakeClient {
	private static Registry reg;
	private static ServicesCM srv;

	public static void main(String[] args) {
		try {
			ServerCM.main(args);
			reg = LocateRegistry.getRegistry();
			srv = (ServicesCM) reg.lookup("CM");
			test();
		} catch (Exception e) {
			log.fatal("Error! ", e);
		}
	}

	private static void test() throws RemoteException {

		AreaGeografica ag = new AreaGeografica();
		log.debug(ag = srv.cercaAreaGeografica("Zumpano", "IT").get().getFirst());

		CentroMonitoraggio cm = new CentroMonitoraggio();
		log.debug(cm = srv.registraCentroAree(cm).get());

		ListaAree la = new ListaAree();
		la.add(ag);
		log.debug(cm = srv.alterListaAree(cm, la).get());

		Operatore op = new Operatore(0, "Iuri", "Antico", "1234567890abcdef", "test@example.org", cm);
		log.debug(op = srv.registrazione(op, "password0").get());

		log.debug(srv.login(op.getUid(), "password0"));

		Misurazione mis = new Misurazione(-1, op, ag, Misurazione.buildDati(0, 0, 0, 0, 0, 0, 0), Misurazione.buildNote("", "", "", "", "", "", ""));
		log.debug(srv.inserisciParametriClimatici(mis));

		log.debug(srv.getListaMisurazioni(-1, -1, -1, -1, null, LocalDateTime.now().plusMinutes(10)).get());
	}
}

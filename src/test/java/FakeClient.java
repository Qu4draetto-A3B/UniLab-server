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
import org.a3b.commons.magazzeno.AreaGeografica;
import org.a3b.commons.magazzeno.CentroMonitoraggio;
import org.a3b.commons.magazzeno.Operatore;
import org.a3b.serverCM.ServerCM;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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

		Operatore op = new Operatore(0, "Iuri", "Antico", "1234567890abcdef", "test@example.org", cm);
		log.debug(op = srv.registrazione(op, "password0").get());

		log.debug(srv.login(op.getUid(), "password0"));
	}
}

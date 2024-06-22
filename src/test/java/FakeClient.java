import lombok.extern.log4j.Log4j2;
import org.a3b.commons.ServicesCM;
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
			ServerCM.init();
			reg = LocateRegistry.getRegistry();
			srv = (ServicesCM) reg.lookup("CM");
			test();
		} catch (Exception e) {
			log.fatal("Error! ", e);
		}
	}

	private static void test() throws RemoteException {
		log.debug(srv.cercaAreaGeografica(54, 45));
		log.debug(srv.login(2, "password0"));
	}
}

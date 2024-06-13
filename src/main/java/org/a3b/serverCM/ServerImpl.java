package org.a3b.serverCM;

import lombok.extern.log4j.Log4j2;
import org.a3b.commons.ServicesCM;
import org.a3b.serverCM.magazzeno.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j2
public class ServerImpl extends UnicastRemoteObject implements ServicesCM {
	public ServerImpl() throws RemoteException {
		super();
	}

	@Override
	public ListaAree cercaAreaGeografica(String name, String country) throws RemoteException {
		ListaAree la = new ListaAree();
		String query = """
				SELECT *
				FROM "CoordinateMonitoraggio"
				WHERE "CountryCode" = ?;
				""";

		try (var stmt = ServerCM.db.prepareStatement(query)) {
			stmt.setString(1, country);
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				la.add(DataFactory.buildAreaGeografica(set));
			}
		} catch (SQLException e) {
			log.error("Error!", e);
		}
		return la.cercaAreeGeografiche(name, country);
	}

	@Override
	public ListaAree cercaAreaGeografica(double latitude, double longitude) throws RemoteException {
		ListaAree la = new ListaAree();
		// Incorrect: floor of -12,80 is 13 not 12
		String query = """
				SELECT *
				FROM "CoordinateMonitoraggio"
				WHERE "Latitude" = FLOOR(?) AND "Longitude" = FLOOR(?);
				""";

		try (var stmt = ServerCM.db.prepareStatement(query)) {
			stmt.setDouble(1, latitude);
			stmt.setDouble(2, longitude);
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				la.add(DataFactory.buildAreaGeografica(set));
			}
		} catch (SQLException e) {
			log.error("Error!", e);
		}
		return la.cercaAreeGeografiche(latitude, longitude);
	}

	@Override
	public AreaGeografica getAreaGeografica(int geoID) throws RemoteException {
		String query = """
				SELECT *
				FROM "CoordinateMonitoraggio"
				WHERE "GeoID" = ?;
				""";
		try (var stmt = ServerCM.db.prepareStatement(query)) {
			stmt.setInt(1, geoID);
			ResultSet set = stmt.executeQuery();
			set.next();

			return DataFactory.buildAreaGeografica(set);
		} catch (SQLException e) {
			log.error("Error!", e);
		}

		return new AreaGeografica();
	}

	//TODO
	@Override
	public Misurazione visualizzaAreaGeografica(int geoID) throws RemoteException {
		return null;
	}

	//TODO
	@Override
	public Operatore registrazione(Operatore operator, String password) throws RemoteException {
		return null;
	}

	//TODO
	@Override
	public Operatore login(int userID, String password) throws RemoteException {
		return null;
	}

	//TODO
	@Override
	public CentroMonitoraggio registraCentroAree(CentroMonitoraggio centro) throws RemoteException {
		return null;
	}

	//TODO
	@Override
	public boolean alterListaAreeCentro(CentroMonitoraggio center, ListaAree newlist) throws RemoteException {
		return false;
	}

	//TODO
	@Override
	public ListaAree getListaAree(int centerID) throws RemoteException {
		return null;
	}

	//TODO
	@Override
	public boolean inserisciParametriClimatici(Misurazione misurazione) throws RemoteException {
		return false;
	}

	//TODO
	@Override
	public Misurazione getMisurazione(int recordID) throws RemoteException {
		return null;
	}
}

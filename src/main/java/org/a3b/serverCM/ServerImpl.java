package org.a3b.serverCM;

import lombok.extern.log4j.Log4j2;
import org.a3b.commons.ServicesCM;
import org.a3b.commons.magazzeno.*;
import org.a3b.commons.result.Result;

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
	public Result<ListaAree> cercaAreaGeografica(String name, String country) throws RemoteException {
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
			return new Result<>(e);
		}
		return new Result<>(la.cercaAreeGeografiche(name, country));
	}

	@Override
	public Result<ListaAree> cercaAreaGeografica(double latitude, double longitude) throws RemoteException {
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
			return new Result<>(e);
		}
		return new Result<>(la.cercaAreeGeografiche(latitude, longitude));
	}

	@Override
	public Result<AreaGeografica> getAreaGeografica(int geoID) throws RemoteException {
		String query = """
				SELECT *
				FROM "CoordinateMonitoraggio"
				WHERE "GeoID" = ?;
				""";
		try (var stmt = ServerCM.db.prepareStatement(query)) {
			stmt.setInt(1, geoID);
			ResultSet set = stmt.executeQuery();
			set.next();

			return new Result<>(DataFactory.buildAreaGeografica(set));
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}
	}

	//TODO
	@Override
	public Result<Misurazione> visualizzaAreaGeografica(int geoID) throws RemoteException {
		return null;
	}

	//TODO
	@Override
	public Result<Operatore> registrazione(Operatore operator, String password) throws RemoteException {
		return null;
	}

	//TODO
	@Override
	public Result<Operatore> login(int userID, String password) throws RemoteException {
		return null;
	}

	//TODO
	@Override
	public Result<CentroMonitoraggio> registraCentroAree(CentroMonitoraggio centro) throws RemoteException {
		return null;
	}

	//TODO
	@Override
	public Result<Boolean> alterListaAreeCentro(CentroMonitoraggio center, ListaAree newlist) throws RemoteException {
		return null;
	}

	//TODO
	@Override
	public Result<ListaAree> getListaAree(int centerID) throws RemoteException {
		return null;
	}

	//TODO
	@Override
	public Result<Boolean> inserisciParametriClimatici(Misurazione misurazione) throws RemoteException {
		return null;
	}

	//TODO
	@Override
	public Result<Misurazione> getMisurazione(int recordID) throws RemoteException {
		return null;
	}
}

package org.a3b.serverCM;

import lombok.extern.log4j.Log4j2;
import org.a3b.commons.ServicesCM;
import org.a3b.commons.magazzeno.*;
import org.a3b.commons.result.Result;
import org.a3b.commons.result.errors.DataNotFoundException;
import org.a3b.commons.utils.TipoDatoGeografico;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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
	public Result<AreaGeografica> getAreaGeografica(long geoID) throws RemoteException {
		String query = """
				SELECT *
				FROM "CoordinateMonitoraggio"
				WHERE "GeoID" = ?;
				""";
		try (var stmt = ServerCM.db.prepareStatement(query)) {
			stmt.setLong(1, geoID);
			ResultSet set = stmt.executeQuery();
			set.next();

			return new Result<>(DataFactory.buildAreaGeografica(set));
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}
	}

	@Override
	public Result<Misurazione> visualizzaAreaGeografica(long geoID) throws RemoteException {
		String query = """
				SELECT *
				FROM "ParametriClimatici"
				WHERE "Area" = ?;
				""";
		try (var stmt = ServerCM.db.prepareStatement(query)) {
			stmt.setLong(1, geoID);
			ResultSet set = stmt.executeQuery();
			set.next();

			return new Result<>(DataFactory.buildMisurazione(set));
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}
	}

	@Override
	public Result<Operatore> registrazione(Operatore operator, String password) throws RemoteException {
		String query = """
				INSERT INTO "OpertoriRegistrati"("UserID", "Name", "Surname", "CF", "Email", "Password", "Center")
				VALUES (?, ?, ?, ?, ?, ?, ?)
				""";
		try (var stmt = ServerCM.db.prepareStatement(query)) {
			stmt.setLong(1, operator.getUid());
			stmt.setString(2, operator.getNome());
			stmt.setString(3, operator.getCognome());
			stmt.setString(4, operator.getCf());
			stmt.setString(5, operator.getEmail());
			stmt.setString(6, password);
			stmt.setLong(7, operator.getCentro().getCenterID());

			ResultSet set = stmt.executeQuery();
			set.next();

			return new Result<>(DataFactory.buildOperatore(set));
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}
	}

	@Override
	public Result<Operatore> login(long userID, String password) throws RemoteException {
		String query = """
				SELECT *
				FROM "OperatoriRegistrati"
				WHERE "UserID" = ? AND "Password" = ?;
				""";

		try (var stmt = ServerCM.db.prepareStatement(query)) {
			stmt.setLong(1, userID);
			stmt.setString(2, password);
			log.debug(stmt.toString());

			ResultSet set = stmt.executeQuery();
			set.next();

			if (set.getRow() == 0) {
				return new Result<>(new DataNotFoundException("User " + userID + " not found"));
			}

			return new Result<>(DataFactory.buildOperatore(set));
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}
	}

	@Override
	public Result<CentroMonitoraggio> registraCentroAree(CentroMonitoraggio centro) throws RemoteException {
		String query = """
				INSERT INTO "CentriMonitoraggio"("CenterID", "Name", "Street", "CivicNumber", "ZIPCode", "Town",
                                 "Province")
				VALUES (?, ?, ?, ?, ?, ?, ?)
				""";
		try (var stmt = ServerCM.db.prepareStatement(query)) {
			stmt.setLong(1, centro.getCenterID());
			stmt.setString(2, centro.getNome());
			stmt.setString(3, centro.getNomeVia());
			stmt.setInt(4, centro.getCivico());
			stmt.setInt(5, centro.getCap());
			stmt.setString(6, centro.getComune());
			stmt.setString(7, centro.getProvincia());

			ResultSet set = stmt.executeQuery();
			set.next();

			return new Result<>(DataFactory.buildCentroMonitoraggio(set));
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}
	}

	@Override
	public Result<ListaAree> getListaAree(long centerID) throws RemoteException {
		ListaAree la = new ListaAree();
		String query = """
				SELECT *
				FROM "Area_Center"
				WHERE "Center" = ?;
				""";
		try (var stmt = ServerCM.db.prepareStatement(query)) {
			stmt.setLong(1, centerID);
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				la.add(DataFactory.buildAreaGeografica(set));
			}

			return new Result<>(la);
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}
	}

	@Override
	public Result<Misurazione> inserisciParametriClimatici(Misurazione misurazione) throws RemoteException {
		String query = """
				INSERT INTO "ParametriClimatici"("RecordID", "Center", "Operator", "Area", "Datetime",
                                 "Wind", "Humidity", "Pressure", "Temperature", "Precipitation", "GlacierAltitude",
                                 "GlacierMass", "WindNotes", "HumidityNotes", "PressureNotes", "TemperatureNotes", 
                                 "PrecipitationNotes", "GlacierAltitudeNotes", "GlacierMassNotes")
				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";
		try (var stmt = ServerCM.db.prepareStatement(query)) {
			stmt.setLong(1, misurazione.getRid());
			stmt.setLong(2, misurazione.getCentro().getCenterID());
			stmt.setLong(3, misurazione.getOperatore().getUid());
			stmt.setLong(4, misurazione.getArea().getGeoID());

			stmt.setTimestamp(5, Timestamp.valueOf(misurazione.getTime()));

			stmt.setInt(6, misurazione.getDato(TipoDatoGeografico.Vento));
			stmt.setInt(7, misurazione.getDato(TipoDatoGeografico.Umidita));
			stmt.setInt(8, misurazione.getDato(TipoDatoGeografico.Pressione));
			stmt.setInt(9, misurazione.getDato(TipoDatoGeografico.Temperatura));
			stmt.setInt(10, misurazione.getDato(TipoDatoGeografico.Precipitazioni));
			stmt.setInt(11, misurazione.getDato(TipoDatoGeografico.AltitudineGhiacciai));
			stmt.setInt(12, misurazione.getDato(TipoDatoGeografico.MassaGhiacciai));

			stmt.setString(13, misurazione.getNota(TipoDatoGeografico.Vento));
			stmt.setString(14, misurazione.getNota(TipoDatoGeografico.Umidita));
			stmt.setString(15, misurazione.getNota(TipoDatoGeografico.Pressione));
			stmt.setString(16, misurazione.getNota(TipoDatoGeografico.Temperatura));
			stmt.setString(17, misurazione.getNota(TipoDatoGeografico.Precipitazioni));
			stmt.setString(18, misurazione.getNota(TipoDatoGeografico.AltitudineGhiacciai));
			stmt.setString(19, misurazione.getNota(TipoDatoGeografico.MassaGhiacciai));

			ResultSet set = stmt.executeQuery();
			set.next();

			return new Result<>(DataFactory.buildMisurazione(set));
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}
	}

	@Override
	public Result<Misurazione> getMisurazione(long recordID) throws RemoteException {
		String query = """
				SELECT *
				FROM "ParamteriClimatici"
				WHERE "RecordID" = ?;
				""";
		try (var stmt = ServerCM.db.prepareStatement(query)) {
			stmt.setLong(1, recordID);
			ResultSet set = stmt.executeQuery();
			set.next();

			return new Result<>(DataFactory.buildMisurazione(set));
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}
	}

	@Override
	public Result<CentroMonitoraggio> getCentroMonitoraggio(long centerID) throws RemoteException {
		String query = """
				SELECT *
				FROM "CentriMonitoraggio"
				WHERE "CenterID" = ?;
				""";
		try (var stmt = ServerCM.db.prepareStatement(query)) {
			stmt.setLong(1, centerID);
			ResultSet set = stmt.executeQuery();
			set.next();

			return new Result<>(DataFactory.buildCentroMonitoraggio(set));
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}
	}
}

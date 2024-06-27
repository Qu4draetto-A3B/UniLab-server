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
package org.a3b.serverCM;

import lombok.extern.log4j.Log4j2;
import org.a3b.commons.ServicesCM;
import org.a3b.commons.magazzeno.*;
import org.a3b.commons.result.Result;
import org.a3b.commons.result.ResultException;
import org.a3b.commons.result.errors.DataNotFoundException;
import org.a3b.commons.result.errors.InconsistentDataException;
import org.a3b.commons.utils.TipoDatoGeografico;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * La classe {@code ServerImpl} contiene l'implementazione di tutti i metodi utilizzati dal client
 * dichiarati nell'interfaccia {@link ServicesCM}
 */
@Log4j2
public class ServerImpl extends UnicastRemoteObject implements ServicesCM {
	public ServerImpl() throws RemoteException {
		super();
	}

	/**
	 * Metodo per la ricerca dell'area geografica in base al nome e allo stato di appartenenza
	 *
	 * @param name    nome dell'area geografica
	 * @param country stato dell'area geografica
	 * @return {@link Result<ListaAree>} lista delle aree
	 * @throws RemoteException per la gestione delle eccezioni legate alla comunicazione con il client
	 */
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

	/**
	 * Metodo per la ricerca dell'area geografica in base alla latitudine e alla longituidine
	 *
	 * @param latitude  latitudine dell'area geografica
	 * @param longitude longitudine dell'area geografica
	 * @return {@link Result<ListaAree>} lista delle aree
	 * @throws RemoteException per la gestione delle eccezioni legate alla comunicazione con il client
	 */
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

	/**
	 * Metodo per la recuperare l'area geografica in base al geoID
	 *
	 * @param geoID geoID dell'area geografica
	 * @return {@link Result<AreaGeografica>} area geografica
	 * @throws RemoteException per la gestione delle eccezioni legate alla comunicazione con il client
	 */
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

	/**
	 * Metodo per visualizzare le misurazioni climatiche associate a una determinata area geografica
	 *
	 * @param geoID geoID dell'area geografica
	 * @return {@link Result<Misurazione>} insieme di rilevazioni climatiche legate all'area geografica
	 * @throws RemoteException per la gestione delle eccezioni legate alla comunicazione con il client
	 */
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

	/**
	 * Metodo per la registrazione degli operatori
	 *
	 * @param operator operatore da registare
	 * @param password associata all'operatore
	 * @return {@link Result<Operatore>} istanza che rappresenta l'operatore registrato
	 * @throws RemoteException per la gestione delle eccezioni legate alla comunicazione con il client
	 */
	@Override
	public Result<Operatore> registrazione(Operatore operator, String password) throws RemoteException {
		String insertQuery = """
				INSERT INTO "OperatoriRegistrati"("Name", "Surname", "CF", "Email", "Password", "Center")
				VALUES (?, ?, ?, ?, ?, ?);
				""";

		String selectQuery = """
				SELECT *
				FROM "OperatoriRegistrati"
				WHERE "CF" = ?;
				""";

		try (var stmt = ServerCM.db.prepareStatement(insertQuery)) {
			stmt.setString(1, operator.getNome());
			stmt.setString(2, operator.getCognome());
			stmt.setString(3, operator.getCf());
			stmt.setString(4, operator.getEmail());
			stmt.setString(5, password);
			stmt.setLong(6, operator.getCentro().getCenterID());

			int rows = stmt.executeUpdate();
			if (rows != 1) {
				return new Result<>(new InconsistentDataException("Insertion added " + rows + " instead of 1"));
			}
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}

		try (var stmt = ServerCM.db.prepareStatement(selectQuery)) {
			stmt.setString(1, operator.getCf());

			ResultSet set = stmt.executeQuery();
			set.next();

			return new Result<>(DataFactory.buildOperatore(set));
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}
	}

	/**
	 * Metodo per l'accesso degli operatori
	 *
	 * @param userID   ID dell'operatore
	 * @param password dell'operatore per la registrazione
	 * @return {@link Result<Operatore>} istanza che rappresenta l'operatore che ha effettuato il login
	 * @throws RemoteException per la gestione delle eccezioni legate alla comunicazione con il client
	 */
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

	/**
	 * Metodo per la registrazione di un {@link CentroMonitoraggio} nel database
	 *
	 * @param centro centro di monitoraggio da registrare
	 * @return {@link Result<CentroMonitoraggio>} istanza che rappresenta il centro di monitoraggio registrato
	 * @throws RemoteException per la gestione delle eccezioni legate alla comunicazione con il client
	 */
	@Override
	public Result<CentroMonitoraggio> registraCentroAree(CentroMonitoraggio centro) throws RemoteException {
		String insertQuery = """
				INSERT INTO "CentriMonitoraggio"("Name", "Street", "CivicNumber", "ZIPCode", "Town", "Province")
				VALUES (?, ?, ?, ?, ?, ?);
				""";
		String selectQuery = """
				SELECT *
				FROM "CentriMonitoraggio"
				WHERE "Name" = ?;
				""";
		try (var stmt = ServerCM.db.prepareStatement(insertQuery)) {
			stmt.setString(1, centro.getNome());
			stmt.setString(2, centro.getNomeVia());
			stmt.setInt(3, centro.getCivico());
			stmt.setInt(4, centro.getCap());
			stmt.setString(5, centro.getComune());
			stmt.setString(6, centro.getProvincia());

			int rows = stmt.executeUpdate();
			if (rows != 1) {
				return new Result<>(new InconsistentDataException("Insertion added " + rows + " instead of 1"));
			}
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}

		try (var stmt = ServerCM.db.prepareStatement(selectQuery)) {
			stmt.setString(1, centro.getNome());

			ResultSet set = stmt.executeQuery();
			set.next();

			return new Result<>(DataFactory.buildCentroMonitoraggio(set));
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}
	}

	/**
	 * Metodo che restituisce la lista delle aree geografiche associate a un determinato {@link CentroMonitoraggio}
	 *
	 * @param centerID id del centro di monitoraggio
	 * @return {@link Result<ListaAree>} lista delle aree associate al centro
	 * @throws RemoteException per la gestione delle eccezioni legate alla comunicazione con il client
	 */
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

	/**
	 * @return {@link Result<ListaAree>} lista delle aree geografiche presenti nel database
	 * @throws RemoteException per la gestione delle eccezioni legate alla comunicazione con il client
	 */
	@Override
	public Result<ListaAree> getAreeGeografiche() throws RemoteException {
		ListaAree la = new ListaAree();
		String query = """
				SELECT *
				FROM "CoordinateMonitoraggio";
				""";
		try (var stmt = ServerCM.db.prepareStatement(query)) {
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

	/**
	 * @return {@link Result<ListaOperatori>} lista degli operatori registrati nel database
	 * @throws RemoteException per la gestione delle eccezioni legate alla comunicazione con il client
	 */
	@Override
	public Result<ListaOperatori> getOperatoriRegistrati() throws RemoteException {
		ListaOperatori lo = new ListaOperatori();
		String query = """
				SELECT *
				FROM "OperatoriRegistrati";
				""";
		try (var stmt = ServerCM.db.prepareStatement(query)) {
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				lo.add(DataFactory.buildOperatore(set));
			}

			return new Result<>(lo);
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}
	}

	/**
	 * @return {@link Result<ListaCentri>} lista degli centri di monitoraggio nel database
	 * @throws RemoteException per la gestione delle eccezioni legate alla comunicazione con il client
	 */
	@Override
	public Result<ListaCentri> getCentriMonitoraggio() throws RemoteException {
		ListaCentri lc = new ListaCentri();
		String query = """
				SELECT *
				FROM "CentriMonitoraggio";
				""";
		try (var stmt = ServerCM.db.prepareStatement(query)) {
			ResultSet set = stmt.executeQuery();
			while (set.next()) {
				lc.add(DataFactory.buildCentroMonitoraggio(set));
			}

			return new Result<>(lc);
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}
	}

	/**
	 * Metodo per modificare la {@link ListaAree} associate a un determinato {@link CentroMonitoraggio}
	 *
	 * @param center centro di monitoraggio di cui modificare la lista
	 * @param newList lista modificata
	 * @return {@link Result<CentroMonitoraggio>} centro monitoraggio con la relativa lista di aree aggiornata
	 * @throws RemoteException per la gestione delle eccezioni legate alla comunicazione con il client
	 */

	@Override
	public Result<CentroMonitoraggio> alterListaAree(CentroMonitoraggio center, ListaAree newList) throws RemoteException {
		String deleteQuery = """
				DELETE FROM "Area_Centro"
				WHERE Centro = ?;
				""";

		String insertQuery = """
				INSERT INTO "Area_Centro"("Area", "Centro")
				""";
		insertQuery += """
				VALUES ("?", "?")
				""".repeat(newList.size());

		insertQuery += ";";

		try (var stmt = ServerCM.db.prepareStatement(deleteQuery)) {
			stmt.setLong(1, center.getCenterID());

			int rows = stmt.executeUpdate();

			if (rows != center.getAree().size()) {
				return new Result(new ResultException("Number of rows deleted did not equal list size"));
			}


		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}

		try (var stmt = ServerCM.db.prepareStatement(insertQuery)) {
			for (int i = 0; i < newList.size(); i++) {
				stmt.setLong(2 * i, newList.peekFirst().getGeoID());
				stmt.setLong(2 * i + 1, center.getCenterID());
			}

			int rows = stmt.executeUpdate();

			CentroMonitoraggio newcenter = ServerCM.server.getCentroMonitoraggio(center.getCenterID()).get();

			if (rows != newList.size()) {
				return new Result(newcenter, new ResultException("Number of rows modified did not equal list size"));
			}

			return new Result(newcenter);
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}



	}

	/**
	 * Metodo per inserire parametri climatici ({@link Misurazione}) all'interno del database
	 *
	 * @param misurazione insieme dei parametri climatici
	 * @return {@link Result<Misurazione>} istanza che rappresenta la misurazione registrata
	 * @throws RemoteException per la gestione delle eccezioni legate alla comunicazione con il client
	 */
	@Override
	public Result<Misurazione> inserisciParametriClimatici(Misurazione misurazione) throws RemoteException {
		String query = """
				INSERT INTO "ParametriClimatici"("Center", "Operator", "Area", "Datetime",
				                             "Wind", "Humidity", "Pressure", "Temperature", "Precipitation", "GlacierAltitude",
				                             "GlacierMass", "WindNotes", "HumidityNotes", "PressureNotes", "TemperatureNotes",
				                             "PrecipitationNotes", "GlacierAltitudeNotes", "GlacierMassNotes")
				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
				""";
		try (var stmt = ServerCM.db.prepareStatement(query)) {
			stmt.setLong(1, misurazione.getCentro().getCenterID());
			stmt.setLong(2, misurazione.getOperatore().getUid());
			stmt.setLong(3, misurazione.getArea().getGeoID());

			stmt.setTimestamp(4, Timestamp.valueOf(misurazione.getTime()));

			stmt.setInt(5, misurazione.getDato(TipoDatoGeografico.Vento));
			stmt.setInt(6, misurazione.getDato(TipoDatoGeografico.Umidita));
			stmt.setInt(7, misurazione.getDato(TipoDatoGeografico.Pressione));
			stmt.setInt(8, misurazione.getDato(TipoDatoGeografico.Temperatura));
			stmt.setInt(9, misurazione.getDato(TipoDatoGeografico.Precipitazioni));
			stmt.setInt(10, misurazione.getDato(TipoDatoGeografico.AltitudineGhiacciai));
			stmt.setInt(11, misurazione.getDato(TipoDatoGeografico.MassaGhiacciai));

			stmt.setString(12, misurazione.getNota(TipoDatoGeografico.Vento));
			stmt.setString(13, misurazione.getNota(TipoDatoGeografico.Umidita));
			stmt.setString(14, misurazione.getNota(TipoDatoGeografico.Pressione));
			stmt.setString(15, misurazione.getNota(TipoDatoGeografico.Temperatura));
			stmt.setString(16, misurazione.getNota(TipoDatoGeografico.Precipitazioni));
			stmt.setString(17, misurazione.getNota(TipoDatoGeografico.AltitudineGhiacciai));
			stmt.setString(18, misurazione.getNota(TipoDatoGeografico.MassaGhiacciai));

			ResultSet set = stmt.executeQuery();
			set.next();

			return new Result<>(DataFactory.buildMisurazione(set));
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}
	}

	/**
	 * Metodo che restituisce una specifica {@link Misurazione} del database
	 *
	 * @param recordID ID univoco della misurazione
	 * @return {@link Result<Misurazione>} misurazione corrispondente all'ID fornito
	 * @throws RemoteException per la gestione delle eccezioni legate alla comunicazione con il client
	 */
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

	/**
	 * Metodo che restituisce uno specifico {@link CentroMonitoraggio} del database
	 *
	 * @param centerID ID univoco del centro di monitoraggio
	 * @return {@link Result<CentroMonitoraggio>} centro di monitoraggio corrispondente all'ID fornito
	 * @throws RemoteException per la gestione delle eccezioni legate alla comunicazione con il client
	 */
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

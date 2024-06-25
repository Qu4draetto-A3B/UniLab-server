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
import org.a3b.commons.magazzeno.AreaGeografica;
import org.a3b.commons.magazzeno.CentroMonitoraggio;
import org.a3b.commons.magazzeno.Misurazione;
import org.a3b.commons.magazzeno.Operatore;
import org.a3b.commons.result.Result;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * La classe {@code Datafactory} fornisce una serie di metodi per la costruzioni d'istanze
 * a partire dai record del database
 */
@Log4j2
public class DataFactory {
	/**
	 * Costruisce un oggetto di tipo {@link AreaGeografica} a partire dal {@link ResultSet} fornito
	 *
	 * @param record relativo all'oggetto da costruire
	 * @return di istanza di {@link AreaGeografica} costruita
	 * @throws SQLException per gestire eccezioni legate a errori database
	 */
	public static AreaGeografica buildAreaGeografica(ResultSet record) throws SQLException {
		if (Objects.isNull(record)) {
			throw new NullPointerException("record was null while trying to extract AreaGeografica");
		}
		if (record.getRow() == 0) {
			throw new IllegalArgumentException("Invalid row in record while trying to extract AreaGeografica");
		}

		return new AreaGeografica(
				record.getLong("GeoID"),
				record.getDouble("Latitude"),
				record.getDouble("Longitude"),
				record.getString("CountryCode"),
				record.getString("Name")
		);
	}

	/**
	 * Costruisce un oggetto di tipo {@link CentroMonitoraggio} a partire dal {@link ResultSet} fornito
	 *
	 * @param record relativo all'oggetto da costruire
	 * @return di istanza di {@link CentroMonitoraggio} costruita
	 * @throws SQLException    per gestire eccezioni legate a errori database
	 * @throws RemoteException per gestire eccezioni legate alla comunicazione con il client
	 */
	public static CentroMonitoraggio buildCentroMonitoraggio(ResultSet record) throws SQLException, RemoteException {
		if (Objects.isNull(record)) {
			throw new NullPointerException("record was null while trying to extract CentroMonitoraggio");
		}
		if (record.getRow() == 0) {
			throw new IllegalArgumentException("Invalid row in record while trying to extract CentroMonitoraggio");
		}

		long id = record.getLong("CenterID");
		return new CentroMonitoraggio(
				id,
				record.getString("Name"),
				record.getString("Street"),
				record.getInt("CivicNumber"),
				Integer.parseInt(record.getString("ZIPCode")),
				record.getString("Town"),
				record.getString("Province"),
				ServerCM.server.getListaAree(id).get()
		);
	}

	/**
	 * Costruisce un oggetto di tipo {@link Operatore} a partire dal {@link ResultSet} fornito
	 *
	 * @param record relativo all'oggetto da costruire
	 * @return di istanza di {@link Operatore} costruita
	 * @throws SQLException    per gestire eccezioni legate a errori database
	 * @throws RemoteException per gestire eccezioni legate alla comunicazione con il client
	 */
	public static Operatore buildOperatore(ResultSet record) throws SQLException, RemoteException {
		if (Objects.isNull(record)) {
			throw new NullPointerException("record was null while trying to extract Operatore");
		}
		if (record.getRow() == 0) {
			throw new IllegalArgumentException("Invalid row in record while trying to extract Operatore");
		}

		return new Operatore(
				record.getLong("UserID"),
				record.getString("Name"),
				record.getString("Surname"),
				record.getString("CF"),
				record.getString("Email"),
				ServerCM.server.getCentroMonitoraggio(record.getLong("Center")).get()
		);
	}

	/**
	 * Costruisce un oggetto di tipo {@link Misurazione} a partire dal {@link ResultSet} fornito
	 *
	 * @param record relativo all'oggetto da costruire
	 * @return di istanza di {@link Misurazione} costruita
	 * @throws SQLException    per gestire eccezioni legate a errori database
	 * @throws RemoteException per gestire eccezioni legate alla comunicazione con il client
	 */
	public static Misurazione buildMisurazione(ResultSet record) throws SQLException, RemoteException {
		if (Objects.isNull(record)) {
			throw new NullPointerException("record was null while trying to extract Misurazione");
		}
		if (record.getRow() == 0) {
			throw new IllegalArgumentException("Invalid row in record while trying to extract Misurazione");
		}

		return new Misurazione(
				record.getLong("RecordID"),
				record.getTimestamp("Datetime").toLocalDateTime(),
				getOperatore(record.getLong("Operator")).get(),
				ServerCM.server.getCentroMonitoraggio(record.getLong("Center")).get(),
				ServerCM.server.getAreaGeografica(record.getLong("Area")).get(),
				Misurazione.buildDati(
						record.getByte("GlacierAltitude"),
						record.getByte("GlacierMass"),
						record.getByte("Precipitation"),
						record.getByte("Pressure"),
						record.getByte("Temperature"),
						record.getByte("Humidity"),
						record.getByte("Wind")
				),
				Misurazione.buildNote(
						record.getString("GlacierAltitudeNotes"),
						record.getString("GlacierMassNotes"),
						record.getString("PrecipitationNotes"),
						record.getString("PressureNotes"),
						record.getString("TemperatureNotes"),
						record.getString("HumidityNotes"),
						record.getString("WindNotes")
				)
		);
	}

	/**
	 * Restituisce l'operatore corrisponde all'{@code userID}
	 *
	 * @param userID dell'operatore
	 * @return {@link Result<Operatore>} cercato nel database
	 * @throws RemoteException per gestire eccezioni legate alla comunicazione con il client
	 */
	private static Result<Operatore> getOperatore(long userID) throws RemoteException {
		String query = """
				SELECT *
				FROM "ParametriClimatici"
				WHERE "Operator" = ?;
				""";
		try (var stmt = ServerCM.db.prepareStatement(query)) {
			stmt.setLong(1, userID);
			ResultSet set = stmt.executeQuery();
			set.next();

			return new Result<>(DataFactory.buildOperatore(set));
		} catch (SQLException e) {
			log.error("Error!", e);
			return new Result<>(e);
		}
	}
}

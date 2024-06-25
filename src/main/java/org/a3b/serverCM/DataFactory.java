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

@Log4j2
public class DataFactory {
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

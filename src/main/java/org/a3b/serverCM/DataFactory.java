package org.a3b.serverCM;

import org.a3b.commons.magazzeno.AreaGeografica;
import org.a3b.commons.magazzeno.CentroMonitoraggio;
import org.a3b.commons.magazzeno.Misurazione;
import org.a3b.commons.magazzeno.Operatore;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

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

	public static CentroMonitoraggio buildCentroMonitoraggio(ResultSet record) throws SQLException {
		if (Objects.isNull(record)) {
			throw new NullPointerException("record was null while trying to extract CentroMonitoraggio");
		}
		if (record.getRow() == 0) {
			throw new IllegalArgumentException("Invalid row in record while trying to extract CentroMonitoraggio");
		}

		return new CentroMonitoraggio(
				record.getLong("CenterID"),
				record.getString("Name"),
				record.getString("Street"),
				record.getInt("CivicNumber"),
				record.getString("ZIPCode"),
				record.getString("Town"),
				record.getString("Province")
				//serve ListaAree per costruire l'oggetto
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
				ServerCM.server.getCentroMonitoraggio(record.getLong("Center")).get(),
				ServerCM.server.getOperatore(record.getLong("Operator")).get(),
				ServerCM.server.getAreaGeografica(record.getLong("Area")).get(),
				record.getTimestamp("Datetime"), //misurazione usa localdatetime
				record.getInt("Wind"),
				record.getInt("Humidity"),
				record.getInt("Pressure"),
				record.getInt("Temperature"),
				record.getInt("Precipitation"),
				record.getInt("GlacierAltitude"),
				record.getInt("GlacierMass"),
				record.getString("WindNotes"),
				record.getString("HumidityNotes"),
				record.getString("PressureNotes"),
				record.getString("TemperatureNotes"),
				record.getString("PrecipitationNotes"),
				record.getString("GlacierAltitudeNotes"),
				record.getString("GlacierMassNotes"),
				record.getString("GlacierMassNotes")
		);
	}
}

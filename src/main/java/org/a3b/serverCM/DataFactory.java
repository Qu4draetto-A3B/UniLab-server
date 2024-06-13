package org.a3b.serverCM;

import org.a3b.serverCM.magazzeno.AreaGeografica;

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
}

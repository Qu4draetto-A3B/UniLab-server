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
package org.a3b.commons.magazzeno;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * La classe {@code AreaGeografica} rappresenta un'area geografica identificata
 * da: GeoID, latitudine, longitudine, stato e denominazione.
 * <p>
 * Questa classe implementa l'interfaccia {@link Serializable} per consentire la
 * serializzazione dei dati.
 */
@Data
public class AreaGeografica implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private long geoID;
	private double latitudine;
	private double longitudine;
	private String stato;
	private String denominazione;

	/**
	 * Costruttore di un'istanza di {@code AreaGeografica}.
	 *
	 * @param geoID         ID dell'area geografica
	 * @param latitudine    latitudine relativa all'area geografica
	 * @param longitudine   longitudine relativa all'area geografica
	 * @param stato         stato in cui si trova l'area geografica
	 * @param denominazione nome dell'area geografica
	 */
	public AreaGeografica(long geoID, double latitudine, double longitudine, String stato, String denominazione) {
		this.geoID = geoID;
		this.latitudine = latitudine;
		this.longitudine = longitudine;
		this.stato = stato;
		this.denominazione = denominazione;
	}

	/**
	 * Costruttore di un'istanza di {@code AreaGeografica}.
	 */
	public AreaGeografica() {
		geoID = 0;
		latitudine = 0;
		longitudine = 0;
		stato = "//";
		denominazione = "//";
	}

	@Override
	public String toString() {
		return String.format("[%d] %s (%s) LAT:%f LON:%f", this.geoID, this.denominazione, this.stato, this.latitudine,
				this.longitudine);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AreaGeografica ag)) {
			return super.equals(obj);
		}
		return this.geoID == ag.getGeoID();
	}
}

/*
 * Interdisciplinary Workshop A
 * Climate Monitoring
 * A.A. 2022-2023
 *
 * Authors:
 * - Iuri Antico, 753144
 * - Beatrice Balzarini, 752257
 * - Michael Bernasconi, 752259
 * - Gabriele Borgia, 753262
 *
 * Some rights reserved.
 * See LICENSE file for additional information.
 */
package org.a3b.serverCM.magazzeno;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * La classe {@code AreaGeografica} rappresenta un'area geografica identificata
 * da: GeoID, latitudine, longitudine, stato e denominazione.
 * <p>
 * Questa classe implementa l'interfaccia {@link DataTable} per consentire la
 * gestione dei dati.
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
	 * Restituisce l'ID dell'area geografica.
	 *
	 * @return {@link #geoID} relativo all'{@code AreaGeografica}
	 */
	public long getGeoID() {
		return geoID;
	}

	/**
	 * Restituisce la latitudine dell'area geografica.
	 *
	 * @return {@link #latitudine} relativa all'{@code AreaGeografica}
	 */
	public double getLatitudine() {
		return latitudine;
	}

	/**
	 * Restituisce la longitudine dell'area geografica.
	 *
	 * @return {@link #longitudine} relativa all'{@code AreaGeografica}
	 */
	public double getLongitudine() {
		return longitudine;
	}

	/**
	 * Restituisce lo stato in cui si trova dell'area geografica.
	 *
	 * @return {@link #stato} relativo all'{@code AreaGeografica}
	 */
	public String getStato() {
		return stato;
	}

	/**
	 * Restituisce il nome dell'area geografica.
	 *
	 * @return {@link #denominazione} relativa all'{@code AreaGeografica}
	 */
	public String getDenominazione() {
		return denominazione;
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

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
package org.a3b.commons.magazzeno;

import lombok.Data;
import org.a3b.commons.utils.CercaAree;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * La classe {@code ListaAree} rappresenta una lista di istanze di
 * {@link AreaGeografica}.
 * <p>
 * Fornisce metodi per gestire e accedere agli elementi della lista.
 * <p>
 * Implementa l'interfaccia {@link Iterable} per consentire l'iterazione delle
 * aree geografiche nella lista, mentre le interfacce {@link CercaAree}  fornisce metodi per effettuare
 * operazioni di ricerca e convertire le istanze.
 */
@Data
public class ListaAree extends LinkedList<AreaGeografica> implements CercaAree, Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Ricerca un'area geografica nella lista basandosi sul suo ID.
	 *
	 * @param geoId ID dell'{@link AreaGeografica} da cercare
	 * @return Risultato della ricerca
	 */
	public AreaGeografica getAreaGeografica(long geoId) {
		for (AreaGeografica ag : this) {
			if (geoId == ag.getGeoID()) {
				return ag;
			}
		}
		throw new IllegalArgumentException("Area non trovata");
	}

	@Override
	public ListaAree cercaAreeGeografiche(String denominazione, String stato) {
		if (denominazione == null) {
			denominazione = "";
		}
		if (stato == null) {
			stato = "";
		}

		denominazione = denominazione.strip();
		stato = stato.strip();

		ListaAree la = new ListaAree();

		if (!denominazione.isEmpty()) {
			for (AreaGeografica areaGeografica : this) {
				if (areaGeografica.getDenominazione().equalsIgnoreCase(denominazione))
					la.addFirst(areaGeografica);
			}
		}

		if (!stato.isEmpty()) {
			for (AreaGeografica areaGeografica : la) {
				if (areaGeografica.getStato().equalsIgnoreCase(stato))
					la.add(areaGeografica);
			}
		}

		return la;
	}

	@Override
	public ListaAree cercaAreeGeografiche(double latitudine, double longitudine) {
		ListaAree list = new ListaAree();

		if ((latitudine < -90) || (latitudine > 90)) {
			throw new IllegalArgumentException("hai inserito valori errati riprova");
		}
		if ((longitudine < -180) || (longitudine > 180)) {
			throw new IllegalArgumentException("hai inserito valori errati riprova");
		}

		for (AreaGeografica areaGeografica : this) {
			if ((latitudine == areaGeografica.getLatitudine()) && (longitudine == areaGeografica.getLongitudine())) {
				list.add(areaGeografica);
				return list;
			}
		}

		AreaGeografica ag = this.getFirst();
		double differenzalat = latitudine - ag.getLatitudine();
		differenzalat *= differenzalat;

		double differenzalong = longitudine - ag.getLongitudine();
		differenzalong *= differenzalong;

		double min = Math.sqrt(differenzalat + differenzalong);
		for (AreaGeografica areaGeografica : this) {

			differenzalat = latitudine - areaGeografica.getLatitudine();
			differenzalat *= differenzalat;

			differenzalong = longitudine - areaGeografica.getLongitudine();
			differenzalong *= differenzalong;

			double dist = Math.sqrt(differenzalat + differenzalong);

			if (min > dist) {
				min = dist;
				ag = areaGeografica;
				list.add(ag);
			}

		}
		return list;

	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (AreaGeografica tmp : this)
			str.append(tmp.toString()).append("\n");
		return str.toString();
	}

}
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
package org.a3b.serverCM.utils;

import  org.a3b.serverCM.magazzeno.AreaGeografica;
import  org.a3b.serverCM.magazzeno.ListaAree;
import  org.a3b.serverCM.utils.result.Result;

/**
 * L'interfaccia {@code CercaAree} modella la ricerca di aree geografiche.
 */
public interface CercaAree {

	/**
	 * Ricerca un'area geografica in base a denominazione e stato di appartenenza forniti.
	 *
	 * @return aree nel cui nome &egrave presente la stringa di caratteri fornita
	 * @param denominazione nome relativo a un'{@link AreaGeografica}
	 * @param stato         stato di appartenenza di un'{@link AreaGeografica}
	 */
	public ListaAree cercaAreaGeografica(String denominazione, String stato);

	/**
	 * Ricerca delle aree geografiche in base alle coordinate geografiche fornite.
	 *
	 * @return nome dell'{@link AreaGeografica} corrispondente alle coordinate geografiche corrispondenti o delle aree geografiche corrispondenti
	 *         con coordinate più vicine
	 * @param latitudine  latitudine di una coordinata geografica
	 * @param longitudine longitudine di una coordinata geografica
	 */

	public ListaAree cercaAreeGeografiche(double latitudine, double longitudine);
}

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
package org.a3b.commons.utils;

import org.a3b.commons.magazzeno.AreaGeografica;
import org.a3b.commons.magazzeno.ListaAree;

/**
 * L'interfaccia {@code CercaAree} modella la ricerca di aree geografiche.
 */
public interface CercaAree {

	/**
	 * Ricerca un'area geografica in base a denominazione e stato di appartenenza forniti.
	 *
	 * @param denominazione nome relativo a un'{@link AreaGeografica}
	 * @param stato         stato di appartenenza di un'{@link AreaGeografica}
	 * @return aree nel cui nome &egrave; presente la stringa di caratteri fornita
	 */
	ListaAree cercaAreeGeografiche(String denominazione, String stato);

	/**
	 * Ricerca delle aree geografiche in base alle coordinate geografiche fornite.
	 *
	 * @param latitudine  latitudine di una coordinata geografica
	 * @param longitudine longitudine di una coordinata geografica
	 * @return nome dell'{@link AreaGeografica} corrispondente alle coordinate geografiche corrispondenti o delle aree geografiche corrispondenti
	 * con coordinate più vicine
	 */

	ListaAree cercaAreeGeografiche(double latitudine, double longitudine);
}

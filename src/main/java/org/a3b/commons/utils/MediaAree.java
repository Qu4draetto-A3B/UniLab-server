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
package org.a3b.commons.utils;

import org.a3b.commons.magazzeno.AreaGeografica;
import org.a3b.commons.magazzeno.Misurazione;

/**
 * L'interfaccia {@link MediaAree} si occupa di calcolare la media dei dati di una {@link AreaGeografica}.
 */
public interface MediaAree {

	Misurazione visualizzaAreaGeografica(AreaGeografica area);
}

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
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * La classe {@code ListaOperatori} rappresenta una lista di istanze di
 * {@link Operatore}.
 * <p>
 * Fornisce metodi per gestire e accedere agli elementi della lista.
 * <p>
 * Questa classe implementa l'interfaccia {@link Serializable} per consentire la
 * serializzazione dei dati.
 */

@Data
public class ListaCentri extends ConcurrentLinkedDeque<CentroMonitoraggio> implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (CentroMonitoraggio tmp : this)
			str.append(tmp.toString()).append("\n");
		return str.toString();
	}

}

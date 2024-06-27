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
public class ListaOperatori extends ConcurrentLinkedDeque<Operatore> implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (Operatore tmp : this)
			str.append(tmp.toString()).append("\n");
		return str.toString();
	}

}

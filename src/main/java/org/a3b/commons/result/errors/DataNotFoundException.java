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
package org.a3b.commons.result.errors;

import org.a3b.commons.result.ResultException;

/**
 * La classe {@code DataNotFoundException} rappresenta un eccezione non controllata se il dato non e' presente,
 * creata con il messaggio dell'eccezione e l'eccezione
 * <p>
 * Questa classe estende la classe {@link ResultException} per gestire le eccezioni non controllate
 */
public class DataNotFoundException extends ResultException {
	/**
	 * Costruttore di un'istanza di {@code DataNotFoundException}
	 *
	 * @param msg l'errore visualizzato sul display dell'user
	 */
	public DataNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * Costruttore di un'istanza di {@code DataNotFoundException}
	 *
	 * @param t oggetto che ha causato l'errore
	 */
	public DataNotFoundException(Throwable t) {
		super(t);
	}
}

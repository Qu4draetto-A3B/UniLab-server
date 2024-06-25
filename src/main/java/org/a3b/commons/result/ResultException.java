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
package org.a3b.commons.result;

/**
 * La classe {@code ResultException} rappresenta un'eccezione non controllata ed estende
 * {@link RuntimeException}.
 * <p>
 * Viene utilizzata quando si presenta un errore inaspettato o una situazione
 * critica che impediscono la continuazione del programma.
 */
public class ResultException extends RuntimeException {
	/**
	 * Costruttore di un'istanza di {@code ResultException}.
	 * <p>
	 * L'istanza viene creata a partire dal messaggio da mostrare all'utente.
	 *
	 * @param msg l'errore visualizzato sul display dell'user
	 */
	public ResultException(String msg) {
		super(msg);
	}

	/**
	 * Costruttore di un'istanza di {@code ResultException}.
	 * <p>
	 * L'istanza viene creata a partire dall'oggetto <i> {@link Throwable} </i> specificato.
	 *
	 * @param t oggetto che ha causato l'errore
	 */
	public ResultException(Throwable t) {
		super(t);
	}
}

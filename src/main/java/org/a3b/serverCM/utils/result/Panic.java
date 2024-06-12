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
package org.a3b.serverCM.utils.result;
/**
 * La classe {@code Panic} rappresenta un'eccezione non controllata ed estende
 * {@link Error}.
 * <p>
 * Viene utilizzata quando si presenta un errore inaspettato o una situazione
 * critica che impediscono la continuazione del programma.
 */
public class Panic extends Error {
	/**
	 * Costruttore di un'istanza di {@code Panic}.
	 * <p>
	 * L'istanza viene creata a partire dal messaggio da mostrare all'utente.
	 *
	 * @param msg The error message to display to the user.
	 */
	public Panic(String msg) {
		super(msg);
	}

	/**
	 * Costruttore di un'istanza di {@code Panic}.
	 * <p>
	 * L'istanza viene creata a partire dall'oggetto <i> {@link Throwable} </i> specificato.
	 *
	 * @param t oggetto che ha causato l'errore
	 */
	public Panic(Throwable t) {
		super(t);
	}
}

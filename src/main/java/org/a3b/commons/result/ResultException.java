package org.a3b.commons.result;

/**
 * La classe {@code Panic} rappresenta un'eccezione non controllata ed estende
 * {@link Error}.
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
	 * @param msg The error message to display to the user.
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
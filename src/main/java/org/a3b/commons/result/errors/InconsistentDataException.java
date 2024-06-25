package org.a3b.commons.result.errors;

import org.a3b.commons.result.ResultException;
/**
 * La classe {@code InconsistentDataException} rappresenta un eccezione non controllata se il dato non e' inconsistente,
 * creata con il messaggio dell'eccezione e l'eccezione
 * <p>
 * Questa classe estende la classe {@link ResultException} per gestire le eccezioni non controllate
 */
public class InconsistentDataException extends ResultException {
	/**
	 * Costruttore di un'istanza di {@code InconsistentDataException}
	 *
	 * @param msg l'errore visualizzato sul display dell'user
	 */
	public InconsistentDataException(String msg) {
		super(msg);
	}
	/**
	 * Costruttore di un'istanza di {@code InconsistentDataException}
	 *
	 * @param t oggetto che ha causato l'errore
	 */
	public InconsistentDataException(Throwable t) {
		super(t);
	}
}

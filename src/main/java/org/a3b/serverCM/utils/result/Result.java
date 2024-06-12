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
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * La classe {@class Result<T>} rappresenta il risultato di un'operazione che
 * può essere stata eseguita correttamente o contenere un errore.
 *
 * @param <T> tipo del contenuto del {@code Result}
 */
public class Result<T> {
	private final int error;
	private final String message;
	private final T content;
	private final String fullMessage;

	/**
	 * Costruttore di un'istanza di {@code Result} contenente un'operazione eseguita
	 * correttamente.
	 *
	 * @param content contenuto del {@code Result}.
	 */

	public Result(T content) {
		this(0, "OK", content);
	}

	/**
	 * Costruttore di un'istanza di {@code Result} contenente un errore.
	 *
	 * @param err codice di errore
	 * @param msg messaggio relativo all'errore
	 */
	public Result(int err, String msg) {
		this(err, msg, null);
	}

	/**
	 *
	 * Costruttore di un'istanza di {@code Result}.
	 * <p>
	 * L'istanza viene creata a partire da codice, messaggio e contenuto relativi
	 * all'errore specificati.
	 *
	 * @param err     codice di errore
	 * @param msg     messaggio relativo all'errore
	 * @param content contenuto del {@code Result}, {@code null} se l'operazione non
	 *                viene eseguita correttamente
	 */
	public Result(int err, String msg, T content) {
		this.error = err;
		this.message = msg;
		this.content = content;
		this.fullMessage = String.format("%s [%d] %s", content != null ? content.getClass() : "NULL", error, message);
	}

	/**
	 * Controlla che il {@code Result} sia valido.
	 * <p>
	 * Un {@code Result} risulta valido se l'operazione che contiene &egrave stata
	 * eseguita correttamente.
	 *
	 * @return {@code boolean} che indica la validità del {@code Result}
	 */
	public boolean isValid() {
		return error == 0;
	}

	/**
	 * Controlla se il {@code Result} contiene un errore.
	 *
	 * @return {@code boolean} che indica se il {@code Result} contiene un errore
	 */
	public boolean isError() {
		return error != 0;
	}

	/**
	 * Esegue la funzione fornita se il {@code Result} risulta valido.
	 *
	 * @param fn funzione da eseguire con contenuto e codice di errore come
	 *           parametri
	 */
	public void ifValid(BiConsumer<T, Integer> fn) {
		if (isValid()) {
			fn.accept(content, error);
		}
	}

	/**
	 * Esegue la funzione fornita se il {@code Result} contiene un errore.
	 *
	 * @param fn funzione da eseguire con contenuto e codice di errore come
	 *           parametri
	 */
	public void ifError(BiConsumer<T, Integer> fn) {
		if (isError()) {
			fn.accept(content, error);
		}
	}

	/**
	 * Restituisce il codice relativo all'errore contenuto nel {@code Result}.
	 *
	 * @return codice di errore del {@code Result}
	 */
	public Integer getError() {
		return error;
	}

	/**
	 * Restituisce il messaggio relativo all'errore contenuto nel {@code Result}.
	 *
	 * @return messaggio relativo all'errore del {@code Result}
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Restituisce l'intero messaggio relativo all'errore all'errore contenuto nel
	 * {@code Result}.
	 * <p>
	 * L'intero messaggio comprende il tipo di contenuto, il codice e il messaggio
	 * di errore.
	 *
	 * @return l'intero messaggio
	 */
	public String getFullMessage() {
		return fullMessage;
	}

	/**
	 * Restituisce il contenuto del {@code Result}.
	 * <p>
	 * Lancia un'eccezione {@link Panic} se il contenuto &egrave {@code null}.
	 *
	 * @return contenuto del {@code Result}
	 * @throws Panic se il contenuto &egrave {@code null}
	 */
	public T get() {
		if (content == null) {
			panic();
		}
		return content;
	}

	/**
	 * Restituisce il contenuto del {@code Result} o il valore di default fornito se
	 * il contenuto &egrave {@code null}.
	 *
	 * @param other valore di default
	 * @return contenuto del {@code Result} o valore di default
	 */
	public T getOr(T other) {
		return content != null ? content : other;
	}

	/**
	 * Rstituisce il contenuto del {@code Result} o il valore prodotto dalla
	 * funzione fornita se il contenuto &egrave {@code null}.
	 *
	 *
	 * @param fn funzione che produce il valore che viene restituito se il contenuto
	 *           del {@code Result} &egrave {@code null}
	 * @return contenuto del {@code Result} o il valore prodotto dalla funzione
	 *         fornita
	 */
	public T getOrElse(Supplier<T> fn) {
		return content != null ? content : fn.get();
	}

	/**
	 * Restituisce il contenuto del {@code Result} senza verificare che non sia {@code null}.
	 *
	 * @return contenuto del {@code Result}
	 */
	public T except() {
		return content;
	}

	/**
	 * Lancia un'eccezione {@link Panic} con l'intero messaggio associato.
	 *
	 * @throws Panic con l'intero messaggio associato.
	 */
	public void panic() {
		throw new Panic(fullMessage);
	}
}

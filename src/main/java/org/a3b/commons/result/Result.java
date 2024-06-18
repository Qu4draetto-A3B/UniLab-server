package org.a3b.commons.result;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * La classe {@code Result<T, E>} rappresenta il risultato di un'operazione che
 * può essere stata eseguita correttamente o contenere un errore.
 *
 * @param <T> tipo del contenuto del {@code Result}
 */
public class Result<T> {
	private final T content;
	private final Throwable error;

	/**
	 * Costruttore di un'istanza di {@code Result} contenente un'operazione eseguita
	 * correttamente.
	 *
	 * @param content contenuto del {@code Result}.
	 */

	public Result(T content) {
		this(content, null);
	}

	public Result(Throwable error) {
		this(null, error);
	}

	/**
	 * Costruttore di un'istanza di {@code Result}.
	 * <p>
	 * L'istanza viene creata a partire da codice, messaggio e contenuto relativi
	 * all'errore specificati.
	 *
	 * @param content contenuto del {@code Result}, {@code null} se l'operazione non
	 *                viene eseguita correttamente
	 */
	public Result(T content, Throwable error) {
		this.content = content;
		this.error = error;
	}

	/**
	 * Controlla che il {@code Result} sia valido.
	 * <p>
	 * Un {@code Result} risulta valido se l'operazione che contiene &egrave; stata
	 * eseguita correttamente.
	 *
	 * @return {@code boolean} che indica la validità del {@code Result}
	 */
	public boolean isValid() {
		return Objects.nonNull(content);
	}

	/**
	 * Controlla se il {@code Result} contiene un errore.
	 *
	 * @return {@code boolean} che indica se il {@code Result} contiene un errore
	 */
	public boolean isError() {
		return Objects.nonNull(error);
	}

	/**
	 * Esegue la funzione fornita se il {@code Result} risulta valido.
	 *
	 * @param fn funzione da eseguire con contenuto e codice di errore come
	 *           parametri
	 */
	public void ifValid(BiConsumer<T, Throwable> fn) {
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
	public void ifError(BiConsumer<T, Throwable> fn) {
		if (isError()) {
			fn.accept(content, error);
		}
	}

	/**
	 * Restituisce il contenuto del {@code Result}.
	 */
	public T get() {
		if (content == null) {
			panic();
		}
		return content;
	}

	/**
	 * Restituisce il contenuto del {@code Result} o il valore di default fornito se
	 * il contenuto &egrave; {@code null}.
	 *
	 * @param other valore di default
	 * @return contenuto del {@code Result} o valore di default
	 */
	public T getOr(T other) {
		return isValid() ? content : other;
	}

	/**
	 * Rstituisce il contenuto del {@code Result} o il valore prodotto dalla
	 * funzione fornita se il contenuto &egrave; {@code null}.
	 *
	 * @param fn funzione che produce il valore che viene restituito se il contenuto
	 *           del {@code Result} &egrave; {@code null}
	 * @return contenuto del {@code Result} o il valore prodotto dalla funzione
	 * fornita
	 */
	public T getOrElse(Supplier<T> fn) {
		return isValid() ? content : fn.get();
	}

	/**
	 * Restituisce il contenuto del {@code Result} senza verificare che non sia
	 * {@code null}.
	 *
	 * @return contenuto del {@code Result}
	 */
	public T except() {
		return content;
	}

	/**
	 */
	public void panic() {
		throw new Error(error);
	}
}

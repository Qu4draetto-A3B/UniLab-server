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
package  org.a3b.serverCM.utils.ListaCustom;

/**
 * La classe {@code Nodo<E>} rappresenta un elemento di una lista.
 *
 * @param <E> tipo dell'elemento contenuto nel nodo
 */
public class Nodo<E> {
	private E dato;
	private Nodo<E> next;

	/**
	 * Costruttore di un'istanza di {@code Nodo}.
	 * <p>
	 * L'istanza viene creata a partire dal dato specificato e il riferimento al
	 * nodo successivo.
	 *
	 * @param dato elemento da inserire nel nodo
	 * @param next riferimento al nodo successivo nella lista
	 */
	public Nodo(E dato, Nodo<E> next) {
		this.next = next;
		this.dato = dato;
	}

	/**
	 * Imposta il dato successivo nella lista.
	 *
	 * @param next riferimento al nodo da inserire come successivo
	 */
	public void setNext(Nodo<E> next) {
		this.next = next;
	}

	/**
	 * Imposta il dato del nodo.
	 *
	 * @param dato dato da inserire nel nodo
	 */
	public void setDato(E dato) {
		this.dato = dato;
	}

	/**
	 * Restituisce il riferimento al nodo successivo nella lista.
	 *
	 * @return rifeirmento al nodo successivo
	 */
	public Nodo<E> getNext() {
		return next;
	}

	/**
	 * Restituisce il dato contenuto nel nodo.
	 *
	 * @return dato contenuto nel nodo
	 */
	public E getDato() {
		return dato;
	}
}

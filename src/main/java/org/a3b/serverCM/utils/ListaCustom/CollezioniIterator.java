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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * La classe {@code CollezioniIterator<E>} rappresenta un iteratore per una
 * collezione di nodi contenenti elementi.
 *
 * @param <E> tipo degli elementi nei nodi
 */
public class CollezioniIterator<E> implements Iterator<E> {
	Nodo<E> current;

	/**
	 * Costruttore di un'istanza di {@code Collezioni Iterator}.
	 * <p>
	 * L'istanza viene creata a partire dal nodo di partenza (<i>head</i>).
	 *
	 * @param head riferimento al nodo di inizio della collezione.
	 */
	public CollezioniIterator(Nodo<E> head) {
		current = head;
	}

	/**
	 * Controlla se esiste un nodo successivo nella collezione.
	 *
	 * @return {@code boolean} che indica l'esistenza di un nodo succesivo.
	 */
	public boolean hasNext() {
		return current != null;
	}

	/**
	 * Recupera il dato contenuto nel nodo corrente e sposta l'iteratore al nodo
	 * successivo.
	 *
	 * @return dato contenuto nel nodo corrente
	 * @throws NoSuchElementException se non sono presenti ulteriori elementi da
	 *                                iterare
	 */
	public E next() {
		E e = current.getDato();
		current = current.getNext();

		return e;
	}

}

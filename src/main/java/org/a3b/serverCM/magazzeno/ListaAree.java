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
package org.a3b.serverCM.magazzeno;

import java.io.Serial;
import java.io.Serializable;
import java.util.NoSuchElementException;

import lombok.Data;
import org.a3b.serverCM.utils.CercaAree;
import org.a3b.serverCM.utils.ListaCustom.*;
import org.a3b.serverCM.utils.result.Result;

import java.util.Iterator;

/**
 * La classe {@code ListaAree} rappresenta una lista di istanze di
 * {@link AreaGeografica}.
 * <p>
 * Fornisce metodi per gestire e accedere agli elementi della lista.
 * <p>
 * Implementa l'interfaccia {@link Iterable} per consentire l'iterazione delle
 * aree geografiche nella lista, mentre le interfacce {@link CercaAree} e
 * {@link Convertable} forniscono metodi per effettuare
 * operazioni di ricerca e convertire le istanze.
 */
@Data
public class ListaAree implements Iterable<AreaGeografica>, CercaAree, Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	Nodo<AreaGeografica> head;
	Nodo<AreaGeografica> tail;

	/**
	 * Controlla se la lista di aree georgafiche &egrave vuota.
	 *
	 * @return {@code boolean} che indica se la lista &egrave vuota
	 */
	public boolean isEmpty() {
		return head == null;
	}

	/**
	 * Recupera l'area geografica presente nella posizione fornita della lista.
	 *
	 * @throws IndexOutOfBoundsException se l'indice fornito non &egrave valido
	 * @param k indice della posizione dell'area geografica
	 * @return {@link AreaGeografica} presente all'indice fornito
	 *
	 */
	public AreaGeografica get(int k) {
		if (0 > k || k >= this.size())
			throw new IndexOutOfBoundsException("ERRORE : valore dell'indice non valido");
		Nodo<AreaGeografica> current = head;
		for (int i = 0; i < k; i++)
			current = current.getNext();

		return current.getDato();

	}

	/**
	 * Aggiunge un'area geografica nella posizione fornita della lista.
	 *
	 * @throws IndexOutOfBoundsException se l'indice fornito non &egrave valido
	 * @param e {@link AreaGeografica} da aggiungere alla lista
	 * @param k indice della posizione dell'area geografica
	 */
	public void add(AreaGeografica e, int k) {
		if ((0 > k) || (k >= this.size()))
			throw new IndexOutOfBoundsException("ERRORE: valore dell'indice non valido");
		Nodo<AreaGeografica> current = head;
		for (int i = 0; i < k - 1; i++) {
			current = current.getNext();
		}
		Nodo<AreaGeografica> x = new Nodo<AreaGeografica>(e, current.getNext());
		current.setNext(x);
	}

	/**
	 * Restituisce l'area geografica presente nella prima posizione della lista.
	 *
	 * @return {@link AreaGeografica} presente in prima posizione nella lista
	 * @throws NoSuchElementException se la lista &egrave vuota
	 */
	public AreaGeografica getFirst() {
		if (head == null)
			throw new NoSuchElementException("La lista e' vuota");
		return head.getDato();
	}

	/**
	 * Restituisce l'area geografica presente nell'ultima posizione della lista.
	 *
	 * @return {@link AreaGeografica} presente in ultima posizione nella lista
	 * @throws NoSuchElementException se la lista &egrave vuota
	 */
	public AreaGeografica getLast() {
		if (head == null)
			throw new NoSuchElementException("La lista e' vuota");
		return tail.getDato();
	}

	/**
	 * Indica il numero di elementi presenti nella lista.
	 *
	 * @return numero di istanze di {@link AreaGeografica} presenti nella lista
	 */
	public int size() {
		int i = 0;
		Nodo<AreaGeografica> current = head;
		while (current != null) {
			i++;
			current = current.getNext();
		}
		return i;
	}

	/**
	 * Aggiunge un'area geografica in prima posizione nella lista.
	 *
	 * @param e {@link AreaGeografica} da aggiungere alla lista
	 */
	public void addFirst(AreaGeografica e) {
		Nodo<AreaGeografica> x = new Nodo<AreaGeografica>(e, head);
		head = x;
		if (tail == null)
			tail = x;

	}

	/**
	 * Crea un iteratore con le aree presenti nella lista.
	 *
	 * @return {@link Iterator} per iterare le istanze di {@link AreaGeografica}
	 */
	public Iterator<AreaGeografica> iterator() {
		return new CollezioniIterator<AreaGeografica>(head);
	}

	/**
	 * Ricerca un'area geografica nella lista basandosi sul suo ID.
	 * <p>
	 * Nel caso in cui l'{@link AreaGeografica} non venga trovata, restituisce un
	 * {@link Result} con un codice di errore.
	 *
	 * @param geoId ID dell'{@link AreaGeografica} da cercare
	 * @return {@link Result} contenente il risultato della ricerca
	 */
	public Result<AreaGeografica> getArea(long geoId) {
		for (AreaGeografica ag : this) {
			if (geoId == ag.getGeoID()) {
				return new Result<AreaGeografica>(ag);
			}
		}

		return new Result<>(1, "Area non trovata");
	}

	@Override
	public ListaAree cercaAreaGeografica(String denominazione, String stato) {

		ListaAree la = new ListaAree();
		ListaAree lt = this;

		if ((denominazione != null) && (denominazione != "")) {
			for (AreaGeografica areaGeografica : lt) {
				if (areaGeografica.getDenominazione().toLowerCase().contains(denominazione.toLowerCase()))
					la.addFirst(areaGeografica);
			}

			lt = la;
			la = null;
		}

		if ((stato != null) && (stato != "")) {
			for (AreaGeografica areaGeografica : lt) {
				if (areaGeografica.getStato().toLowerCase().contains(stato.toLowerCase()))
					la.addFirst(areaGeografica);
			}

			lt = la;
			la = null;
		}

		return lt;
	}

	@Override
	public Result<AreaGeografica> cercaAreeGeografiche(double latitudine, double longitudine) {

		if ((latitudine < -90) || (latitudine > 90)) {
			return new Result<>(1, "hai inserito valori errati riprova");
		}
		if ((longitudine < -180) || (longitudine > 180)) {
			return new Result<>(1, "hai inserito valori errati riprova");
		}

		for (AreaGeografica areaGeografica : this) {
			if ((latitudine == areaGeografica.getLatitudine()) && (longitudine == areaGeografica.getLongitudine()))
				return new Result<>(areaGeografica);
		}

		AreaGeografica ag = this.getFirst();
		double differenzalat = latitudine - ag.getLatitudine();
		differenzalat *= differenzalat;

		double differenzalong = longitudine - ag.getLongitudine();
		differenzalong *= differenzalong;

		double min = Math.sqrt(differenzalat + differenzalong);
		for (AreaGeografica areaGeografica : this) {

			differenzalat = latitudine - areaGeografica.getLatitudine();
			differenzalat *= differenzalat;

			differenzalong = longitudine - areaGeografica.getLongitudine();
			differenzalong *= differenzalong;

			double dist = Math.sqrt(differenzalat + differenzalong);

			if (min > dist) {
				min = dist;
				ag = areaGeografica;
			}

		}
		return new Result<>(ag);

	}

	@Override
	public String toString() {
		String str = "";
		for (AreaGeografica tmp : this)
			str += tmp.toString() + "\n";
		return str;
	}

}

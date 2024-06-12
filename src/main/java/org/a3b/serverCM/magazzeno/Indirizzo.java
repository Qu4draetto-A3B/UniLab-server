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

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * La classe {@code Indirizzo} rappresenta un indirizzo identificato
 * da: nome della via, numero civico, CAP, comune di appartenenza e provincia.
 */
@Data
public class Indirizzo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private String nomeVia;
	private int civico;
	private int cap;
	private String comune;
	private String provincia;

	/**
	 * Costruttore di un'istanza di {@code Indirizzo} con valori predefiniti.
	 * <p>
	 * Il {@link #nomeVia} della via viene impostato su "<i>Via Durin I</i>", il {@link #civico} su "<i>42</i>",
	 * il {@link #cap} su "<i>12345</i>", il {@link #comune} su "<i>Westfalia</i>" e la {@link #provincia} su "<i>Norrenia</i>".
	 */

	public Indirizzo() {
		nomeVia = "Via Durin I";
		civico = 42;
		cap = 12345;
		comune = "Westfalia";
		provincia = "Norrenia";
	}

	/**
	 * Costruttore di un'istanza di {@link Indirizzo}.
	 *
	 * @param nomeVia   nome della via relativa all'indirizzo
	 * @param civico    numero civico relativo all'indirizzo
	 * @param cap       CAP relativo all'indirizzo
	 * @param comune    comune relativo all'indirizzo
	 * @param provincia provincia relativa all'indirizzo
	 */
	public Indirizzo(String nomeVia, int civico, int cap, String comune, String provincia) {
		if (cap >= 100000)
			throw new IllegalArgumentException("CAP invalido");
		this.nomeVia = nomeVia;
		this.civico = civico;
		this.cap = cap;
		this.comune = comune;
		this.provincia = provincia;
	}

	/**
	 * Restituisce il nome della via.
	 *
	 * @return {@link #nomeVia} relativo all'{@code Indirizzo}
	 */
	public String getNomeVia() {
		return nomeVia;
	}

	/**
	 * Restituisce il numero civico.
	 *
	 * @return {@link #civico} relativo all'{@code Indirizzo}
	 */
	public int getCivico() {
		return civico;
	}

	/**
	 * Imposta il numero civico di un indirizzo.
	 *
	 * @param civico numero civico da impostare nell'{@link Indirizzo}
	 */
	public void setCivico(int civico) {
		this.civico = civico;
	}

	/**
	 * Restituisce il CAP.
	 *
	 * @return {@link #cap} relativo all'{@code Indirizzo}
	 */
	public int getCap() {
		return cap;
	}

	/**
	 * Restituisce il comune di appartenenza.
	 *
	 * @return {@link #comune} relativo all'{@code Indirizzo}
	 */
	public String getComune() {
		return comune;
	}

	/**
	 * Restituisce la provincia.
	 *
	 * @return {@link #provincia} relativa all'{@code Indirizzo}
	 */
	public String getProvincia() {
		return provincia;
	}

	@Override
	public String toString() {
		return String.format(
				"%s: (\n\tnomeVia: %s\n\tCivico: %d\n\tCap: %d\n\tComune: %s\n\tProvincia: %s\n)",
				super.toString(), nomeVia, civico, cap, comune, provincia);
	}
}

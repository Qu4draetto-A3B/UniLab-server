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
package org.a3b.commons.magazzeno;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * La classe {@code CentroMonitoraggio} rappresenta un centro di monitoraggio
 * identificato da nome, indirizzo e aree associate.
 */
@Data
public class CentroMonitoraggio implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private String nome;
	private Indirizzo indirizzo;
	private ListaAree aree;

	/**
	 * Costruttore di un'istanza di {@code CentroMonitoraggio}.
	 *
	 * @param nome      nome del centro di monitoraggio
	 * @param indirizzo indirizzo relativo al centro di monitoraggio
	 * @param lag       lista delle aree associate al centro di monitoraggio
	 */

	public CentroMonitoraggio(String nome, Indirizzo indirizzo, ListaAree lag) {
		this.nome = nome;
		this.indirizzo = indirizzo;
		this.aree = lag;
	}

	/**
	 * Costruttore di un'istanza di {@code CentroMonitoraggio} con valori predefiniti.
	 * <p>
	 * Il {@link #nome} del centro viene impostato su "<i>Torre Civile</i>", mentre l'{@link #indirizzo} e la
	 * lista delle {@link #aree} vengono inizializzati rispettivamente con un nuovo oggetto
	 * di tipo {@link Indirizzo} e una nuova {@link ListaAree} vuota.
	 */

	public CentroMonitoraggio() {
		nome = "Torre Civile";
		indirizzo = new Indirizzo();
		aree = new ListaAree();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(String.format(
				"%s: (\n\tNome: %s\n\tIndirizzo: %s, %d, %d, %s (%s)",
				super.toString(), nome,
				indirizzo.getNomeVia(), indirizzo.getCivico(),
				indirizzo.getCap(), indirizzo.getComune(), indirizzo.getProvincia()));

		for (AreaGeografica area : aree) {
			sb.append(String.format("\n\t\t%s", area));
		}
		sb.append("\n)");

		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof CentroMonitoraggio cm)) {
			return super.equals(obj);
		}

		return nome.equals(cm.getNome());
	}
}

/**
 * La classe {@code Indirizzo} rappresenta un indirizzo identificato
 * da: nome della via, numero civico, CAP, comune di appartenenza e provincia.
 */
@Data
class Indirizzo implements Serializable {
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

	@Override
	public String toString() {
		return String.format(
				"%s: (\n\tnomeVia: %s\n\tCivico: %d\n\tCap: %d\n\tComune: %s\n\tProvincia: %s\n)",
				super.toString(), nomeVia, civico, cap, comune, provincia);
	}
}

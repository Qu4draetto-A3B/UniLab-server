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
	private long CenterID;
	private String nome;
	private ListaAree aree;
	private String nomeVia;
	private int civico;
	private int cap;
	private String comune;
	private String provincia;

	/**
	 * Costruttore di un'istanza di {@code CentroMonitoraggio}.
	 *
	 * @param nome      nome del centro di monitoraggio
	 * @param indirizzo indirizzo relativo al centro di monitoraggio
	 * @param lag       lista delle aree associate al centro di monitoraggio
	 */

	public CentroMonitoraggio(long CenterID, String nome, String via, int civico, int cap, String comune, String provincia, ListaAree lag) {
		if (cap >= 100000)
			throw new IllegalArgumentException("CAP invalido");
		this.CenterID = CenterID;
		this.nome = nome;
		this.aree = lag;
		this.nomeVia = via;
		this.civico = civico;
		this.cap = cap;
		this.comune = comune;
		this.provincia = provincia;
	}

	/**
	 * Costruttore di un'istanza di {@code CentroMonitoraggio} con valori predefiniti.
	 * <p>
	 * Il {@link #nome} del centro viene impostato su "<i>Torre Civile</i>", mentre l'{@link #indirizzo} e la
	 * lista delle {@link #aree} vengono inizializzati rispettivamente con un nuovo oggetto
	 * di tipo {@link Indirizzo} e una nuova {@link ListaAree} vuota.
	 */

	public CentroMonitoraggio() {
		nome = "Centro non trovato";
		nomeVia = "Via Delle Vie";
		civico = 0;
		cap = 0;
		comune = "Atlantide";
		provincia = "I Sette Mari";
		aree = new ListaAree();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(String.format(
				"%s: (\n\tNome: %s\n\tIndirizzo: %s, %d, %d, %s (%s)",
				super.toString(), nome,
				nomeVia, civico, cap, comune, provincia));

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
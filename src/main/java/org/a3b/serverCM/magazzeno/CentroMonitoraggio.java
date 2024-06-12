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

	/**
	 * Restituisce il nome del centro di monitoraggio.
	 *
	 * @return {@link #nome} relativo al centro di monitoraggio
	 */

	public String getNome() {
		return nome;
	}

	/**
	 * Restituisce la lista di aree associate al centro di monitoraggio.
	 *
	 * @return {@link #aree} associate al {@code CentroMonitoraggio}
	 */

	public ListaAree getListaAree() {
		return this.aree;
	}

	/**
	 * Restituisce l'indirizzo relativo al centro di monitoraggio.
	 *
	 * @return {@link #indirizzo} relativo al {@code CentroMonitoraggio}
	 */

	public Indirizzo getIndirizzo() {
		return indirizzo;
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

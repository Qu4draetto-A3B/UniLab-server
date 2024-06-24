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
import org.a3b.commons.utils.TipoDatoGeografico;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * La classe {@code Operatore} rappresenta un operatore identificato
 * da: nome, cognome, e-mail, codice fiscale, user ID e centro di monitoraggio
 * associato.
 * <p>
 * Questa classe implementa l'interfaccia {@link Serializable} per consentire la
 * serializzazione dei dati.
 */

@Data
public class Operatore implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private String nome;
	private String cognome;
	private String email;
	private String cf;
	private long uid;
	private CentroMonitoraggio centro;

	/**
	 * Costruttore di un'istanza di {@code Operatore}
	 *
	 * @param cf      codice fiscale dell'operatore
	 * @param uid     user ID relativo all'operatore
	 * @param nome    nome dell'operatore
	 * @param cognome cognome dell'operatore
	 * @param email   e-mail dell'operatore
	 * @param centro  centro di monitoraggio a cui l'operatore &egrave associato
	 */
	public Operatore(long uid, String nome, String cognome, String cf, String email, CentroMonitoraggio centro) {
		this.cf = cf;
		this.uid = uid;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.centro = centro;
	}

	/**
	 * Costruttore di un'istanza di {@code Operatore} con valori predefiniti.
	 * <p>
	 * Il {@link #cf} viene impostato su "<i>CIVILE</i>", lo {@link #uid} su "<i>civile</i>",
	 * il {@link #nome} su "<i>Civile</i>", il {@link #cognome} su"<i>Civile</i>" e la {@link #email} su "<i>civile@example.com</i>".
	 * Il {@link #centro} viene inizializzato con un nuovo {@link CentroMonitoraggio} predefinito.
	 */
	public Operatore() {
		cf = "CIVILE";
		uid = 0000;
		nome = "Civile";
		cognome = "Civile";
		email = "civile@example.com";
		centro = new CentroMonitoraggio();
	}

	/**
	 * Imposta i dati climatici di una determinata area nel database.
	 *
	 * @param area  {@link AreaGeografica} relativa ai dati
	 * @param tempo data e ora in cui avviene l'inserimento dei dati nel database
	 */
	public Misurazione inserisciParametri(AreaGeografica area, LocalDateTime tempo, HashMap<TipoDatoGeografico, Byte> dati, HashMap<TipoDatoGeografico, String> note) {
		return new Misurazione(0, tempo, this, centro, area, dati, note);
	}

	@Override
	public String toString() {
		return String.format(
				"%s: (\n\tCF: %s\n\tUserID: %s\n\tNome: %s\n\tCognome: %s\n\tEmail: %s\n\tCentro: %s\n)",
				super.toString(), cf, uid, nome, cognome, email, centro.getNome());
	}
	/**
	 * Formatta una stringa con tutti gli attributi dell'{@code Operatore}.
	 *
	 * @return la stringa formatta con gli attributi dell'{@code Operatore}.
	 */
	public String toStringPretty() {
		return String.format(
				"C.F.\t: %s\nUser ID\t: %s\nNome\t: %s %s\nEmail\t: %s\nCentro\t: %s",
				cf, uid, nome, cognome, email, centro.getNome());
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Operatore op)) {
			return super.equals(obj);
		}
		return cf.equals(op.getCf());
	}
}

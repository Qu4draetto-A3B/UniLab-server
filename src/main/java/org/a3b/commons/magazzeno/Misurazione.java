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
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

/**
 * La classe {@code Misurazione} rappresenta una misurazione identificata
 * da: ID, dato geografico, data e ora, operatore, centro di monitoraggio e area
 * geografica.
 */
@Data
public class Misurazione implements Serializable {
	public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.RFC_1123_DATE_TIME.localizedBy(Locale.getDefault());
	@Serial
	private static final long serialVersionUID = 1L;
	private long rid;
	private HashMap<TipoDatoGeografico, Byte> dati;
	private HashMap<TipoDatoGeografico, String> note;
	private LocalDateTime time;
	private Operatore operatore;
	private CentroMonitoraggio centro;
	private AreaGeografica area;

	/**
	 * Costruttore di un'istanza di {@code Misurazione}.
	 *
	 * @param rid       ID del record della misurazione
	 * @param dati      dato geografico relativo alla misurazione
	 * @param operatore operatore relativo alla misurazione
	 * @param area      area geografica relativa alla misurazione
	 */
	public Misurazione(long rid, Operatore operatore, AreaGeografica area, HashMap<TipoDatoGeografico, Byte> dati, HashMap<TipoDatoGeografico, String> note) {
		this.dati = dati;
		this.note = note;
		this.operatore = operatore;
		this.area = area;
		time = LocalDateTime.now();
		centro = operatore.getCentro();
	}

	/**
	 * Costruttore di un'istanza di {@code Misurazione}
	 *
	 * @param rid       ID relativo al record della misurazione
	 * @param dateTime  data e ora relativa all'inserimento della misurazione
	 * @param operatore operatore relativo alla misurazione
	 * @param centro    centro di monitoraggio relativo alla misurazione
	 * @param area      area geografica relativa alla misurazione
	 * @param dati      dato geografico relativo alla misurazione
	 */
	public Misurazione(long rid, LocalDateTime dateTime, Operatore operatore, CentroMonitoraggio centro, AreaGeografica area, HashMap<TipoDatoGeografico, Byte> dati, HashMap<TipoDatoGeografico, String> note) {
		this.dati = dati;
		this.note = note;
		this.operatore = operatore;
		this.area = area;
		time = dateTime;
		this.centro = centro;
	}

	/**
	 * Raccolta dei parametri relativi ad una {@link Misurazione}
	 *
	 * @param altitudineGhiacciai altitudine dei ghiacciai
	 * @param massaGhiacciai massa dei ghiacciai
	 * @param precipitazioni volume delle precipitazioni
	 * @param pressione pressione dell'area
	 * @param temperatura gradi dell'area
	 * @param umidita precentuale dell'umidita'
	 * @param vento velocita' del vento
	 * @return l'hash map con i relativi valori dei parametri inseriti
	 */
	public static HashMap<TipoDatoGeografico, Byte> buildDati(byte altitudineGhiacciai, byte massaGhiacciai, byte precipitazioni, byte pressione, byte temperatura, byte umidita, byte vento) {
		HashMap<TipoDatoGeografico, Byte> dati = new HashMap<>();
		dati.put(TipoDatoGeografico.AltitudineGhiacciai, altitudineGhiacciai);
		dati.put(TipoDatoGeografico.MassaGhiacciai, massaGhiacciai);
		dati.put(TipoDatoGeografico.Precipitazioni, precipitazioni);
		dati.put(TipoDatoGeografico.Pressione, pressione);
		dati.put(TipoDatoGeografico.Temperatura, temperatura);
		dati.put(TipoDatoGeografico.Umidita, umidita);
		dati.put(TipoDatoGeografico.Vento, vento);
		return dati;
	}

	/**
	 * Raccolta delle note relative ad una {@link Misurazione}
	 *
	 * @param altitudineGhiacciai altitudine dei ghiacciai
	 * @param massaGhiacciai massa dei ghiacciai
	 * @param precipitazioni volume delle precipitazioni
	 * @param pressione pressione dell'area
	 * @param temperatura gradi dell'area
	 * @param umidita precentuale dell'umidita'
	 * @param vento velocita' del vento
	 * @return l'hash map con i relativi valori dei parametri inseriti
	 */
	public static HashMap<TipoDatoGeografico, String> buildNote(String altitudineGhiacciai, String massaGhiacciai, String precipitazioni, String pressione, String temperatura, String umidita, String vento) {
		HashMap<TipoDatoGeografico, String> dati = new HashMap<>();
		dati.put(TipoDatoGeografico.AltitudineGhiacciai, altitudineGhiacciai);
		dati.put(TipoDatoGeografico.MassaGhiacciai, massaGhiacciai);
		dati.put(TipoDatoGeografico.Precipitazioni, precipitazioni);
		dati.put(TipoDatoGeografico.Pressione, pressione);
		dati.put(TipoDatoGeografico.Temperatura, temperatura);
		dati.put(TipoDatoGeografico.Umidita, umidita);
		dati.put(TipoDatoGeografico.Vento, vento);
		return dati;
	}

	/**
	 * Restituisce il dato della misurazione.
	 *
	 * @return dato relativo alla {@code Misurazione}
	 */
	public byte getDato(TipoDatoGeografico tipo) {
		return dati.get(tipo);
	}

	/**
	 * Restituisce la nota della misurazione.
	 *
	 * @return nota relativa alla {@code Misurazione}
	 */
	public String getNota(TipoDatoGeografico tipo) {
		return note.get(tipo);
	}

	/**
	 * Restituisce una stringa che rappresento la data
	 *
	 * @return stringa relativo al formato data
	 */
	public String getTimeString() {
		return time.format(DATE_TIME_FORMAT);
	}

	/**
	 * Formatta in una stringa il {@link TipoDatoGeografico} con tutti gli attributi
	 *
	 * @return il {@link TipoDatoGeografico} formattato in una stringa.
	 */
	public String toString() {
		StringBuilder dato = new StringBuilder();

		for (TipoDatoGeografico tipo : TipoDatoGeografico.values()) {
			dato.append(String.format("%s: %d, '%s'", tipo.name(), getDato(tipo), getNota(tipo)));
		}

		return String.format("%s <<<\n- DateTime: \n%s\n- AreaGeografica: \n%s\n- Operatore: \n%s\n- Centro: \n%s\n- Dato: \n%s\n>>> %s", super.toString(), getTimeString(), area, operatore, centro, dato, super.toString());
	}
}

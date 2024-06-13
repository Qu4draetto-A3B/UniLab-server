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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * La classe {@code Misurazione} rappresenta una misurazione identificata
 * da: ID, dato geografico, data e ora, operatore, centro di monitoraggio e area
 * geografica.
 */
@Data
public class Misurazione implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private long rid;
	private DatoGeografico dato;
	private LocalDateTime time;
	private Operatore operatore;
	private CentroMonitoraggio centro;
	private AreaGeografica area;
	public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ISO_INSTANT;

	/**
	 * Costruttore di un'istanza di {@code Misurazione}.
	 *
	 * @param rid       ID del record della misurazione
	 * @param dato      dato geografico relativo alla misurazione
	 * @param operatore operatore relativo alla misurazione
	 * @param area      area geografica relativa alla misurazione
	 */
	public Misurazione(long rid, DatoGeografico dato, Operatore operatore, AreaGeografica area) {
		this.dato = dato;
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
	 * @param dato      dato geografico relativo alla misurazione
	 */
	public Misurazione(long rid, LocalDateTime dateTime, Operatore operatore, CentroMonitoraggio centro,
					   AreaGeografica area, DatoGeografico dato) {
		this.dato = dato;
		this.operatore = operatore;
		this.area = area;
		time = LocalDateTime.now();
		centro = operatore.getCentro();
	}

	/**
	 * Restituisce l'ID della misurazione.
	 *
	 * @return {@link #rid} relativo alla {@code Misurazione}
	 */
	public long getRid() {
		return rid;
	}

	/**
	 * Restituisce il dato della misurazione.
	 *
	 * @return {@link #dato} relativo alla {@code Misurazione}
	 */
	public DatoGeografico getDato() {
		return this.dato;
	}

	/**
	 * Restituisce data e ora della misurazione.
	 *
	 * @return {@link #time} relativo alla {@code Misurazione}
	 */
	public LocalDateTime getTime() {
		return this.time;
	}

	/**
	 * Restituisce la stringa relativa a data e ora della misurazione.
	 *
	 * @return stringa formattata relativa a data e ora della {@code Misurazione}
	 */
	public String getTimeString() {
		return time.format(DATE_TIME_FORMAT);
	}

	/**
	 * Restituisce l'operatore della misurazione.
	 *
	 * @return {@link #operatore} relativo alla {@code Misurazione}
	 */
	public Operatore getOperatore() {
		return this.operatore;
	}

	/**
	 * Restituisce il centro di monitoraggio della misurazione.
	 *
	 * @return {@link #centro} relativo alla {@code Misurazione}
	 */
	public CentroMonitoraggio getCentro() {
		return this.centro;
	}

	/**
	 * Restituisce l'area geografica della misurazione.
	 *
	 * @return {@link #area} relativo alla {@code Misurazione}
	 */
	public AreaGeografica getArea() {
		return this.area;
	}

	@Override
	public String toString() {
		return String.format(
				"%s <<<\n- DateTime: \n%s\n- AreaGeografica: \n%s\n- Operatore: \n%s\n- Centro: \n%s\n- Dato: \n%s\n>>> %s",
				super.toString(), getTimeString(),
				area, operatore, centro, dato, super.toString());
	}
}

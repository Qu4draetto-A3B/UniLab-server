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
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import org.a3b.serverCM.utils.DataTable;
import org.a3b.serverCM.utils.TipoDatoGeografico;

/**
 * La classe {@code DatGeografico} rappresenta un dato geografico
 * identificato da ID, massa dei ghiacciai, altitudine dei ghiacciai,
 * precipitazioni, temperatura, pressione, umidit&agrave e vento.
 * <p>
 * Questa classe implementa l'interfaccia {@link DataTable} per consentire la
 * gestione dei dati.
 */
@Data
public class DatoGeografico implements DataTable, Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private long rid;
	private byte massaGhiacciai;
	private byte altitudineGhiacciai;
	private byte precipitazioni;
	private byte temperatura;
	private byte pressione;
	private byte umidita;
	private byte vento;

	private HashMap<TipoDatoGeografico, String> note;

	/**
	 * Costruttore di un'istanza di {@code DatoGeografico}.
	 *
	 * @param rid  ID relativo al record del dato
	 * @param tipo {@link TipoDatoGeografico} (tipo del dato geografico)
	 * @param dato valore da 0 (valore nullo, di default) a 5 che rappresenta il
	 *             livello di criticit&agrave del dato geografico
	 * @param nota note relative al dato geografico
	 */

	public DatoGeografico(long rid, TipoDatoGeografico tipo, byte dato, String nota) {
		this.rid = rid;
		setDato(tipo, dato);

		note = new HashMap<>();
		for (TipoDatoGeografico t : TipoDatoGeografico.values()) {
			note.put(t, "");
		}

		if (!setNota(tipo, nota))
			throw new IllegalArgumentException("Nota troppo lunga");
	}

	/**
	 * Costruttore di un'istanza di {@code DatoGeografico}.
	 *
	 * @param rid                  ID relativo al record del dato
	 * @param massaGhiacciai       valore da 0 (valore nullo, di default) a 5
	 *                             relativo alla criticita' della massa dei
	 *                             ghiacciai
	 * @param altituidineGhiacciai valore da 0 (valore nullo, di default) a 5
	 *                             relativo alla criticita' dell'altitudine dei
	 *                             ghiacciai
	 * @param precipitazioni       valore da 0 (valore nullo, di default) a 5
	 *                             relativo alla criticita' delle precipitazioni
	 * @param temperatura          valore da 0 (valore nullo, di default) a 5
	 *                             relativo alla criticita' della temperatura
	 * @param pressione            valore da 0 (valore nullo, di default) a 5
	 *                             relativo alla criticita' della pressione
	 * @param umidita              valore da 0 (valore nullo, di default) a 5
	 *                             relativo alla criticita' dell'umidita'
	 * @param vento                valore da 0 (valore nullo, di default) a 5
	 *                             relativo alla criticita' dei venti
	 * @param note                 {@link HashMap} contenente le note relative al
	 *                             dato geografico
	 */

	public DatoGeografico(long rid, byte massaGhiacciai, byte altitudineGhiacciai, byte precipitazioni,
			byte temperatura,
			byte pressione, byte umidita, byte vento, HashMap<TipoDatoGeografico, String> note) {
		/*
		 * I byte vengono inizializzati a 0 implicitamente,
		 * se tutti i dati sono 0 anche la loro somma sara' 0,
		 * quindi posso dedurre che non sono stati inseriti dati
		 */
		int all = massaGhiacciai + altitudineGhiacciai + precipitazioni + temperatura + pressione + umidita + vento;
		if (all < 1 || all > 30) {
			throw new IllegalArgumentException("Almeno un dato deve essere > 0 in DatoGeografico");
		}
		this.rid = rid;

		for (String nota : note.values()) {
			if (nota.length() > 255) {
				throw new IllegalArgumentException("Nota troppo lunga");
			}
		}

		this.massaGhiacciai = massaGhiacciai;
		this.altitudineGhiacciai = altitudineGhiacciai;
		this.precipitazioni = precipitazioni;
		this.temperatura = temperatura;
		this.pressione = pressione;
		this.umidita = umidita;
		this.vento = vento;
		this.note = note;
	}

	/**
	 * Costruttore di un'istanza di {@code DatoGeografico}.
	 *
	 * @param rid  ID del dato
	 * @param dati {@link Map} contenente i valori dei dati
	 * @param note {@link Map} contenente le note relative al dato
	 */

	public DatoGeografico(long rid, Map<TipoDatoGeografico, Byte> dati, Map<TipoDatoGeografico, String> note) {
		this.rid = rid;
		this.note = new HashMap<>();

		if (dati == null) {
			dati = new HashMap<>();
		}
		if (note == null) {
			note = new HashMap<>();
		}

		for (TipoDatoGeografico tipo : TipoDatoGeografico.values()) {
			try {
				setDato(tipo, dati.get(tipo));
			} catch (NullPointerException e) {
				setDato(tipo, (byte) 0);
			}
			try {
				setNota(tipo, note.get(tipo));
			} catch (NullPointerException e) {
				setNota(tipo, "");
			}
		}
	}

	/**
	 * Restituisce l'ID del dato geografico.
	 *
	 * @return {@link #rid} relativo al {@code DatoGeografico}
	 */
	public long getRid() {
		return rid;
	}

	/**
	 * Imposta il valore di un determinato tipo di dato geografico.
	 * <p>
	 * Controlla che il valore fornito sia valido e lo assegna al relativo tipo.
	 *
	 * @throws IllegalArgumentException se il valore del dato non &egrave compreso
	 *                                  tra 0 e 5
	 * @param tipo {@link TipoDatoGeografico}
	 * @param dato valore del dato geografico
	 */

	private void setDato(TipoDatoGeografico tipo, byte dato) {
		if (dato < 0 || dato > 5) {
			throw new IllegalArgumentException("dato non e' 0 >= X >= 5");
		}

		switch (tipo) {
			case MassaGhiacciai:
				massaGhiacciai = dato;
				break;

			case AltitudineGhiacciai:
				altitudineGhiacciai = dato;
				break;

			case Precipitazioni:
				precipitazioni = dato;
				break;

			case Pressione:
				pressione = dato;
				break;

			case Temperatura:
				temperatura = dato;
				break;

			case Umidita:
				umidita = dato;
				break;

			case Vento:
				vento = dato;
				break;
		}
	}

	/**
	 * Restituisce il valore di un determinato tipo di dato geografico.
	 *
	 * @param tipo {@link TipoDatoGeografico}
	 * @return valore del dato geografico
	 */

	public byte getDato(TipoDatoGeografico tipo) {
		byte dato = 0;

		switch (tipo) {
			case MassaGhiacciai:
				dato = massaGhiacciai;
				break;

			case AltitudineGhiacciai:
				dato = altitudineGhiacciai;
				break;

			case Precipitazioni:
				dato = precipitazioni;
				break;

			case Pressione:
				dato = pressione;
				break;

			case Temperatura:
				dato = temperatura;
				break;

			case Umidita:
				dato = umidita;
				break;

			case Vento:
				dato = vento;
				break;
		}

		return dato;
	}

	/**
	 * Restituisce la nota relativa al dato geografico.
	 *
	 * @param key {@link TipoDatoGeografico} (chiave per recuperare la nota)
	 * @return nota relativa al dato geografico
	 */

	public String getNota(TipoDatoGeografico key) {
		return note.get(key);
	}

	/**
	 * Imposta la nota relative al dato geografico.
	 * <p>
	 * Restituisce {@code false} se la nota fornita supera i 255 caratteri.
	 *
	 * @param key  {@link TipoDatoGeografico} (chiave per inserire la nota)
	 * @param nota nota relative al dato geografico
	 * @return {@code boolean} che indica se la nota &egrave stata impostata
	 *         correttamente
	 */

	private boolean setNota(TipoDatoGeografico key, String nota) {
		if (nota.length() > 255) {
			return false;
		}
		note.put(key, nota);
		return true;
	}

	/**
	 * Restituisce i dati relativi al dato geografico.
	 *
	 * @return {@link HashMap} contenente i dati relativi al dato geografico
	 */
	public HashMap<TipoDatoGeografico, Byte> getDati() {
		HashMap<TipoDatoGeografico, Byte> dato = new HashMap<>();
		dato.put(TipoDatoGeografico.AltitudineGhiacciai, getDato(TipoDatoGeografico.AltitudineGhiacciai));
		dato.put(TipoDatoGeografico.MassaGhiacciai, getDato(TipoDatoGeografico.MassaGhiacciai));
		dato.put(TipoDatoGeografico.Precipitazioni, getDato(TipoDatoGeografico.Precipitazioni));
		dato.put(TipoDatoGeografico.Pressione, getDato(TipoDatoGeografico.Pressione));
		dato.put(TipoDatoGeografico.Temperatura, getDato(TipoDatoGeografico.Temperatura));
		dato.put(TipoDatoGeografico.Umidita, getDato(TipoDatoGeografico.Umidita));
		dato.put(TipoDatoGeografico.Vento, getDato(TipoDatoGeografico.Vento));
		return dato;
	}

	/**
	 * Restituisce le note relative al dato geografico.
	 *
	 * @return {@link HashMap} contenente le note relative al dato geografico
	 */
	public HashMap<TipoDatoGeografico, String> getNote() {
		return note;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DatoGeografico)) {
			return super.equals(obj);
		}

		DatoGeografico dato = (DatoGeografico) obj;

		return dato.getRid() == rid;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s: (\n", super.toString()));
		for (TipoDatoGeografico tipo : TipoDatoGeografico.values()) {
			sb.append(String.format("\t%s: %s '%s'\n", tipo.name(), getDato(tipo), getNota(tipo)));
		}
		sb.append(")");
		return sb.toString();
	}

	/**
	 * Recupera le note del dato geografico e le confronta con quelle del dato
	 * geografico fornito.
	 *
	 * @param dato {@link DatoGeografico} con cui confrontare le note
	 * @return {@code boolean} che indica se le note dei dati geografici sono
	 *         uaguali
	 */

	public boolean noteEquals(DatoGeografico dato) {
		boolean res = true;

		for (TipoDatoGeografico tipo : TipoDatoGeografico.values()) {
			res &= getNota(tipo).equals(dato.getNota(tipo));
		}

		return res;
	}

	/**
	 * Recupera i valori dei dati del dato geografico e li confronta con quelli del
	 * dato
	 * geografico fornito.
	 *
	 * @param dato {@link DatoGeografico} con cui confrontare i valori dei dati
	 * @return {@code boolean} che indica se i valori dei dati dei dati geografici
	 *         sono uguali
	 */

	public boolean datoEquals(DatoGeografico dato) {
		boolean res = true;

		for (TipoDatoGeografico tipo : TipoDatoGeografico.values()) {
			res &= getDato(tipo) == dato.getDato(tipo);
		}

		return res;
	}
}

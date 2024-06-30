package org.a3b.commons.magazzeno;

import org.a3b.commons.utils.TipoDatoGeografico;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Wrapper intorno a {@link ConcurrentLinkedDeque} adibito alla gestione di {@link Misurazione}
 */
public class ListaMisurazioni extends ConcurrentLinkedDeque<Misurazione> {
	public ListaMisurazioni() {
		//
	}

	/**
	 * Metodo che ritorna l'elemento in posizione {@code idx} della {@code ListaMisurazioni}
	 *
	 * @param idx indice dell'elemento
	 * @return elemento all'indice {@code idx}
	 */
	public Misurazione get(int idx) {
		int i = 0;
		for (Misurazione mis : this) {
			if (i++ == idx) {
				return mis;
			}
		}
		return null;
	}


	/**
	 * Metodo che ritorna la {@link Misurazione} specificata
	 *
	 * @param rid RecordID della misurazione
	 * @return la misurazione con RecordID == a {@code rid}
	 */
	public Misurazione get(long rid) {
		for (Misurazione mis : this) {
			if (mis.getRid() == rid) {
				return mis;
			}
		}
		return null;
	}

	/**
	 * Metodo che rimuove ogni misurazione che non è stata fatta dall'utente dato
	 *
	 * @param userID l'operatore da filtrare
	 * @return una nuova lista con solo misurazioni dell'operatore
	 */
	public ListaMisurazioni filterOperator(long userID) {
		ListaMisurazioni lm = new ListaMisurazioni();
		for (Misurazione mis : this) {
			if (mis.getOperatore().getUid() == userID) {
				lm.offer(mis);
			}
		}
		return lm;
	}

	/**
	 * Metodo che rimuove ogni misurazione che non è stata fatta dal centro dato
	 *
	 * @param centerID il centro da filtrare
	 * @return una nuova lista con solo misurazioni dal centro dato
	 */
	public ListaMisurazioni filterCenter(long centerID) {
		ListaMisurazioni lm = new ListaMisurazioni();
		for (Misurazione mis : this) {
			if (mis.getCentro().getCenterID() == centerID) {
				lm.offer(mis);
			}
		}
		return lm;
	}

	/**
	 * Metodo che rimuove ogni misurazione che non è stata fatta nell'area data
	 *
	 * @param geoID l'area da filtrare
	 * @return una nuova lista con solo misurazioni dall'area data
	 */
	public ListaMisurazioni filterArea(long geoID) {
		ListaMisurazioni lm = new ListaMisurazioni();
		for (Misurazione mis : this) {
			if (mis.getArea().getGeoID() == geoID) {
				lm.offer(mis);
			}
		}
		return lm;
	}

	/**
	 * Metodo che filtra le misurazioni in base alla data di immissione nel sistema
	 *
	 * @param start Data di inizio del periodo di immissione
	 * @param end   Data di fine del periodo di immissione
	 * @return una nuova lista con solo misurazioni fatte nel lasso di tempo indicato
	 */
	public ListaMisurazioni filterTimestamp(LocalDateTime start, LocalDateTime end) {
		if (start == null) {
			start = LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0, 0);
		}
		if (end == null) {
			end = LocalDateTime.of(2970, Month.JANUARY, 1, 0, 0, 0);
		}

		ListaMisurazioni lm = new ListaMisurazioni();
		for (Misurazione mis : this) {
			LocalDateTime time = mis.getTime();
			if (time.isAfter(start) && time.isBefore(end)) {
				lm.offer(mis);
			}
		}
		return lm;
	}

	/**
	 * Calcolo della media dei dati di una certa area geografica
	 *
	 * @param area l'area di cui fare la media
	 * @return la media dei dati riguardanti l'area
	 */
	public Misurazione visualizzaAreaGeografica(AreaGeografica area) {
		ListaMisurazioni lm = filterArea(area.getGeoID());
		Operatore srv = new Operatore(0,
				"Server",
				" Monitoraggio",
				"",
				"server@example.org",
				new CentroMonitoraggio(
						0,
						"Server Monitoraggio",
						"Via Ravasi",
						2,
						21000,
						"Varese",
						"Varese",
						new ListaAree()
				));

		int count = 0;
		HashMap<TipoDatoGeografico, Integer> aggragates = new HashMap<>();
		for (TipoDatoGeografico tipo : TipoDatoGeografico.values()) {
			aggragates.put(tipo, 0);
		}

		for (Misurazione mis : lm) {
			for (TipoDatoGeografico tipo : TipoDatoGeografico.values()) {
				aggragates.put(tipo, aggragates.get(tipo) + mis.getDato(tipo));
			}
			count++;
		}

		HashMap<TipoDatoGeografico, Byte> dati = new HashMap<>();
		for (TipoDatoGeografico tipo : TipoDatoGeografico.values()) {
			dati.put(tipo, (byte) (aggragates.get(tipo) / count));
		}

		return new Misurazione(0,
				srv,
				area,
				dati,
				null);
	}
}

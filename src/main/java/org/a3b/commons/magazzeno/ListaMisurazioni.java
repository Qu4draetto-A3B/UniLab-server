package org.a3b.commons.magazzeno;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ListaMisurazioni extends ConcurrentLinkedDeque<Misurazione> {
	public ListaMisurazioni() {
		//
	}

	public Misurazione get(int idx) {
		int i = 0;
		for (Misurazione mis : this) {
			if (i++ == idx) {
				return mis;
			}
		}
		return null;
	}

	public Misurazione get(long rid) {
		for (Misurazione mis : this) {
			if (mis.getRid() == rid) {
				return mis;
			}
		}
		return null;
	}

	public ListaMisurazioni filterOperator(long userID) {
		ListaMisurazioni lm = new ListaMisurazioni();
		for (Misurazione mis : this) {
			if (mis.getOperatore().getUid() == userID) {
				lm.offer(mis);
			}
		}
		return lm;
	}

	public ListaMisurazioni filterCenter(long centerID) {
		ListaMisurazioni lm = new ListaMisurazioni();
		for (Misurazione mis : this) {
			if (mis.getCentro().getCenterID() == centerID) {
				lm.offer(mis);
			}
		}
		return lm;
	}

	public ListaMisurazioni filterArea(long geoID) {
		ListaMisurazioni lm = new ListaMisurazioni();
		for (Misurazione mis : this) {
			if (mis.getArea().getGeoID() == geoID) {
				lm.offer(mis);
			}
		}
		return lm;
	}

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
			if (time.isAfter(start) || time.isBefore(end)) {
				lm.offer(mis);
			}
		}
		return lm;
	}
}

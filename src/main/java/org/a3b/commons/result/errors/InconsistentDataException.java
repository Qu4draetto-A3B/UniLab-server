package org.a3b.commons.result.errors;

import org.a3b.commons.result.ResultException;

public class InconsistentDataException extends ResultException {
	public InconsistentDataException(String msg) {
		super(msg);
	}

	public InconsistentDataException(Throwable t) {
		super(t);
	}
}

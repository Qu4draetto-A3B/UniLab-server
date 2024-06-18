package org.a3b.commons.result.errors;

import org.a3b.commons.result.ResultException;

public class DataNotFoundException extends ResultException {
	public DataNotFoundException(String msg) {
		super(msg);
	}

	public DataNotFoundException(Throwable t) {
		super(t);
	}
}

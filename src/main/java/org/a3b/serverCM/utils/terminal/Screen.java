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
package a3b.climate.utils.terminal;

import java.io.IOException;

/**
 * The `Screen` class represents a screen for displaying views using a terminal.
 * It provides methods to manage the display of views on the terminal screen.
 */
public class Screen {
	protected Terminal term;

	/**
     * Constructs an instance of the `Screen` class.
     * Initializes the terminal used for display.
     */
	public Screen() {
		term = new Terminal();
	}

	/**
     * Displays the provided `View` on the screen.
     *
     * @param v The `View` to be displayed on the screen.
     */
	public void show(View v) {
		term.clear();
		try {
			v.start(term);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

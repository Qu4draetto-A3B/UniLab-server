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
 * L'interfaccia {@code View} permette di inizializzare il terminale e visualizzarne i contenuti iniziali.
 */
public interface View {
	/**
	 * Inizializza il contenuto della visualizzazione nel terminale fornito.
     *
     * @param term terminale utilizzato per la visualizzazione
     */
    void start(Terminal term) throws IOException;
}

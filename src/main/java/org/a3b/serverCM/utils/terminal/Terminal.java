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

import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * La classe {@code Terminal} fornisce metodi utili per l'interazione con il
 * terminale, tra cui metodi di <i>input</i> e <i>output</i>.
 *
 * @see java.io.Console
 */
public class Terminal {
	private PrintStream out;
	private Scanner in;

	/**
	 * Costruttore di un'istanza di {@code Terminal}.
	 * <p>
	 * Inizializza gli stream standard di input e output e esegue un'ulteriore
	 * configurazione del display del terminale delle piattaforme Windows.
	 */
	public Terminal() {
		out = System.out;
		in = new Scanner(System.in);

		String os = System.getProperty("os.name");
		try {
			if (os.contains("Windows")) {
				new ProcessBuilder("REG ADD HKCU\\CONSOLE /f /v VirtualTerminalLevel /t REG_DWORD /d 1")
						.inheritIO()
						.start()
						.waitFor();
			}
		} catch (Exception e) {
			out.printf("Errore nella modifica del registro 'VirtualTerminalLevel'\n");
			//e.printStackTrace();
		}
	}

	/**
	 * Pulisce il terminale con il codice ANSI "\033[H\033[2J"
	 */
	public void clear() {
		printf("\033[H\033[2J");
	}

	/**
	 * Formatta e stampa una stringa nel terminale.
	 *
	 * @param str  stringa da stampare
	 * @param args valori per l'interpolazione della stringa
	 */
	public void printf(String str, Object... args) {
		out.printf(str, args);
	}

	/**
	 * Formatta e stampa una stringa nel terminale, con un carattere di <i>a
	 * capo</i> finale
	 *
	 * @param str  stringa da stampare
	 * @param args valori per l'interpolazione della stringa
	 */
	public void printfln(String str, Object... args) {
		printf(str + System.lineSeparator(), args);
	}

	/**
	 * Legge una linea di testo dal terminale.
	 *
	 * @return stringa fornita dall'utente
	 */
	public String readLine() {
		printf("> ");
		String s = in.nextLine();
		return s == null ? "" : s;
	}

	/**
	 * Stampa una stringa di testo e aspetta una risposta dall'utente.
	 *
	 * @param str  stringa da stampare
	 * @param args valori per l'interpolazione della stringa
	 * @return stringa fornita dall'utente
	 */
	public String readLine(String str, Object... args) {
		printfln(str + System.lineSeparator() + "> ", args);
		String s = readLine();
		return s == null ? "" : s;
	}

	/**
	 * Stampa una stringa di testo e aspetta una risposta dall'utente.
	 * <p>
	 * Restituisce il valore di default fornito nel caso l'utente inserisca una
	 * stringa vuota.
	 *
	 * @param def  valore di default
	 * @param str  stringa da stampare
	 * @param args valori per l'interpolazione della stringa
	 * @return stringa fornita dall'utente o il valore di default
	 */
	public String readLineOrDefault(String def, String str, Object... args) {
		String out = readLine(str, args).trim();
		return out.isBlank() ? def : out;
	}

	/**
	 * Stampa una stringa e aspetta una risposta dall'utente.
	 * <p>
	 * Controlla la stringa tramite la condizione booleana fornita ({@code fn}).
	 * Se quest'ultima è {@code true}, continua a richiedere una stringa all'utente.
	 *
	 * @param fn   condizione booleana
	 * @param str  stringa da stampare
	 * @param args valori per l'interpolazione della stringa
	 * @return stringa fornita dall'utente
	 */
	public String readWhile(Predicate<String> fn, String str, Object... args) {
		String out = readLine(str, args);
		while (fn.test(out)) {
			out = readLine(str, args);
		}
		return out;
	}

	/**
	 * Chiede all'utente una domanda con risposta Si/No.
	 *
	 * @param yes  risposta (di default &egrave Si)
	 * @param str  stringa da stampare
	 * @param args valori per l'interpolazione della stringa
	 * @return {@code risposta} che rappresenta la risposta
	 */
	public boolean promptUser(boolean yes, String str, Object... args) {
		String yn = "[y/N]";
		String def = "N";
		if (yes) {
			yn = "[Y/n]";
			def = "Y";
		}

		String in = readLine(str + " " + yn, args).trim();
		in = in.isBlank() ? def : in;

		boolean res = false;
		res |= in.equalsIgnoreCase("y");
		res |= in.equalsIgnoreCase("yes");
		res |= in.equalsIgnoreCase("s");
		res |= in.equalsIgnoreCase("si");

		return res;
	}

	/**
	 * <i><b>Metodo non implementato</b></i>
	 * <p>
	 * Legge un stringa di testo dal terminale o restituisce un valore di default se
	 * i parametri sono vuoti.
	 * <p>
	 * Mostra una domanda all'utente, aspettando una risposta come input.
	 * Il valore di default fornito viene restituito se l'input è vuoto o contiene
	 * solo spazi.
	 *
	 * @param def  valore di default
	 * @param str  domanda da mostrare all'utente
	 * @param args valori per l'interpolazione della stringa
	 * @return stringa fornita dall'utente o valori di input
	 */

	/*
	 * public Either<? extends Throwable, String> read(Predicate<String> fn, String
	 * def, String str, Object... args) {
	 * String out;
	 * do {
	 * out = readLine(str, args);
	 * if (out == null) {
	 * return Either.left(new NullPointerException());
	 * }
	 * } while (fn.test(out));
	 * return Either.right(out.isBlank() ? def : out);
	 * }
	 */
}

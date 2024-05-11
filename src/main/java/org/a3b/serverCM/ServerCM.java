package org.a3b.serverCM;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.ServerSocket;

@Log4j2
public class ServerCM {
	private static final int N_THREADS = 64;
	private static final Thread MAIN_THREAD = Thread.currentThread();

	private static ServerPool pool = new ServerPool(N_THREADS);

	public static void main(String[] args) {
		// Stop when it receives CTRL-c
		Runtime.getRuntime().addShutdownHook(new Thread(MAIN_THREAD::interrupt));

		log.info("Starting server");
		try (ServerSocket server = new ServerSocket(8080)) {
			while (true) {
				if (Thread.interrupted())
					throw new InterruptedException("Main interrupted");
				log.trace("Waiting connection");
				pool.execute(server.accept());
			}
		} catch (IOException e) {
			log.error("Stopping server due to IOException");
			log.error(e.toString());
		} catch (InterruptedException e) {
			log.info("Stopping server gracefully after interrupt");
		} finally {
			pool.close();
		}
	}
}
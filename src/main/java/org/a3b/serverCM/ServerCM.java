/*
 * Interdisciplinary Workshop B
 * Climate Monitoring
 * A.A. 2023-2024
 *
 * Authors:
 * - Iuri Antico, 753144, VA
 * - Beatrice Balzarini, 752257, VA
 * - Michael Bernasconi, 752259, VA
 * - Gabriele Borgia, 753262, VA
 *
 * Some rights reserved.
 * See LICENSE file for additional information.
 */
package org.a3b.serverCM;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
/**
 * La classe {@code ServerCM} e' il punto d'avvio del server
 */
@Log4j2
public class ServerCM {
	public static Connection db;
	public static Registry registry;
	public static ServerImpl server;

	public static void main(String[] args) {
		try {
			init();
		} catch (Exception e) {
			log.fatal("Error! ", e);
		}
	}

	/**
	 * Metodo di inizializzazione del server
	 * @throws Exception per gestire le eccezioni legate agli errori del server
	 */
	public static void init() throws Exception {
		log.trace("Initializing ServerCM");

		Dotenv env = Dotenv.configure().filename("postgres.env").load();

		String url = "jdbc:postgresql://localhost/dbCM";
		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", env.get("POSTGRES_PASSWORD"));
		db = DriverManager.getConnection(url, props);

		server = new ServerImpl();
		registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		registry.rebind("CM", server);

		log.trace("ServerCM initialized");
	}
}
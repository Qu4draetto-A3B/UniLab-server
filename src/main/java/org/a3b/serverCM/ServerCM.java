package org.a3b.serverCM;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

@Log4j2
public class ServerCM {
	public static Connection db;
	public static Registry registry;
	private static ServerImpl server;

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> System.exit(0)));
		try {
			init();
			log.info(server.getAreaGeografica(123456));
			throw new Exception("Ha!");
		} catch (Exception e) {
			log.error("Error! ", e);
		}
	}

	private static void init() throws Exception {
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
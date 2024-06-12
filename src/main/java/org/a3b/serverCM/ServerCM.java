package org.a3b.serverCM;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

@Log4j2
public class ServerCM {
	public static Connection db;
	public static Registry registry;

	public static void main(String[] args) {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			//log.error(e);
		}
	}

	private static void init() throws Exception {
		log.trace("Initializing ServerCM");

		Dotenv env = Dotenv.configure().filename("postgres.env").ignoreIfMissing().load();

		String url = "jdbc:postgresql://localhost/dbCM";
		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", env.get("POSTGRES_PASSWORD"));
		db = DriverManager.getConnection(url, props);

		registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		registry.rebind("CM", new UnicastRemoteObject() {});

		log.trace("ServerCM initialized");
	}
}
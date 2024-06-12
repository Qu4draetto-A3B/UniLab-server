package org.a3b.serverCM;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.TransferQueue;

@Log4j2
public class ServerWorker extends Thread {
	private TransferQueue<Socket> channel;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;

	public ServerWorker(TransferQueue<Socket> chan, ThreadGroup group, String name) {
		super(group, name);
		channel = chan;
		start();
	}

	@Override
	public void run() {
		try {
			while (true) {
				log.trace("Waiting for socket");
				socket = channel.take();
				log.trace("Handling connection");
				input = new ObjectInputStream(socket.getInputStream());
				output = new ObjectOutputStream(socket.getOutputStream());
				//TODO
			}
		} catch (InterruptedException e) {
			log.trace("Closing");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

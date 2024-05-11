package org.a3b.serverCM;

import lombok.extern.log4j.Log4j2;

import java.net.Socket;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

@Log4j2
public class ServerPool implements AutoCloseable {
	private ServerWorker[] pool;
	private ThreadGroup group;
	private TransferQueue<Socket> channel;

	public ServerPool(final int n) {
		pool = new ServerWorker[n];
		group = new ThreadGroup(String.format("Pool-%x", this.hashCode()));
		channel = new LinkedTransferQueue<>();
		for (int i = 0; i < pool.length; i++) {
			pool[i] = new ServerWorker(channel, group, "Worker-" + i);
		}
	}

	public boolean execute(Socket sock) {
		return channel.offer(sock);
	}

	@Override
	public void close() {
		log.trace("Interrupting workers");
		group.interrupt();
	}
}

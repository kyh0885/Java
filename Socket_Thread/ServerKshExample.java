package kcci.iotksh_v1;

public class ServerKshExample {
	public static void main(String[] args) {
		ServerKshThread serverKshThread =  new ServerKshThread();
		serverKshThread.start();
	}
}
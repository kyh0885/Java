package kcci.iotkyh_v1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.Writer;
import java.io.FileWriter;

public class ServerKyhExample {//server
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
			Thread serverKyhThread = new ServerKyhThread();
			serverKyhThread.start();
			
		}
	}


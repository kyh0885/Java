package kcci.iotkyh_v1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientKyhExample {//client

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		Thread clientKyhThread = new ClientKyhThread();
		clientKyhThread.start();	
		}
	}
 

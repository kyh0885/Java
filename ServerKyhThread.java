package kcci.iotkyh_v1; 

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.io.Writer;
import java.io.FileWriter;

public class ServerKyhThread extends Thread {
	static BlockingQueue<String> queueInput = new LinkedBlockingQueue<String>();
	Socket socket = null;
	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(); 
			serverSocket.bind(new InetSocketAddress("localhost", 5000));
			while (true) {
				System.out.println("[���� ��ٸ�]");
				Socket socket = serverSocket.accept(); 
				InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
				System.out.println("[���� ������] " + isa.getHostName());
				byte[] bytes = null; 
				String message = null;
				InputStream is = socket.getInputStream(); //Socket ��ü�� ��ȯ �Ǹ� (������ �Ǹ�) InputStream�� ����Ͽ� Ŭ���̾�Ʈ���� ���� �����͸� ���� �� �ֽ��ϴ�.
				bytes = new byte[100];

				int readByteCount = is.read(bytes);
				if(readByteCount<=0)
						break;
				message = new String(bytes, 0, readByteCount, "UTF-8");
				System.out.println("[������ �ޱ� ����]: " + message);
					
				try {
					Writer writer = new FileWriter("C:\\Users\\IoT14\\eclipse-workspace\\java_socket\\bin\\kcci/recvdata.txt");
					String[] splitMessage = message.split("@");
					if(splitMessage.length ==4) {
						
					String data = "ID:"+splitMessage[0]+"�µ� : "+splitMessage[1]+" ����:"+splitMessage[2]+"����:"+splitMessage[3]+"\r\n";
					writer.write(data);
					
					}
					writer.flush();
					writer.close();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				OutputStream os = socket.getOutputStream();
			//	message = "Hello Server";
				System.out.print("Server���� ���� �޼����� �Է��ϼ��� : ");
				message = sc.nextLine();
				bytes = message.getBytes("UTF-8");
				os.write(bytes);
				os.flush();
				System.out.println("[������ ������ ����]");
				
				sendData(message);
				is.close();
				socket.close(); //InputStream�� OutStream�� �ݾ��ش�.
			}
		} catch (Exception e) {
		}

		if (!serverSocket.isClosed()) {
			try {
				serverSocket.close();
			} catch (IOException e1) {
			}
		}
	}
	void sendData(String message) {
		queueInput.offer(message);
		Scanner sc = new Scanner(System.in);
		OutputStream os = null;
		try {
			os = this.socket.getOutputStream();
			System.out.print("Server���� ���� �޼����� �Է��ϼ��� : ");
			message = sc.nextLine();
			byte[] bytes = message.getBytes("UTF-8");
			os.write(bytes);
			os.flush();
			System.out.println("[������ ������ ����]"+message);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//System.out.println("[������ ������ ����]"+message);
	}
}

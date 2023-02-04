package kcci.iotkyh_v1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientKyhThread extends Thread{
	//private static final String  = null;
	static BlockingQueue<String> queueInput = new LinkedBlockingQueue<String>();
	Socket socket = null;
	
	public void run() {//client
		Scanner sc = new Scanner(System.in);
		Socket socket = null;
		super.run();
		try {
			socket = new Socket(); //���ϻ���
			System.out.println("[���� ��û]");
			socket.connect(new InetSocketAddress("localhost", 5000));
			System.out.println("[���� ����]");
			byte[] bytes = null;
			String message = null;

			OutputStream os = socket.getOutputStream(); //������ ��ȯ
			//message = "Hello Server";
			System.out.print("Server���� ���� �޼����� �Է��ϼ��� : ");
			message = sc.nextLine();
			bytes = message.getBytes("UTF-8");
			os.write(bytes);
			os.flush();
			System.out.println("[������ ������ ����]");
		
			while (true) {	
			InputStream is = socket.getInputStream();
			bytes = new byte[100];
			int readByteCount = is.read(bytes);
			if(readByteCount<=0) 
				break;
			message = new String(bytes, 0, readByteCount, "UTF-8");
			System.out.println("[������ �ޱ� ����]: " + message);
			message = sc.nextLine();
			bytes = message.getBytes("UTF-8");
			if(message.equals("quit")){
				break;
				}
			}
			
			sendData(message);
			//is.close();
			
		}
		catch (Exception e) {
		System.out.println("������ �����Ǿ����ϴ�");
			}
		if (!socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e1) {}
		}System.out.println("Ŭ���̾�Ʈ�� ����Ǿ����ϴ�");
	}
	
	
	void sendData(String message) {
		Scanner sc = new Scanner(System.in);
		try {
			OutputStream os = socket.getOutputStream();
			System.out.print("Server���� ���� �޼����� �Է��ϼ��� : ");
			message = sc.nextLine();
			byte[] bytes = message.getBytes("UTF-8");
			os.write(bytes);
			os.flush();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("[������ ������ ����]");
	}
}




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
			socket = new Socket(); //소켓생성
			System.out.println("[연결 요청]");
			socket.connect(new InetSocketAddress("localhost", 5000));
			System.out.println("[연결 성공]");
			byte[] bytes = null;
			String message = null;

			OutputStream os = socket.getOutputStream(); //데이터 교환
			//message = "Hello Server";
			System.out.print("Server에게 보낼 메세지를 입력하세요 : ");
			message = sc.nextLine();
			bytes = message.getBytes("UTF-8");
			os.write(bytes);
			os.flush();
			System.out.println("[데이터 보내기 성공]");
		
			while (true) {	
			InputStream is = socket.getInputStream();
			bytes = new byte[100];
			int readByteCount = is.read(bytes);
			if(readByteCount<=0) 
				break;
			message = new String(bytes, 0, readByteCount, "UTF-8");
			System.out.println("[데이터 받기 성공]: " + message);
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
		System.out.println("서버가 중지되었습니다");
			}
		if (!socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e1) {}
		}System.out.println("클라이언트가 종료되었습니다");
	}
	
	
	void sendData(String message) {
		Scanner sc = new Scanner(System.in);
		try {
			OutputStream os = socket.getOutputStream();
			System.out.print("Server에게 보낼 메세지를 입력하세요 : ");
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
		System.out.println("[데이터 보내기 성공]");
	}
}




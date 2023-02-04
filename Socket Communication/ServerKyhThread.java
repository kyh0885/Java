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
				System.out.println("[연결 기다림]");
				Socket socket = serverSocket.accept(); 
				InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
				System.out.println("[연결 수락함] " + isa.getHostName());
				byte[] bytes = null; 
				String message = null;
				InputStream is = socket.getInputStream(); //Socket 객체가 반환 되면 (연결이 되면) InputStream을 사용하여 클라이언트에서 보낸 데이터를 읽을 수 있습니다.
				bytes = new byte[100];

				int readByteCount = is.read(bytes);
				if(readByteCount<=0)
						break;
				message = new String(bytes, 0, readByteCount, "UTF-8");
				System.out.println("[데이터 받기 성공]: " + message);
					
				try {
					Writer writer = new FileWriter("C:\\Users\\IoT14\\eclipse-workspace\\java_socket\\bin\\kcci/recvdata.txt");
					String[] splitMessage = message.split("@");
					if(splitMessage.length ==4) {
						
					String data = "ID:"+splitMessage[0]+"온도 : "+splitMessage[1]+" 조도:"+splitMessage[2]+"습도:"+splitMessage[3]+"\r\n";
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
				System.out.print("Server에게 보낼 메세지를 입력하세요 : ");
				message = sc.nextLine();
				bytes = message.getBytes("UTF-8");
				os.write(bytes);
				os.flush();
				System.out.println("[데이터 보내기 성공]");
				
				sendData(message);
				is.close();
				socket.close(); //InputStream과 OutStream을 닫아준다.
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
			System.out.print("Server에게 보낼 메세지를 입력하세요 : ");
			message = sc.nextLine();
			byte[] bytes = message.getBytes("UTF-8");
			os.write(bytes);
			os.flush();
			System.out.println("[데이터 보내기 성공]"+message);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//System.out.println("[데이터 보내기 성공]"+message);
	}
}

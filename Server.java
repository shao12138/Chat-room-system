
import java.io.*;

import java.net.*;
import java.util.*;

import javax.swing.JOptionPane;

public class Server {
	private ServerSocket serverSocket;
	private HashSet<Socket> allSockets;

	public Server(int i) {
		try {
			serverSocket = new ServerSocket(i);
			JOptionPane.showMessageDialog(null, "������������\n�����пͻ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		allSockets = new HashSet<Socket>();
		try {
			startService();
		} catch (IOException e) {
		System.out.println("����");
		}
	}

	public void startService() throws IOException {
		while (true) {
			Socket s = serverSocket.accept();
			System.out.println("�û��ѽ���������");
			allSockets.add(s);
			new ServerThread(s).start();

		}
	}

	private class ServerThread extends Thread {
		Socket socket;

		public ServerThread(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while (true) {
					String str = br.readLine();
					sendMessageTOAllClient(str);
				}
			} catch (Exception e) {
				System.out.println("�û��˳�������");
			}
		}

		public void sendMessageTOAllClient(String message) throws IOException {
			for (Socket s : allSockets) {
				PrintWriter pw = new PrintWriter(s.getOutputStream());
				pw.println(message);
				pw.flush();
			}
		}
	}

}

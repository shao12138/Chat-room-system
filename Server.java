
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
			JOptionPane.showMessageDialog(null, "服务器已启动\n请运行客户端", "提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		allSockets = new HashSet<Socket>();
		try {
			startService();
		} catch (IOException e) {
		System.out.println("错误");
		}
	}

	public void startService() throws IOException {
		while (true) {
			Socket s = serverSocket.accept();
			System.out.println("用户已进入聊天室");
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
				System.out.println("用户退出聊天室");
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

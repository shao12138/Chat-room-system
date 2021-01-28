
import java.io.*;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ServerTPC extends JFrame {
	private JTextField enterField;
	private JTextArea displayArea;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	private JButton but;
	private JPanel panel;
	public ServerTPC(int i) {
		super("����Ա");
		panel = new JPanel(new FlowLayout());
		but = new JButton("����");
		Container container = getContentPane();
		enterField = new JTextField(20);
		enterField.setEditable(false);
		enterField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendData(e.getActionCommand());
				enterField.setText("");
			}
		});
		but.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendData(e.getActionCommand());
				enterField.setText("");
			}
		});
		try {
			server = new ServerSocket(i, 100);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "�˿ڱ�ռ���� ����ѡ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		}
		panel.add(enterField);
		panel.add(but);
		container.add(panel, BorderLayout.SOUTH);
		displayArea = new JTextArea();
		displayArea.setEditable(false);

		container.add(new JScrollPane(displayArea), BorderLayout.CENTER);
		setBounds(600, 200, 500, 250);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Thread a = new Thread(new Roll());
		a.start();
	}

	private void waitForConnection() throws IOException {
		displayMessage("�ȴ��û�����");
		connection = server.accept();
	}

	private void getStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
	}

	private void processConnection() throws IOException {
		String message = "���ӳɹ�";
		sendData(message);
		setTextFieldEditable(true);
		try {
			do {
				message = (String) input.readObject();
				displayMessage("\n" + message);
			} while (true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "����������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void closeConnection() {
		displayMessage("�Ի�����\n");
		setTextFieldEditable(false);
		try {
			output.close();
			input.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendData(String message) {
		try {
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy��MM��dd��HH:mm:ss");
			output.writeObject("����Ա:" + sf.format(date) + "\n" + message);
			output.flush();
			displayMessage("\n����Ա:" + sf.format(date) + "\n" + message);
		} catch (Exception e) {
			displayArea.append("\n��������");
		}
	}

	private void displayMessage(final String messageToDisplay) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				displayArea.append(messageToDisplay);
				displayArea.setCaretPosition(displayArea.getText().length());
			}
		});
	}

	private void setTextFieldEditable(final boolean editable) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				enterField.setEditable(editable);
			}
		});
	}

	class Roll implements Runnable {
		public void run() {
			try {
				while (true) {
					try {
						waitForConnection();
						getStreams();
						processConnection();
					} catch (EOFException e) {
						System.out.println("���﷢���˴����뼰ʱ�޸�");
					} finally {
						closeConnection();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

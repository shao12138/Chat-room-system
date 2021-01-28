
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientTPC extends JFrame{
	private JTextField enterField;
	private JTextArea displayArea;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message="";
	private String chatServer,userName;
	private Socket client;
	private JButton but;
	private JPanel panel;
	private int server;
	public ClientTPC(String username,String ip,int i) {
		super("�ͻ�");
		but=new JButton("����");
		panel=new JPanel(new FlowLayout());
		chatServer=ip;
		server=i;
		userName=username;
		Container c=getContentPane();
		enterField=new JTextField(20);
		enterField.setEditable(false);
		enterField.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						sendData(e.getActionCommand());
						enterField.setText( "" );
					}
				});
		panel.add(enterField);
		panel.add(but);
		c.add(panel,BorderLayout.SOUTH);
		displayArea=new JTextArea();
		displayArea.setEditable(false);
		c.add(new JScrollPane(displayArea),BorderLayout.CENTER);
		setBounds(600, 200, 500, 250);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Thread a=new Thread(new Roll());
		a.start();
	}

	private void connectionToServer() throws IOException{
		displayMessage("��������");
		client=new Socket(InetAddress.getByName(chatServer),server);
	}
	private void getStreams() throws IOException{
		output=new ObjectOutputStream(client.getOutputStream());
		output.flush();
		input=new ObjectInputStream(client.getInputStream());
	}
	private void processConnection()throws IOException{
		setTextFieldEditable(true);
		try {do {
			
				message=(String)input.readObject();
				displayMessage("\n"+message);
			
			} while (true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "�������ѹر�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	private void closeConnection() { 
		displayMessage("\n�Ͽ�����");
		setTextFieldEditable(false);
		try {
			output.close();
			input.close();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void sendData(String message) {
		try {
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy��MM��dd��HH:mm:ss");
			output.writeObject(userName+":"+sf.format(date)+"\n"+message);
			output.flush();
			displayMessage("\n"+userName+":"+sf.format(date)+"\n"+message);
		} catch (Exception e) {
			displayArea.append("\n�����˴���");
		}
	}
	private void displayMessage(final String messageToDisplay) {
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						displayArea.append(messageToDisplay);
						displayArea.setCaretPosition(displayArea.getText().length());
					}
				});
	}
	private void setTextFieldEditable(final boolean editable) {
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						enterField.setEditable(editable);
					}
				});
	}
	class Roll implements Runnable{
		public void run() {
			try {
				connectionToServer();
				getStreams();
				processConnection();
			} catch (EOFException e1) {
				System.out.println("�ͻ��Ͽ�����");
			} catch(IOException e2) {
				e2.printStackTrace();
			} finally {
				closeConnection();
			}
		}
	}
}

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class qqinpage extends JFrame {
	private JLabel ipLabel, userLabel, serverLabel, meanLabel;
	private JButton inButton, outButton;
	private JTextField userField, serverField;
	private JRadioButton meanButton, mean2Button;
	private JPanel ipPanel, userPanel, serverPanel, meanPanel, lastPanel;
	private JPasswordField ipField;

	public qqinpage() {
		userLabel = new JLabel("�� �� �� �� �� Ա �� �� ����");
		ipLabel = new JLabel("��  ��  ��  �� �� Ա ��  �룺");
		serverLabel = new JLabel("�������� Ҫ���ŵ� �� �ڣ�");
		meanLabel = new JLabel("��    ��    ��    ʽ��");

		inButton = new JButton("����������");
		outButton = new JButton("�˳�");
		meanButton = new JRadioButton("˫��");
		mean2Button = new JRadioButton("����");
		ButtonGroup bg = new ButtonGroup();
		bg.add(meanButton);
		bg.add(mean2Button);
		meanButton.setSelected(true);
		ipField = new JPasswordField(20);
		userField = new JTextField(20);
		serverField = new JTextField(20);

		userPanel = new JPanel(new FlowLayout());
		serverPanel = new JPanel(new FlowLayout());
		meanPanel = new JPanel(new FlowLayout());
		lastPanel = new JPanel(new FlowLayout());
		ipPanel = new JPanel(new FlowLayout());

		userPanel.add(userLabel);
		userPanel.add(userField);
		serverPanel.add(serverLabel);
		serverPanel.add(serverField);
		meanPanel.add(meanLabel);
		meanPanel.add(meanButton);
		meanPanel.add(mean2Button);
		lastPanel.add(inButton);
		lastPanel.add(outButton);
		ipPanel.add(ipLabel);
		ipPanel.add(ipField);
		addListener();

		Container container = getContentPane();
		container.setLayout(new GridLayout(5, 1));
		container.add(userPanel);
		container.add(ipPanel);
		container.add(serverPanel);
		container.add(meanPanel);
		container.add(lastPanel);

		setBounds(600, 200, 500, 250);
		setTitle("����Ա��¼");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void addListener() {
		outButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		inButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (userField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "�������û���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				} else if (ipField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "�������û���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				} else if (!meanButton.isSelected() && !mean2Button.isSelected()) {
					JOptionPane.showMessageDialog(null, "��ѡ�����췽ʽ", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				} else if (userField.getText().equals("root") && ipField.getText().equals("123456")) {
					if (mean2Button.isSelected()) {
						dispose();

						new Server(Integer.parseInt(serverField.getText()));

					} else {
						dispose();
						JOptionPane.showMessageDialog(null, "�������Ѵ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						new ServerTPC(Integer.parseInt(serverField.getText()));
					}

				} else {
					JOptionPane.showMessageDialog(null, "�������\n�����˳�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					System.exit(1);
				}
			}

		});
	}

	public static void main(String[] args) {
		qqinpage a = new qqinpage();
		a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

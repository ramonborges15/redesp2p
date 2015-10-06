# redesp2p
package br.com.ufes.aplicacaoP2P;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.JTable;

public class RedesP2P {

	private JFrame frame;
	private JTextField txtMeuIp;
	private JTextField textField;
	private JTextField txtAntecessor;
	private JTextField textField_1;
	private JTextField txtSucessor;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RedesP2P window = new RedesP2P();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RedesP2P() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnJoin = new JButton("Join");
	/*	btnJoin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
//				textField = Meuip;
			}
		});*/
		btnJoin.setBounds(39, 25, 89, 23);
		frame.getContentPane().add(btnJoin);
		
		JButton btnLeave = new JButton("Leave");
		btnLeave.setBounds(39, 59, 89, 23);
		frame.getContentPane().add(btnLeave);
		
		JButton btnLookup = new JButton("LookUp");
		btnLookup.setBounds(39, 96, 89, 23);
		frame.getContentPane().add(btnLookup);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(39, 130, 89, 23);
		frame.getContentPane().add(btnUpdate);
		
		txtMeuIp = new JTextField();
		txtMeuIp.setText("Meu IP");
		txtMeuIp.setBounds(170, 26, 86, 20);
		frame.getContentPane().add(txtMeuIp);
		txtMeuIp.setColumns(10);
		
		textField = new JTextField();
		textField.setBounds(263, 26, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(158, 54, 200, 50);
		frame.getContentPane().add(separator);
		
		txtAntecessor = new JTextField();
		txtAntecessor.setText("Antecessor");
		txtAntecessor.setBounds(170, 60, 86, 20);
		frame.getContentPane().add(txtAntecessor);
		txtAntecessor.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(158, 91, 200, 50);
		frame.getContentPane().add(separator_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(263, 60, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		txtSucessor = new JTextField();
		txtSucessor.setText("Sucessor");
		txtSucessor.setBounds(170, 97, 86, 20);
		frame.getContentPane().add(txtSucessor);
		txtSucessor.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(263, 97, 86, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
	}
}

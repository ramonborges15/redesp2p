
package br.com.ufes.aplicacaoP2P;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JLabel;

import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.SwingConstants;

public class IntGraphics {

	private JFrame frame;
	private JTextField textMyIP;
	private JTextField textMyID;
	private JTextField textIpAnt;
	private JTextField textIpSuc;
	private JTextField textIdAnt;
	private JTextField textIdSuc;

	Servidor server = new Servidor();
	Cliente client = new Cliente();
	ParticipanteRede node = new ParticipanteRede();
	/*
	  Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IntGraphics window = new IntGraphics();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public JTextField getTxtip() {
		return this.textMyIP;
	}
	
	public JTextField getTxtid() {
		return this.textMyID;
	}
	public JTextField getTxtipAnt() {
		return this.textIpAnt;
	}
	
	public JTextField getTxtidAnt() {
		return this.textIdAnt;
	}
	public JTextField getTxtipSuc() {
		return this.textIpSuc;
	}
	
	public JTextField getTxtidSuc() {
		return this.textIdSuc;
	}
	/*
	  Create the application.
	 */
	public IntGraphics() {
		initialize();
	}
	
	public void btnCreateClick(JButton nod, JTextField txtId, JTextField txtIp) {
		 nod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				String str = Integer.toString(node.getId());
				txtId.setText(str); 
				
            }
        }); 
	}
	
	public void btnLeaveClick(JButton nod) {
		 nod.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e)
           {
           	
           }
       }); 
	}
	
	public void btnLookupClick(JButton nod) {
		 nod.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e)
           {
           	
           }
       }); 
	}
	
	

	/*
	  Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnLeave = new JButton("Leave");
		btnLeave.setHorizontalAlignment(SwingConstants.LEADING);
		btnLeave.setBounds(112, 222, 76, 23);
		frame.getContentPane().add(btnLeave);
		
		JButton btnLookup = new JButton("LookUp");
		btnLookup.setBounds(12, 222, 88, 23);
		frame.getContentPane().add(btnLookup);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(200, 222, 87, 23);
		frame.getContentPane().add(btnUpdate);
		
		textMyIP = new JTextField();
		textMyIP.setBounds(197, 12, 102, 20);
		frame.getContentPane().add(textMyIP);
		textMyIP.setColumns(10);
		
		textMyID = new JTextField();
		textMyID.setBounds(197, 44, 102, 20);
		frame.getContentPane().add(textMyID);
		textMyID.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(212, 195, 208, 50);
		frame.getContentPane().add(separator);
		
		textIpAnt = new JTextField();
		textIpAnt.setBounds(84, 131, 104, 20);
		frame.getContentPane().add(textIpAnt);
		textIpAnt.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(22, 195, 200, 35);
		frame.getContentPane().add(separator_1);
		
		textIpSuc = new JTextField();
		textIpSuc.setBounds(272, 131, 103, 20);
		frame.getContentPane().add(textIpSuc);
		textIpSuc.setColumns(10);
		
		textIdAnt = new JTextField();
		textIdAnt.setBounds(84, 163, 104, 20);
		frame.getContentPane().add(textIdAnt);
		textIdAnt.setColumns(10);
		
		textIdSuc = new JTextField();
		textIdSuc.setBounds(272, 163, 103, 20);
		frame.getContentPane().add(textIdSuc);
		textIdSuc.setColumns(10);
		
		JButton btnCreatenode = new JButton("CreateNode");
		btnCreatenode.setBounds(301, 211, 119, 35);
		frame.getContentPane().add(btnCreatenode);
		
		JLabel lblAntecessor = new JLabel("Antecessor");
		lblAntecessor.setBounds(95, 104, 89, 15);
		frame.getContentPane().add(lblAntecessor);
		
		JLabel lblSucessor = new JLabel("Sucessor");
		lblSucessor.setBounds(272, 104, 70, 15);
		frame.getContentPane().add(lblSucessor);
		
		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(63, 133, 32, 15);
		frame.getContentPane().add(lblIp);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(63, 165, 70, 15);
		frame.getContentPane().add(lblId);
		
		JLabel lblIp_1 = new JLabel("IP:");
		lblIp_1.setBounds(250, 133, 38, 15);
		frame.getContentPane().add(lblIp_1);
		
		JLabel lblId_1 = new JLabel("ID:");
		lblId_1.setBounds(250, 168, 32, 15);
		frame.getContentPane().add(lblId_1);
		
		JLabel lblMyIp = new JLabel("My IP:");
		lblMyIp.setBounds(144, 14, 70, 15);
		frame.getContentPane().add(lblMyIp);
		
		JLabel lblMyId = new JLabel("My ID:");
		lblMyId.setBounds(144, 46, 53, 15);
		frame.getContentPane().add(lblMyId);
		
		btnCreateClick(btnCreatenode, textMyID, textMyIP);
		btnLeaveClick(btnLeave);
		btnLookupClick(btnLookup);
		btnUpdateClick(btnUpdate);
	}
	
}

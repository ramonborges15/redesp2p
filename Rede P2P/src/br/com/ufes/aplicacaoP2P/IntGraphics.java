
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
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Random;

import javax.swing.SwingConstants;
import javax.swing.JEditorPane;

import java.awt.TextField;

public class IntGraphics {

	public JFrame frame;
	public JTextField textMyIP;
	public JTextField textMyID;
	public JTextField textIpAnt;
	public JTextField textIpSuc;
	public JTextField textIdAnt;
	public JTextField textIdSuc;
	Servidor server = new Servidor();
	Cliente client = new Cliente();
	public JTextField textRecvLookup;
	public JButton btnLeave; 
	public JButton btnLookup;
	public JButton btnUpdate;
	public JButton btnCreatenode;
	public JButton btnJoin ;
	ParticipanteRede newNode = new ParticipanteRede();
	
	/*
	  Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				IntGraphics window = new IntGraphics();
				window.frame.setVisible(true);
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
	
	
	public void btnCreateClick() {
		 btnCreatenode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				try {

					int id;
					Random random  = new Random(System.currentTimeMillis());
					id = random.nextInt((int) Math.pow(2, 32) - 1);
					
	            	InetAddress IP;
					Enumeration e1;
					e1 = NetworkInterface.getNetworkInterfaces();
					NetworkInterface ni = (NetworkInterface) e1.nextElement();
					ni.getInetAddresses().nextElement();
					Enumeration e2 = ni.getInetAddresses();
					e2.nextElement();
					
					IP = (InetAddress) e2.nextElement();
					
					newNode.setId(id);
					newNode.setIp(IP);
					newNode.setIdAnt(id);
					newNode.setIpAnt(IP);
					newNode.setIdSuc(id);
					newNode.setIpSuc(IP);
					
					btnCreatenode.setEnabled(false);
					String str = Integer.toString(newNode.getId());
					textMyID.setText(str);
					textMyIP.setText(newNode.getIp().getHostName());
					
					String str2 = Integer.toString(newNode.getIdAnt());
					textIdAnt.setText(str2);
					textIpAnt.setText(newNode.getIpAnt().getHostName());
					
					String str3 = Integer.toString(newNode.getIdSuc());
					textIdSuc.setText(str3);
					textIpSuc.setText(newNode.getIpSuc().getHostName());
					
				} catch (SocketException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
            }
        }); 
	}
	
	public void btnJoinClick(JButton nod) {
		 btnJoin.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e)
           {
        	   try {
        		   
        		   int id;
            	   Random random  = new Random(System.currentTimeMillis());
            	   id = random.nextInt((int) Math.pow(2, 32) - 1);
        		   client.join(id, InetAddress.getByName(textMyIP.getText()));
        		   
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
           }
       }); 
	}
	
	public void btnLookupClick() {
		 btnLookup.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e)
           {
           		try {
           			int idWanted;
           			Random random  = new Random(System.currentTimeMillis());
					idWanted = random.nextInt((int) Math.pow(2, 32) - 1);
					
					InetAddress ip = InetAddress.getByName(textRecvLookup.getText());
					btnLookup.setEnabled(false);
	           		btnJoin.setEnabled(true);
	           		//System.out.println(ip.getHostAddress());
	           		client.lookup(idWanted, ip, idWanted);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
           }
       }); 
	}
	
	public void btnUpdateClick(JButton nod) {
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
		frame.setBounds(100, 100, 600, 320);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		this.btnLeave = new JButton("Leave");
		this.btnLeave.setHorizontalAlignment(SwingConstants.LEADING);
		this.btnLeave.setBounds(22, 219, 76, 23);
		frame.getContentPane().add(this.btnLeave);
	
		this.btnLookup = new JButton("LookUp");
		this.btnLookup.setBounds(55, 161, 88, 23);
		frame.getContentPane().add(this.btnLookup);
		
		this.btnUpdate = new JButton("Update");
		this.btnUpdate.setBounds(110, 219, 87, 23);
		frame.getContentPane().add(this.btnUpdate);
		
		this.btnCreatenode = new JButton("CreateNode");
		this.btnCreatenode.setBounds(410, 219, 125, 23);
		frame.getContentPane().add(this.btnCreatenode);
		
		this.btnJoin = new JButton("Join");
		this.btnJoin.setBounds(265, 218, 60, 25);
		frame.getContentPane().add(this.btnJoin);
		this.btnJoin.setEnabled(false);
		
		textMyIP = new JTextField();
		textMyIP.setEditable(false);
		textMyIP.setBounds(250, 61, 102, 20);
		frame.getContentPane().add(textMyIP);
		textMyIP.setColumns(10);
		textMyIP.enableInputMethods(false);
		
		textMyID = new JTextField();
		textMyID.setBounds(250, 93, 102, 20);
		frame.getContentPane().add(textMyID);
		textMyID.setColumns(10);
		
		textIpAnt = new JTextField();
		textIpAnt.setEditable(false);
		textIpAnt.setBounds(61, 61, 104, 20);
		frame.getContentPane().add(textIpAnt);
		textIpAnt.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(22, 196, 532, 20);
		frame.getContentPane().add(separator_1);
		
		textIpSuc = new JTextField();
		textIpSuc.setEditable(false);
		textIpSuc.setBounds(432, 61, 103, 20);
		frame.getContentPane().add(textIpSuc);
		textIpSuc.setColumns(10);
		
		textIdAnt = new JTextField();
		textIdAnt.setEditable(false);
		textIdAnt.setBounds(61, 93, 104, 20);
		frame.getContentPane().add(textIdAnt);
		textIdAnt.setColumns(10);
		
		textIdSuc = new JTextField();
		textIdSuc.setEditable(false);
		textIdSuc.setBounds(432, 93, 103, 20);
		frame.getContentPane().add(textIdSuc);
		textIdSuc.setColumns(10);
		
		JLabel lblAntecessor = new JLabel("Antecessor");
		lblAntecessor.setBounds(72, 34, 89, 15);
		frame.getContentPane().add(lblAntecessor);
		
		JLabel lblSucessor = new JLabel("Sucessor");
		lblSucessor.setBounds(449, 34, 70, 15);
		frame.getContentPane().add(lblSucessor);
		
		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(40, 63, 32, 15);
		frame.getContentPane().add(lblIp);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(40, 95, 70, 15);
		frame.getContentPane().add(lblId);
		
		JLabel lblIp_1 = new JLabel("IP:");
		lblIp_1.setBounds(410, 63, 38, 15);
		frame.getContentPane().add(lblIp_1);
		
		JLabel lblId_1 = new JLabel("ID:");
		lblId_1.setBounds(410, 98, 32, 15);
		frame.getContentPane().add(lblId_1);
		
		JLabel lblMyIp = new JLabel("My IP:");
		lblMyIp.setBounds(197, 63, 70, 15);
		frame.getContentPane().add(lblMyIp);
		
		JLabel lblMyId = new JLabel("My ID:");
		lblMyId.setBounds(197, 95, 53, 15);
		frame.getContentPane().add(lblMyId);
		
		
		textRecvLookup = new JTextField();
		textRecvLookup.setBounds(155, 163, 380, 19);
		frame.getContentPane().add(textRecvLookup);
		textRecvLookup.setColumns(10);
		
		
		btnCreateClick();
		btnLookupClick();
		/*btnLeaveClick();
		
		btnUpdateClick(); */
	}
}

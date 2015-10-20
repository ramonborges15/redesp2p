
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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.Random;

import javax.swing.SwingConstants;
import javax.swing.JEditorPane;

import java.awt.TextField;
import java.awt.Font;
import java.awt.Color;

public class IntGraphics implements Runnable{  

	public JFrame frame;
	
	public JButton btnLeave; 
	public JButton btnLookup;
	public JButton btnCreatenode;
	public JButton btnJoin ;
	public JButton btnIdgenerate;
	
	public JTextField textMyIP;
	public JTextField textMyID;
	public JTextField textIpAnt;
	public JTextField textIpSuc;
	public JTextField textIdAnt;
	public JTextField textIdSuc;
	public JTextField textIpDestination;
	
	ParticipanteRede newNode;
	Cliente c;
	/*
	public static void main(String[] args) throws SocketException {
		Servidor serv = new Servidor();
		Cliente client = new Cliente();
		
		IntGraphics window = new IntGraphics(serv, client);
		window.frame.setVisible(true);
		new Thread() {
			public void run() {
				window.run();
			}
		}.start();
		//new Thread(window).start();
	}
	*/
	public IntGraphics(ParticipanteRede newNode) {
		this.c = new Cliente();
		this.newNode = newNode;
		initialize();
	}
	
	public void run() {
		btnCreateClick();
		btnLookupClick();
		btnJoinClick();
		btnNewIdClick();
		btnLeaveClick();
		while(true) {
			updateInterface(newNode.getId(), newNode.getIdAnt(), newNode.getIdSuc(), newNode.getIp(), newNode.getIpAnt(), newNode.getIpSuc());
		}
	}
	public void updateInterface(int id, int idAnt, int idSuc, InetAddress ip, InetAddress ipAnt, InetAddress ipSuc) {
		   //Atualiza interface
		   String str = Integer.toString(id);
		   textMyID.setText(str);
		   textMyIP.setText(ip.getHostAddress());

		   String str2 = Integer.toString(idAnt);
		   textIdAnt.setText(str2);
		   textIpAnt.setText(ipAnt.getHostAddress());

		   String str3 = Integer.toString(idSuc);
		   textIdSuc.setText(str3);
		   textIpSuc.setText(ipSuc.getHostAddress());
	}
	
	public void btnNewIdClick() {
		btnIdgenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	int id;
				Random random  = new Random(System.currentTimeMillis());
				id = random.nextInt((int) Math.pow(2, 32) - 1);
				
				if(newNode.getId() == newNode.getIdAnt())
					newNode.setIdAnt(id);
				if(newNode.getId() == newNode.getIdSuc())
					newNode.setIdSuc(id);
				newNode.setId(id);
            }
		});
	}
	
	public void btnLeaveClick() {
		btnLeave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	try {
            		btnLeave.setEnabled(false);
                	btnCreatenode.setEnabled(true);
                	btnLookup.setEnabled(true);
                	btnIdgenerate.setEnabled(true);
					c.leave(newNode.getId(), newNode.getIdAnt(), newNode.getIdSuc(), newNode.getIpAnt(), newNode.getIpSuc());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
		});
	}
	
	public void btnCreateClick() {
		 btnCreatenode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
				try {
					btnCreatenode.setEnabled(false);
					btnIdgenerate.setEnabled(false);
					btnJoin.setEnabled(false);
					btnLookup.setEnabled(false);
					btnLeave.setEnabled(true);
					
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
					newNode.setIdAnt(id);
					newNode.setIdSuc(id);
					newNode.setIp(IP);
					newNode.setIpAnt(IP);
					newNode.setIpSuc(IP);
				} catch (SocketException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
            }
        }); 
	}
	
	public void btnJoinClick() {
		 btnJoin.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e)
           {
        	   try {
        		   
        		   int id = newNode.getId();
            	   InetAddress ip = newNode.getIpSuc();
            	   System.out.print("btnJoinClick: ");
            	   System.out.print(id + " ");
            	   System.out.println(ip.getHostAddress().toString());
        		   c.join(id, ip);
        		          		   
			} catch (IOException e1) {
					e1.printStackTrace();
			}
           }
       });
	}
	
	public void btnLookupClick() {
		 btnLookup.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e)
           {
        	   btnCreatenode.setEnabled(false);
        	   btnJoin.setEnabled(true);
        	   btnLeave.setEnabled(true);
        	   try {
        		   int id;
        		   /*Linux*/
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
				   
        		   /*Windows
        		      InetAddress IP = InetAddress.getLocalHost();
        		   */
        		   newNode.setId(id);
        		   newNode.setIdAnt(id);
        		   newNode.setIdSuc(id);
        		   newNode.setIp(IP);
        		   newNode.setIpAnt(IP);
        		   newNode.setIpSuc(IP);
        		   
        		   InetAddress ipDestination =  InetAddress.getByName(textIpDestination.getText());
        		   int idSource = id;
        		   InetAddress ipSource = IP;
        		   c.lookup(idSource, ipSource, ipDestination);
        		   
        	   } catch (UnknownHostException e1) {
				e1.printStackTrace();
        	   } catch (IOException e1) {
				e1.printStackTrace();
			}
        	   
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
		this.btnLeave.setEnabled(false);
		
		this.btnLookup = new JButton("LookUp");
		this.btnLookup.setBounds(295, 161, 88, 23);
		frame.getContentPane().add(this.btnLookup);
		
		this.btnCreatenode = new JButton("CreateNode");
		this.btnCreatenode.setBounds(410, 219, 125, 23);
		frame.getContentPane().add(this.btnCreatenode);
		
		this.btnJoin = new JButton("Join");
		this.btnJoin.setBounds(265, 218, 60, 25);
		frame.getContentPane().add(this.btnJoin);
		this.btnJoin.setEnabled(false);
		
		this.btnIdgenerate = new JButton("ChangeID");
		this.btnIdgenerate.setFont(new Font("Dialog", Font.BOLD, 12));
		this.btnIdgenerate.setBounds(395, 161, 102, 23);
		frame.getContentPane().add(this.btnIdgenerate);
		
		textMyIP = new JTextField();
		textMyIP.setHorizontalAlignment(SwingConstants.CENTER);
		textMyIP.setEditable(false);
		textMyIP.setBounds(261, 40, 102, 20);
		frame.getContentPane().add(textMyIP);
		textMyIP.setColumns(10);
		textMyIP.enableInputMethods(false);
		
		textMyID = new JTextField();
		textMyID.setHorizontalAlignment(SwingConstants.CENTER);
		textMyID.setBounds(261, 72, 102, 20);
		frame.getContentPane().add(textMyID);
		textMyID.setColumns(10);
		
		textIpAnt = new JTextField();
		textIpAnt.setHorizontalAlignment(SwingConstants.CENTER);
		textIpAnt.setEditable(false);
		textIpAnt.setBounds(56, 39, 104, 20);
		frame.getContentPane().add(textIpAnt);
		textIpAnt.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 119, 576, 2);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(12, 196, 576, 20);
		frame.getContentPane().add(separator_1);
		
		textIpSuc = new JTextField();
		textIpSuc.setHorizontalAlignment(SwingConstants.CENTER);
		textIpSuc.setEditable(false);
		textIpSuc.setBounds(432, 39, 103, 20);
		frame.getContentPane().add(textIpSuc);
		textIpSuc.setColumns(10);
		
		textIdAnt = new JTextField();
		textIdAnt.setHorizontalAlignment(SwingConstants.CENTER);
		textIdAnt.setEditable(false);
		textIdAnt.setBounds(56, 71, 104, 20);
		frame.getContentPane().add(textIdAnt);
		textIdAnt.setColumns(10);
		
		textIdSuc = new JTextField();
		textIdSuc.setHorizontalAlignment(SwingConstants.CENTER);
		textIdSuc.setEditable(false);
		textIdSuc.setBounds(432, 71, 103, 20);
		frame.getContentPane().add(textIdSuc);
		textIdSuc.setColumns(10);
		
		textIpDestination = new JTextField();
		textIpDestination.setText("IP Destination");
		textIpDestination.setHorizontalAlignment(SwingConstants.CENTER);
		textIpDestination.setForeground(Color.LIGHT_GRAY);
		textIpDestination.setBounds(83, 161, 178, 23);
		frame.getContentPane().add(textIpDestination);
		textIpDestination.setColumns(10);
		
		JLabel lblAntecessor = new JLabel("Antecessor");
		lblAntecessor.setBounds(67, 12, 89, 15);
		frame.getContentPane().add(lblAntecessor);
		
		JLabel lblSucessor = new JLabel("Sucessor");
		lblSucessor.setBounds(449, 12, 70, 15);
		frame.getContentPane().add(lblSucessor);
		
		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(35, 41, 32, 15);
		frame.getContentPane().add(lblIp);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(35, 73, 70, 15);
		frame.getContentPane().add(lblId);
		
		JLabel lblIp_1 = new JLabel("IP:");
		lblIp_1.setBounds(410, 41, 38, 15);
		frame.getContentPane().add(lblIp_1);
		
		JLabel lblId_1 = new JLabel("ID:");
		lblId_1.setBounds(410, 76, 32, 15);
		frame.getContentPane().add(lblId_1);
		
		JLabel lblMyIp = new JLabel("My IP:");
		lblMyIp.setBounds(208, 42, 70, 15);
		frame.getContentPane().add(lblMyIp);
		
		JLabel lblMyId = new JLabel("My ID:");
		lblMyId.setBounds(208, 74, 53, 15);
		frame.getContentPane().add(lblMyId);
	}
}

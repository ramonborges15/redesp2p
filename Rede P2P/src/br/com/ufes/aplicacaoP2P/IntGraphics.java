
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
	public JButton btnUpdate;
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
	
	Servidor server;

	//ParticipanteRede newNode = new ParticipanteRede();
	/*
	  Launch the application.
	 */
	
	public static void main(String[] args) throws SocketException {
		Servidor serv = new Servidor();
		IntGraphics window = new IntGraphics(serv);
		window.frame.setVisible(true);
		new Thread(window).start();
	}
	
	public IntGraphics(Servidor server) {
		this.server = server;
		initialize();
	}
	
	public void run() {
		btnCreateClick();
		btnLookupClick();
		btnJoinClick();
		btnNewIdClick();
	}
	/*
	  Create the application.
	 */
	
	public void updatingInterface(ParticipanteRede n) {
		String str;
		str = Integer.toString(n.getIdAnt());
		textIdAnt.setText(str);
		str = Integer.toString(n.getIdSuc());
		textIdSuc.setText(str);
		textIpAnt.setText(n.getIpAnt().getHostAddress());
		textIpSuc.setText(n.getIpSuc().getHostAddress());
	}
	
	
	public void btnNewIdClick() {
		btnIdgenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	int id;
				Random random  = new Random(System.currentTimeMillis());
				id = random.nextInt((int) Math.pow(2, 32) - 1);
				
				String str = Integer.toString(id);
				textMyID.setText(str);
            }
		});
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
					
					server.setAux(id, id, id, IP, IP, IP);
					
					btnCreatenode.setEnabled(false);
					String str = Integer.toString(server.newNode.getId());
					textMyID.setText(str);
					textMyIP.setText(server.newNode.getIp().getHostName());
					
					String str2 = Integer.toString(server.newNode.getIdAnt());
					textIdAnt.setText(str2);
					textIpAnt.setText(server.newNode.getIpAnt().getHostName());
					
					String str3 = Integer.toString(server.newNode.getIdSuc());
					textIdSuc.setText(str3);
					textIpSuc.setText(server.newNode.getIpSuc().getHostName());
					
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
        		   int id = server.newNode.getId();
            	   InetAddress ip = server.newNode.getIpSuc();
            	   System.out.print("btnJoinClick: ");
            	   System.out.print(id + " ");
            	   System.out.println(ip.getHostAddress().toString());
        		   server.client.join(id, ip);
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
        	   try {
        		   InetAddress ipDestination =  InetAddress.getByName(textIpDestination.getText());
        		   int idSource = Integer.parseInt(textMyID.getText());
        		   InetAddress ipSource = InetAddress.getByName(textMyIP.getText());
        		   server.client.lookup(idSource, ipSource, ipDestination);
        		   System.out.println("passo aqui");
        	   } catch (UnknownHostException e1) {
				e1.printStackTrace();
        	   } catch (IOException e1) {
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
		this.btnLookup.setBounds(295, 161, 88, 23);
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
		separator.setBounds(12, 147, 576, 2);
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
	
	//Converte um inteiro em um vetor de 4 bytes.
		public byte[] intToBytes(int i) {
			ByteBuffer bb = ByteBuffer.allocate(4);
			bb.putInt(i);
			return bb.array();
		}
		
		public static int bytesToInt(byte[] b) {
			int value = 0;
			for (int i = 0; i < 4; i++) {
				int shift = (4 - 1 - i) * 8;
				value += (b[i] & 0x000000FF) << shift;
			}
			return value;
		}
}

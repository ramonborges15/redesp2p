package br.com.ufes.aplicacaoP2P;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class View {
	
	public static void main(String[] args) throws IOException {
		Servidor s = new Servidor();
		Cliente c = new Cliente();
		IntGraphics g = new IntGraphics(s, c);
		
		g.frame.setVisible(true);
		
		//Thread 1
		Thread t1 = new Thread(g);
		t1.start();
		
		DatagramSocket server = new DatagramSocket(12345);
		//Thread 2
		while (true) {
	    	//Socket sock = server.accept();
	    	System.out.println("thread ");
	    	DatagramPacket recvPacket = new DatagramPacket(new byte[256] , 256);
			server.receive(recvPacket);
	    	new Thread(new Servidor(server,recvPacket)).start();
	    }
		
		/*
		Socket a = server.accept();
		
		DatagramSocket cservSocket = new DatagramSocket(12345);
		DatagramPacket recvPacket = new DatagramPacket(new byte[256] , 256);
		cservSocket.receive(recvPacket);
		*/
	}
}

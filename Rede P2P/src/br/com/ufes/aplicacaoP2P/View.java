package br.com.ufes.aplicacaoP2P;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class View {
	
	public static void main(String[] args) throws IOException {
		ParticipanteRede p = new ParticipanteRede();
		ParticipanteRede aux = new ParticipanteRede();
		IntGraphics g = new IntGraphics(p);
		
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
			Thread t2 = new Thread(new Servidor(server,recvPacket, p, aux));
			t2.start();
	    	System.out.println("Passou a thread");
	    }
		
		/*
		Socket a = server.accept();
		
		DatagramSocket cservSocket = new DatagramSocket(12345);
		DatagramPacket recvPacket = new DatagramPacket(new byte[256] , 256);
		cservSocket.receive(recvPacket);
		*/
	}
}

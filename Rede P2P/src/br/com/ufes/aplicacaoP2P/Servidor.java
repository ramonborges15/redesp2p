package br.com.ufes.aplicacaoP2P;

import java.net.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;

public class Servidor {

	public static void main(String[] args) {
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		
		//Cria o Socket do lado servidor da aplicacao.
		DatagramSocket serverSocket;
		try {
			serverSocket = new DatagramSocket(12345);
			
			serverSocket.close();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public int generateID() {
		int value;
		Random random  = new Random(System.currentTimeMillis());
		value = random.nextInt((int) Math.pow(2, 32) - 1);
		//System.out.println(value);
		return value;
	}
	
}

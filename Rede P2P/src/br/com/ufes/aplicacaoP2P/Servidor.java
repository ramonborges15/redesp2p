package br.com.ufes.aplicacaoP2P;

import java.net.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

public class Servidor {

	public static void main(String[] args) {
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		
		//Cria o Socket do lado servidor da aplicacao.
		DatagramSocket serverSocket = new DatagramSocket(12345);
		
		while(true) {
			//Cria um espaço para o pacote.
			DatagramPacket receivePacket = DatagramPacket(receiveData, receiveData.length);
			//Espera a chegada de um pacote.
			serverSocket.receive(receivePacket);
			
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

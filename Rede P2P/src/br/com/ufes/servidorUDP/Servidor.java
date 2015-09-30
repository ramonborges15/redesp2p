package br.com.ufes.servidorUDP;

import java.net.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Servidor {

	public static void main(String[] args) {
		Servidor firstNode = new Servidor();
		firstNode.createNode();
		
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
	
	public void createNode() {
		
	}
	

}

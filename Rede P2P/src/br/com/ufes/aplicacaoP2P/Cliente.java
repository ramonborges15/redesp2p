package br.com.ufes.aplicacaoP2P;


import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.lang.*;
import java.io.ByteArrayInputStream;



public class Cliente {
	
	int sendPort = 12345;
	
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
	
	public void join(int idNewNode, InetAddress ipNodeSuccessor) throws IOException {
		
		//Cria o socket do lado cliente.
		DatagramSocket clientSocket = new DatagramSocket();
		
		//Aloca um espaço de 5 bytes 
		ByteBuffer sendData = ByteBuffer.allocate(5);
		//Codigo da Mensagem de Join
		byte codeMessage[] = {0}; 
		sendData.put(codeMessage);
		sendData.put(intToBytes(idNewNode));
			
		//Cria um pacote onde as informações são anexadas.
		DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , ipNodeSuccessor, sendPort);
		System.out.println("Foi no Join do cliente");
		//Envia o pacote
		clientSocket.send(sendPacket);
		clientSocket.close();
	}
	
	public void leave(int idNodeLeaving, ParticipanteRede nodeAnt, ParticipanteRede nodeSuc) throws IOException {
		//Cria o socket do lado cliente.
		DatagramSocket clientSocker = new DatagramSocket();
		
		//Aloca um espaço de 5 bytes
		ByteBuffer sendData = ByteBuffer.allocate(21);
		byte codeMessage[] = {1};  //Codigo da Mensagem Leave
		sendData.put(codeMessage);
		sendData.put(intToBytes(idNodeLeaving));
		sendData.put(intToBytes(nodeSuc.getId()));
		sendData.put(nodeSuc.getIp().getAddress());
		sendData.put(intToBytes(nodeAnt.getId()));
		sendData.put(nodeAnt.getIp().getAddress()); 
		
		// Enviar pacote para Antecessor
		DatagramPacket sendPacketfst = new DatagramPacket(sendData.array() , sendData.capacity() , nodeAnt.getIp(), sendPort);		
		clientSocker.send(sendPacketfst);
		// Enviar pacote para Sucessor
		DatagramPacket sendPacketsnd = new DatagramPacket(sendData.array() , sendData.capacity() , nodeAnt.getIp(), sendPort);		
		clientSocker.send(sendPacketsnd);
				
		clientSocker.close();
	}
	//
	public void lookup(int idSource, InetAddress ipSource, InetAddress ipDestination) throws IOException {
		//idSource - identificador de origem da procura.
		
		//Cria o socket do lado cliente.
		DatagramSocket clientSocket = new DatagramSocket();
		//Montando pacote
		
		ByteBuffer sendData = ByteBuffer.allocate(13); //Aloca um espaço de 13 bytes
		byte codeMessage[] = {(byte)(2)};
		sendData.put(codeMessage);					//Codigo da mensagem de Lookup
		sendData.put(intToBytes(idSource));			//id meu.
		sendData.put(ipSource.getAddress());	//ip meu.
		sendData.put(intToBytes(idSource));			
		
		//Cria um pacote onde as informações são anexadas.
		DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , ipDestination, sendPort);
		//Envia o pacote
		
		clientSocket.send(sendPacket);
		System.out.println(ipDestination);
		System.out.println(sendPort);
		System.out.println("lookup do cliente");
		
		//clientSocket.close();
	}
	
	public void update(int idSource, int idNewSuc, InetAddress ipNewSuc, InetAddress ipNodeAnt) throws IOException {
		//O que seria esse identificador de origem?
		
		//Cria o socket do lado cliente.
		DatagramSocket clientSocket = new DatagramSocket();
		//Montando pacote
				
		ByteBuffer sendData = ByteBuffer.allocate(13); //Aloca um espaço de 13 bytes
		byte codeMessage[] = {(byte)(3)};
		sendData.put(codeMessage);					//Codigo da mensagem de update
		sendData.put(intToBytes(idSource));			//id de origem (valor que vem do gerador)
		sendData.put(intToBytes(idNewSuc));			//id do novo sucessor .
		sendData.put(ipNewSuc.getAddress());			//ip do novo sucessor.
				
		//Cria um pacote onde as informações são anexadas.
		DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , ipNodeAnt, sendPort);
		//Envia o pacote
		clientSocket.send(sendPacket);
				
		clientSocket.close();
	}
}


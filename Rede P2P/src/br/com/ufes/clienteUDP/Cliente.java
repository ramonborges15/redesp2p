package br.com.ufes.clienteUDP;

import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;
import java.lang.*;

public class Cliente {
	
	int id, codMessage, codeConfirmation;
		
	public static void main(String[] args) {
			Cliente ramon = new Cliente();
			//ramon.generateID();
			//ByteBuffer bb = ByteBuffer.allocate(2);
			
			//System.out.println(bb.capacity());
	}
	
	//Converte um inteiro em um vetor de 4 bytes.
	public byte[] intToBytes(int i) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt(i);
		return bb.array();
	}
	
	public void join(int id, int ipAddressServer) {
		//ipAdressServer - endereco ip conhecido pelo nó que deseja entrar na rede.
		//id - Identificador novo da rede.
		
		//Aloca um espaço de 5 bytes 
		ByteBuffer sendData = ByteBuffer.allocate(5);
		//Codigo da Mensagem de Join
		byte codeMessage[] = {0}; 
		sendData.put(codeMessage);
		sendData.put(intToBytes(id));
		int sendPort = 12345;
		
		//Cria o socket do lado cliente.
		DatagramSocket clientSocket = new DatagramSocket();
		
		//Cria um pacote onde as informações são anexadas.
		DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , ipAddressServer, sendPort);
		
		//Envia o pacote
		clientSocket.send(sendPacket);
		
		ByteBuffer receiveData = ByteBuffer.allocate(18);
		DatagramPacket receivePacket = new DatagramPacket(receiveData.array(), receiveData.capacity());
		
		clientSocket.receive(receivePacket);
		
		System.out.println(receivePacket.getData());
		/*Conversao em inteiro
		int value = 0;
		int shift = (18 - 1 - 1) * 8;
		value += (receivePacket.getData() & 0x000000FF) << shift;
*/
		
		clientSocket.close();
	}
	
	public int generateID() {
		int value;
		Random random  = new Random(System.currentTimeMillis());
		value = random.nextInt((int) Math.pow(2, 32) - 1);
		//System.out.println(value);
		return value;
	}
	
}

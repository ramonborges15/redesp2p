package br.com.ufes.clienteUDP;

import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;
import java.lang.*;
import java.io.ByteArrayInputStream;


public class Cliente {
	
	int codMessage, codeConfirmation;
		
	public static void main(String[] args) {
			Cliente ramon = new Cliente();
			ramon.teste();
	}
	
	private void teste() {
		
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
	
	public void join(InetAddress ipAddressServer) throws IOException {
		//ipAdressServer - endereco ip conhecido pelo nó que deseja entrar na rede.
		
		int bitSuccess = 0;		//bitSuccess sera usado para controle erro 
										// se	0- Identificador enviado ja existe na rede.
										// se	1- Identificador enviado nao existe ainda na rede.
		//Cria o socket do lado cliente.
		DatagramSocket clientSocket = new DatagramSocket();
		
		while(bitSuccess == 0) {
			int id = generateID(); 						//Identificador novo da rede.
			
			//Aloca um espaço de 5 bytes 
			ByteBuffer sendData = ByteBuffer.allocate(5);
			//Codigo da Mensagem de Join
			byte codeMessage[] = {0}; 
			sendData.put(codeMessage);
			sendData.put(intToBytes(id));
			int sendPort = 12345;
			
			//Cria um pacote onde as informações são anexadas.
			DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , ipAddressServer, sendPort);
			
			//Envia o pacote
			clientSocket.send(sendPacket);
			//Aloca espaço de 18 bytes que será usado como estrutura do pacote receivePacket.
			ByteBuffer receiveData = ByteBuffer.allocate(18);
			DatagramPacket receivePacket = new DatagramPacket(receiveData.array(), receiveData.capacity());
			//Socker espera pacote.
			clientSocket.receive(receivePacket);
			ByteArrayInputStream bin = new ByteArrayInputStream(receivePacket.getData());
			
			bin.read(); //lê primeiro byte, mas neste caso é irrelevante.
			bitSuccess = bin.read(); //lê segundo byte referente ao codigo de confirmação/erro e 
										//armazena em bitSuccess. 
		}
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

package br.com.ufes.aplicacaoP2P;

import java.net.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Random;

public class Servidor implements Runnable{
	
	DatagramSocket serverSocket;
	Servidor server;
	Cliente client;
	ParticipanteRede node;
	IntGraphics frame;
	
	public void run() {
		
		//byte[] sendData = new byte[1024];
		//byte[] receiveData = new byte[1024];
		
		try {
			ByteBuffer data = ByteBuffer.allocate(256);
			//Cria o Socket do lado servidor da aplicacao.
			serverSocket =  new DatagramSocket(12345);
			server = new Servidor();
			client = new Cliente();
			node = new ParticipanteRede();
			char op;
			while(true) {
				DatagramPacket recvPacket = new DatagramPacket(new byte[1024] , 1024);
				try {
					serverSocket.receive(recvPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ByteArrayInputStream bin = new ByteArrayInputStream(recvPacket.getData());
				op = (char) bin.read();
				
				switch(op) {
					case '0':
						break;
					case '1': 
						break;
					case '2': 
						lookupServer(bin);
						break;
					case '3': 
						break;
					case 128: 
								break;
					case 129: 
								break;
					case 130: 
								break;
					case 131: 
								break;
					default:
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public ParticipanteRede createNode() throws UnknownHostException, SocketException {
		ParticipanteRede newNode = new ParticipanteRede();
		return newNode;
	}
	
	public void lookupServer(ByteArrayInputStream bin) throws IOException {
		//Desmontando o pacote
		byte[] recvDataid = new byte[4];
		recvDataid[0] =	(byte) bin.read();
		recvDataid[1] =	(byte) bin.read();
		recvDataid[2] =	(byte) bin.read();
		recvDataid[3] =	(byte) bin.read();
		byte[] recvDataip = new byte[4];
		recvDataip[0] = (byte) bin.read();
		recvDataip[1] = (byte) bin.read();
		recvDataip[2] = (byte) bin.read();
		recvDataip[3] = (byte) bin.read();
		byte[] recvDataidWanted = new byte[4];
		recvDataidWanted[0] =	(byte) bin.read();
		recvDataidWanted[1] =	(byte) bin.read();
		recvDataidWanted[2] =	(byte) bin.read();
		recvDataidWanted[3] =	(byte) bin.read();
		
		int id = client.bytesToInt(recvDataid);
		int ip = client.bytesToInt(recvDataip);
		int idWanted = client.bytesToInt(recvDataidWanted);
		
		//Esta entre o antecessor e o id de origem
		if(idWanted < node.getId() && idWanted > node.getIdAnt()) {
			//Montando pacote
			ByteBuffer sendData = ByteBuffer.allocate(13); //Aloca um espaço de 13 bytes
			byte codeMessage[] = {(byte)(128)};
			sendData.put(codeMessage);					//Codigo da mensagem de Lookup
			sendData.put(client.intToBytes(idWanted));
			sendData.put(client.intToBytes(node.getId()));
			sendData.put(node.getIp().getAddress());
			//Cria um pacote onde as informações são anexadas.
			DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , InetAddress.getByAddress(client.intToBytes(ip)), client.sendPort);
			//Envia o pacote
			serverSocket.send(sendPacket);
		}
		//Esta entre o id de origem e o sucessor
		else if(idWanted > node.getId() && idWanted < node.getIdSuc()) {
			//Montando pacote
			ByteBuffer sendData = ByteBuffer.allocate(13); //Aloca um espaço de 13 bytes
			byte codeMessage[] = {(byte)(128)};
			sendData.put(codeMessage);					//Codigo da mensagem de Lookup
			sendData.put(client.intToBytes(idWanted));
			sendData.put(client.intToBytes(node.getIdSuc()));
			sendData.put(node.getIpSuc().getAddress());
			//Cria um pacote onde as informações são anexadas.
			DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , InetAddress.getByAddress(client.intToBytes(ip)), client.sendPort);
			//Envia o pacote
			serverSocket.send(sendPacket);
		}
		//Temos apenas um nó
		else if(id == node.getIdSuc()) {
			//Montando pacote
			ByteBuffer sendData = ByteBuffer.allocate(13); //Aloca um espaço de 13 bytes
			byte codeMessage[] = {(byte)(128)};
			sendData.put(codeMessage);					//Codigo da mensagem  Lookup
			sendData.put(client.intToBytes(idWanted));
			sendData.put(client.intToBytes(node.getId()));
			sendData.put(node.getIp().getAddress());
			//Cria um pacote onde as informações são anexadas.
			DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , InetAddress.getByAddress(client.intToBytes(ip)), client.sendPort);
			//Envia o pacote
			serverSocket.send(sendPacket);
		}
		
		//No de origem é o menor da rede.
		//Ex: rede com 3 nos -- noAntecessorId=27   noOrigemId=8  noSucessorId=14       novoNoId=41
		else if(idWanted < node.getId() && node.getId() < node.getIdAnt() && node.getId() < node.getIdSuc()) {
			//Montando pacote
			ByteBuffer sendData = ByteBuffer.allocate(13); //Aloca um espaço de 13 bytes
			byte codeMessage[] = {(byte)(128)};
			sendData.put(codeMessage);					//Codigo da mensagem de Lookup
			sendData.put(client.intToBytes(idWanted));
			sendData.put(client.intToBytes(node.getId()));
			sendData.put(node.getIp().getAddress());
			//Cria um pacote onde as informações são anexadas.
			DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , InetAddress.getByAddress(client.intToBytes(ip)), client.sendPort);
			//Envia o pacote
			serverSocket.send(sendPacket);
		}
		
		else {
			//Montando pacote
			ByteBuffer sendData = ByteBuffer.allocate(13); //Aloca um espaço de 13 bytes
			byte codeMessage[] = {(byte)(128)};
			sendData.put(codeMessage);					//Codigo da mensagem de Lookup
			sendData.put(client.intToBytes(idWanted));
			sendData.put(client.intToBytes(id));
			sendData.put(client.intToBytes(ip));
			//Cria um pacote onde as informações são anexadas.
			DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , InetAddress.getByAddress(node.getIpSuc().getAddress()), client.sendPort);
			//Envia o pacote
			serverSocket.send(sendPacket);
		}
	}
	
	public int generateID() {
		int value;
		Random random  = new Random(System.currentTimeMillis());
		value = random.nextInt((int) Math.pow(2, 32) - 1);
		return value;
	}
}










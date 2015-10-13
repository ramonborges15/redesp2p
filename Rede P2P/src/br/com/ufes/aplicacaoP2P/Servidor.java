package br.com.ufes.aplicacaoP2P;

import java.net.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Random;

public class Servidor implements Runnable{
	Socket csocket;
	DatagramSocket cservSocket;
	Cliente client;
	ParticipanteRede newNode, aux;
	IntGraphics g;
	
	public Servidor() {
		this.client = new Cliente();
		this.newNode = new ParticipanteRede();
		this.aux = new ParticipanteRede();
	}
	
	
	
	public Servidor(Socket csocket) {
	    this.csocket = csocket;
	}

	public static void main(String args[]) throws Exception {
		System.out.println("Inicia Servidor");
	    ServerSocket ssock = new ServerSocket(12345);
	    while (true) {
	    	Socket sock = ssock.accept();
	    	System.out.println("thread ");
	    	new Thread(new Servidor(sock)).start();
	    }
	}
	
	public void run() {
		
		try {
			char op;
			DatagramSocket theSocket = new DatagramSocket(12345);
			while(true) {
				DatagramPacket recvPacket = new DatagramPacket(new byte[256] , 256);
				theSocket.receive(recvPacket);
				try {
					ByteArrayInputStream bin = new ByteArrayInputStream(recvPacket.getData());
					op = (char) bin.read();
					//
					switch(op) {
						case '0':
							joinAnswer(bin);
							break;
						case '1': 
							break;
						case '2': 
							System.out.println("Menu Servidor");
							lookupAnswer(bin, recvPacket);
							System.out.println("Passsou do Menu Servidor");
							break;
						case '3': 
							break;
						case 128: 
							joinServerAnswer(bin);
							break;
						case 129: 
							break;
						case 130: 
							lookupServerAnswer(bin, newNode);
							break;
						case 131: 
									break;
						default:
					}
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public ParticipanteRede getAux() {
		return aux;
	}
	
	public void setAux(int id, int idAnt, int idSuc, InetAddress ip, InetAddress ipAnt, InetAddress ipSuc) {
		newNode.setId(idSuc);
		newNode.setIdAnt(idAnt);
		newNode.setIdSuc(idSuc);
		
		newNode.setIp(ip);
		newNode.setIpAnt(ipAnt);
		newNode.setIpSuc(ipSuc);
	}

	private void joinAnswer(ByteArrayInputStream bin) throws IOException {
		byte[] id = new byte[4];
		id[0] =	(byte) bin.read();
		id[1] =	(byte) bin.read();
		id[2] =	(byte) bin.read();
		id[3] =	(byte) bin.read();
		
		//Montando pacote
		ByteBuffer sendData = ByteBuffer.allocate(18); //Aloca um espaço de 13 bytes
		byte codeMessage[] = {(byte)(128)};
		sendData.put(codeMessage);					//Codigo da mensagem de Lookup
		sendData.put((byte)1);
		sendData.put(intToBytes(newNode.getIdSuc()));
		sendData.put(newNode.getIpSuc().getAddress());
		sendData.put(intToBytes(newNode.getIdAnt()));
		sendData.put(newNode.getIpAnt().getAddress());
		//Cria um pacote onde as informações são anexadas.
		DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , newNode.getIpSuc(), client.sendPort);
		//Envia o pacote
		cservSocket.send(sendPacket);
		System.out.println("joinAnswer do Servidor");
	}
	
	private void joinServerAnswer(ByteArrayInputStream bin) throws UnknownHostException {
		
		//Desmontando o pacote
		byte[] idSource = new byte[4];
		idSource[0] =	(byte) bin.read();
		idSource[1] =	(byte) bin.read();
		idSource[2] =	(byte) bin.read();
		idSource[3] =	(byte) bin.read();
		byte[] idSuc = new byte[4];
		idSuc[0] = (byte) bin.read();
		idSuc[1] = (byte) bin.read();
		idSuc[2] = (byte) bin.read();
		idSuc[3] = (byte) bin.read();
		byte[] ipSuc = new byte[4];
		ipSuc[0] =	(byte) bin.read();
		ipSuc[1] =	(byte) bin.read();
		ipSuc[2] =	(byte) bin.read();
		ipSuc[3] =	(byte) bin.read();
		
		//newNode.setId(id);
		//newNode.setIp(IP);
		newNode.setIdAnt(bytesToInt(idSuc)); //
		//newNode.setIdSuc(idSuccessor);
		newNode.setIpAnt(InetAddress.getByAddress(ipSuc));  //
		//newNode.setIpSuc(IPSuccessor);
		g.updatingInterface(newNode);
		//update();
		System.out.println("joinServerAnswer");
	}

	public void lookupAnswer(ByteArrayInputStream bin, DatagramPacket pckt) throws IOException {
		System.out.println("!");
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
		
		int id = bytesToInt(recvDataid);
		//int ip = client.bytesToInt(recvDataip);
		int idWanted = bytesToInt(recvDataidWanted);
		System.out.println(InetAddress.getByAddress(recvDataip));
		//Esta entre o antecessor e o id de origem
		if(idWanted <= newNode.getId() && idWanted >= newNode.getIdAnt()) {
			//Guarda a informação anterior para ser mandada a funcao de resposta Join.
			aux.setIdAnt(newNode.getIdAnt());
			aux.setIpAnt(newNode.getIpAnt());
			//Montando pacote
			ByteBuffer sendData = ByteBuffer.allocate(13); //Aloca um espaço de 13 bytes
			byte codeMessage[] = {(byte)(130)};
			sendData.put(codeMessage);					//Codigo da mensagem de Lookup
			sendData.put(intToBytes(idWanted));
			sendData.put(intToBytes(newNode.getId()));
			sendData.put(newNode.getIp().getAddress());
			//Cria um pacote onde as informações são anexadas.
			DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , InetAddress.getByAddress(recvDataip), 12345);
			//Envia o pacote
			cservSocket.send(sendPacket);
		}
		//Esta entre o id de origem e o sucessor
		else if(idWanted > newNode.getId() && idWanted <= newNode.getIdSuc()) {
			//Guarda a informação anterior para ser mandada a funcao de resposta Join.
			aux.setIdAnt(newNode.getId());
			aux.setIpAnt(newNode.getIp());
			//Montando pacote
			ByteBuffer sendData = ByteBuffer.allocate(13); //Aloca um espaço de 13 bytes
			byte codeMessage[] = {(byte)(130)};
			sendData.put(codeMessage);					//Codigo da mensagem de Lookup
			sendData.put(intToBytes(idWanted));
			sendData.put(intToBytes(newNode.getIdSuc()));
			sendData.put(newNode.getIpSuc().getAddress());
			//Cria um pacote onde as informações são anexadas.
			DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , InetAddress.getByAddress(recvDataip), 12345);
			//Envia o pacote
			cservSocket.send(sendPacket);
		}
		//Temos apenas um nó
		else if(newNode.getId() == newNode.getIdSuc() && newNode.getId() == newNode.getIdAnt()) {
			//Guarda a informação anterior para ser mandada a funcao de resposta Join.
			aux.setIdAnt(newNode.getId());
			aux.setIpAnt(newNode.getIp());
			//Montando pacote
			ByteBuffer sendData = ByteBuffer.allocate(13); //Aloca um espaço de 13 bytes
			byte codeMessage[] = {(byte)(5)};
			sendData.put(codeMessage);					//Codigo da mensagem  Lookup
			sendData.put(intToBytes(idWanted));
			sendData.put(intToBytes(newNode.getId()));
			sendData.put(newNode.getIp().getAddress());
			//Cria um pacote onde as informações são anexadas.
			DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , InetAddress.getByAddress(recvDataip), pckt.getPort());
			//Envia o pacote
			cservSocket.send(sendPacket);
		}
		
		//No de origem é o menor da rede.
		//Ex: rede com 3 nos -- noAntecessorId=27   noOrigemId=8  noSucessorId=14       novoNoId=41
		else if(idWanted < newNode.getId() && newNode.getId() < newNode.getIdAnt() && newNode.getId() < newNode.getIdSuc()) {
			//Guarda a informação anterior para ser mandada a funcao de resposta Join.
			aux.setIdAnt(newNode.getIdSuc());
			aux.setIpAnt(newNode.getIpSuc());
			
			//Montando pacote
			ByteBuffer sendData = ByteBuffer.allocate(13); //Aloca um espaço de 13 bytes
			byte codeMessage[] = {(byte)(130)};
			sendData.put(codeMessage);					//Codigo da mensagem de Lookup
			sendData.put(intToBytes(idWanted));
			sendData.put(intToBytes(newNode.getId()));
			sendData.put(newNode.getIp().getAddress());
			//Cria um pacote onde as informações são anexadas.
			DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , InetAddress.getByAddress(recvDataip), 12345);
			//Envia o pacote
			cservSocket.send(sendPacket);
		}
		
		else {
			//Montando pacote
			ByteBuffer sendData = ByteBuffer.allocate(13); //Aloca um espaço de 13 bytes
			byte codeMessage[] = {(byte)(2)};
			sendData.put(codeMessage);					//Codigo da mensagem de Lookup
			sendData.put(intToBytes(id));
			sendData.put(recvDataip);
			sendData.put(intToBytes(idWanted));
			//Cria um pacote onde as informações são anexadas.
			//Envia o pacote
			DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , InetAddress.getByAddress(newNode.getIpSuc().getAddress()), client.sendPort);
			cservSocket.send(sendPacket);
		}
	}
	
	public void lookupServerAnswer(ByteArrayInputStream bin, ParticipanteRede p) throws UnknownHostException {
		
		g.textShow.setText("ID sucessor: " + newNode.getIdSuc() + " IP sucessor: " + newNode.getIpSuc().getHostAddress());
		//Desmontando o pacote
		byte[] recvDataid = new byte[4];
		recvDataid[0] =	(byte) bin.read();
		recvDataid[1] =	(byte) bin.read();
		recvDataid[2] =	(byte) bin.read();
		recvDataid[3] =	(byte) bin.read();
	
		byte[] recvDataidSuc = new byte[4];
		recvDataidSuc[0] = (byte) bin.read();
		recvDataidSuc[1] = (byte) bin.read();
		recvDataidSuc[2] = (byte) bin.read();
		recvDataidSuc[3] = (byte) bin.read();
		
		byte[] recvDataipSuc = new byte[4];
		recvDataipSuc[0] =	(byte) bin.read();
		recvDataipSuc[1] =	(byte) bin.read();
		recvDataipSuc[2] =	(byte) bin.read();
		recvDataipSuc[3] =	(byte) bin.read();
		
		//Atualiza ip e id do sucessor
		newNode.setId(client.bytesToInt(recvDataid));
		newNode.setIdSuc(client.bytesToInt(recvDataidSuc));
		newNode.setIpSuc(InetAddress.getByAddress(recvDataipSuc));
		newNode.setIdAnt(aux.getIdAnt());
		newNode.setIpAnt(aux.getIpAnt());
	}
	
	
	public int generateID() {
		int value;
		Random random  = new Random(System.currentTimeMillis());
		value = random.nextInt((int) Math.pow(2, 32) - 1);
		return value;
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










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
	DatagramPacket recvPacket;
	//DatagramSocket server;
	ParticipanteRede newNodeServer, aux;
	Cliente c;
	IntGraphics g;
	/*
	public Servidor(DatagramSocket csocket) {
	    this.cservSocket = csocket;
	}
	*/
	
	public Servidor(DatagramSocket server, DatagramPacket pkt, ParticipanteRede newNodeServer, ParticipanteRede aux) {
	    this.cservSocket = server;
	    this.recvPacket = pkt;
	    this.newNodeServer = newNodeServer;
		this.aux = aux;
		this.c = new Cliente();
	}
	/*
	public static void main(String args[]) throws Exception {
		System.out.println("Inicia Servidor");
		DatagramSocket theSocket = new DatagramSocket(12345);
	    //while(true) {
	    	new Thread() {
	    		Servidor s = new Servidor(theSocket);
	    		public void run() {
	    			s.run1();
				}
			}.start();
			
			
	   //}
	    
	    /*while (true) {
	    	Socket sock = ssock.accept();
	    	System.out.println("thread ");
	    	new Thread(new Servidor(sock)).start();
	    }*/ /*
	}
	*/
	public void run() {
		int op;
		//while(true) {
			//DatagramPacket recvPacket = new DatagramPacket(new byte[256] , 256);
			//cservSocket.receive(recvPacket);
		
			try {
				ByteArrayInputStream bin = new ByteArrayInputStream(recvPacket.getData());
				
				op = bin.read();
				
				System.out.println(op);
				switch(op) {
					case  0:
						joinAnswer(bin);
						break;
					case  1: 
						leaveAnswer(bin);
						break;
					case  2: 
						lookupAnswer(bin);
						break;
					case  3:
						updateAnswer(bin);
						break;
					case 128: 
						joinServerAnswer(bin);
						break;
					case 129: 
						leaveServerAnswer(bin);
						break;
					case 130: 
						lookupServerAnswer(bin);
						break;
					case 131:
						updateAnswerServer(bin);
						break;
					default:
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		//}
	}
	
	public ParticipanteRede getNode() {
		return newNodeServer;
	}
	
	public void setNode(int id, int idAnt, int idSuc, InetAddress ip, InetAddress ipAnt, InetAddress ipSuc) {
		newNodeServer.setId(idSuc);
		newNodeServer.setIdAnt(idAnt);
		newNodeServer.setIdSuc(idSuc);
		
		newNodeServer.setIp(ip);
		newNodeServer.setIpAnt(ipAnt);
		newNodeServer.setIpSuc(ipSuc);
	}

	private void joinAnswer(ByteArrayInputStream bin) throws IOException {
		byte[] id = new byte[4];
		id[0] =	(byte) bin.read();
		id[1] =	(byte) bin.read();
		id[2] =	(byte) bin.read();
		id[3] =	(byte) bin.read();
		//Atualiza o antecessor.
		
		System.out.println("=> " + recvPacket.getAddress());
		newNodeServer.setIdAnt(bytesToInt(id));
		//newNodeServer.setIpAnt();
		
		//Montando pacote
		ByteBuffer sendData = ByteBuffer.allocate(18); //Aloca um espaço de 18 bytes
		byte codeMessage[] = {(byte)(128)};
		sendData.put(codeMessage);					//Codigo da mensagem de Join
		sendData.put((byte)1);
		sendData.put(intToBytes(newNodeServer.getId()));
		sendData.put(newNodeServer.getIp().getAddress());
		sendData.put(intToBytes(newNodeServer.getIdAnt()));
		sendData.put(newNodeServer.getIpAnt().getAddress());
		//Cria um pacote onde as informações são anexadas.
		System.out.println("Endereco: " );
		DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , recvPacket.getAddress(), 12345);
		//Envia o pacote
		cservSocket.send(sendPacket);
	}
	
	private void joinServerAnswer(ByteArrayInputStream bin) throws IOException {
		
		//Desmontando o pacote
		//byte sucess = (byte) bin.read();
		//Identificador do Sucessor
		byte[] idSuc = new byte[4];
		idSuc[0] =	(byte) bin.read();
		idSuc[1] =	(byte) bin.read();
		idSuc[2] =	(byte) bin.read();
		idSuc[3] =	(byte) bin.read();
		//Ip do Sucessor
		byte[] ipSuc = new byte[4];
		ipSuc[0] = (byte) bin.read();
		ipSuc[1] = (byte) bin.read();
		ipSuc[2] = (byte) bin.read();
		ipSuc[3] = (byte) bin.read();
		//Identificador do Antecessor
		byte[] idAnt = new byte[4];
		idAnt[0] =	(byte) bin.read();
		idAnt[1] =	(byte) bin.read();
		idAnt[2] =	(byte) bin.read();
		idAnt[3] =	(byte) bin.read();
		//Ip do Antecessor
		byte[] ipAnt = new byte[4];
		ipAnt[0] = (byte) bin.read();
		ipAnt[1] = (byte) bin.read();
		ipAnt[2] = (byte) bin.read();
		ipAnt[3] = (byte) bin.read();
		
		newNodeServer.setIdAnt(bytesToInt(idAnt));
		newNodeServer.setIpAnt(InetAddress.getByAddress(ipAnt));
		newNodeServer.setIdSuc(bytesToInt(idSuc));
		newNodeServer.setIpSuc(InetAddress.getByAddress(ipSuc));
		System.out.println("id suc: " + newNodeServer.getIdSuc());
		System.out.println("ip suc: " + newNodeServer.getIpSuc().getHostAddress());
		c.update(newNodeServer.getId(), newNodeServer.getId(), newNodeServer.getIp(), newNodeServer.getIpAnt());
		
	}

	public void lookupAnswer(ByteArrayInputStream bin) throws IOException {
		System.out.println("EU");
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
		
		System.out.println("look answer");
		int id = bytesToInt(recvDataid);
		//int ip = client.bytesToInt(recvDataip);
		int idWanted = bytesToInt(recvDataidWanted);
		System.out.println(InetAddress.getByAddress(recvDataip) + " " +idWanted);
		//Esta entre o antecessor e o id de origem
		System.out.println("------------------------------------------------------------");
		System.out.println(newNodeServer.getId());
		System.out.println(newNodeServer.getIp().getHostAddress());
		System.out.println(newNodeServer.getIdAnt());
		if(idWanted <= newNodeServer.getId() && idWanted >= newNodeServer.getIdAnt()) {
			//Guarda a informação anterior para ser mandada a funcao de resposta Join.
			System.out.println("Passou da primeira condicao");
			//aux.setIdAnt(newNodeServer.getIdAnt());
			//aux.setIpAnt(newNodeServer.getIpAnt());
			//Montando pacote
			ByteBuffer sendData = ByteBuffer.allocate(13); //Aloca um espaço de 13 bytes
			byte codeMessage[] = {(byte)(130)};
			sendData.put(codeMessage);					//Codigo da mensagem de Lookup
			sendData.put(intToBytes(idWanted));
			sendData.put(intToBytes(newNodeServer.getId()));
			sendData.put(newNodeServer.getIp().getAddress());
			//Cria um pacote onde as informações são anexadas.
			DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , InetAddress.getByAddress(recvDataip), 12345);
			//Envia o pacote
			cservSocket.send(sendPacket);
		}
		//Esta entre o id de origem e o sucessor
		else if(idWanted > newNodeServer.getId() && idWanted <= newNodeServer.getIdSuc()) {
			System.out.println("Passou da segunda condicao");
			//Guarda a informação anterior para ser mandada a funcao de resposta Join.
			//aux.setIdAnt(newNodeServer.getId());
			//aux.setIpAnt(newNodeServer.getIp());
			//Montando pacote
			ByteBuffer sendData = ByteBuffer.allocate(13); //Aloca um espaço de 13 bytes
			byte codeMessage[] = {(byte)(130)};
			sendData.put(codeMessage);					//Codigo da mensagem de Lookup
			sendData.put(intToBytes(idWanted));
			sendData.put(intToBytes(newNodeServer.getIdSuc()));
			sendData.put(newNodeServer.getIpSuc().getAddress());
			//Cria um pacote onde as informações são anexadas.
			DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , InetAddress.getByAddress(recvDataip), 12345);
			//Envia o pacote
			cservSocket.send(sendPacket);
		}
		//Temos apenas um nó
		else if(newNodeServer.getId() == newNodeServer.getIdSuc() && newNodeServer.getId() == newNodeServer.getIdAnt()) {
			System.out.println("Passou da terceira condicao");
			//Guarda a informação anterior para ser mandada a funcao de resposta Join.
			//aux.setIdAnt(newNodeServer.getId());
			//aux.setIpAnt(newNodeServer.getIp());
			//Montando pacote
			ByteBuffer sendData = ByteBuffer.allocate(13); //Aloca um espaço de 13 bytes
			byte codeMessage[] = {(byte)(130)};
			sendData.put(codeMessage);					//Codigo da mensagem  Lookup
			sendData.put(intToBytes(idWanted));
			sendData.put(intToBytes(newNodeServer.getId()));
			sendData.put(newNodeServer.getIp().getAddress());
			//Cria um pacote onde as informações são anexadas.
			DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , InetAddress.getByAddress(recvDataip), 12345);
			//Envia o pacote
			cservSocket.send(sendPacket);
			/*
			//Seta as informacoes sucessor e antecessor do no criado, que neste caso são iguais. 
			newNode.setIdAnt(idWanted);
			newNode.setIpAnt(InetAddress.getByAddress(recvDataip));
			newNode.setIdSuc(idWanted);
			newNode.setIpSuc(InetAddress.getByAddress(recvDataip));
			*/
		}
		
		//No de origem é o menor da rede.
		//Ex: rede com 3 nos -- noAntecessorId=27   noOrigemId=8  noSucessorId=14       novoNoId=41
		else if(idWanted < newNodeServer.getId() && newNodeServer.getId() < newNodeServer.getIdAnt() && newNodeServer.getId() < newNodeServer.getIdSuc()) {
			System.out.println("Passou da quarta condicao");
			//Guarda a informação anterior para ser mandada a funcao de resposta Join.
			//aux.setIdAnt(newNodeServer.getIdSuc());
			//aux.setIpAnt(newNodeServer.getIpSuc());
			
			//Montando pacote
			ByteBuffer sendData = ByteBuffer.allocate(13); //Aloca um espaço de 13 bytes
			byte codeMessage[] = {(byte)(130)};
			sendData.put(codeMessage);					//Codigo da mensagem de Lookup
			sendData.put(intToBytes(idWanted));
			sendData.put(intToBytes(newNodeServer.getId()));
			sendData.put(newNodeServer.getIp().getAddress());
			//Cria um pacote onde as informações são anexadas.
			DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , InetAddress.getByAddress(recvDataip), 12345);
			//Envia o pacote
			cservSocket.send(sendPacket);
		}
		
		else {
			System.out.println("Passou para o else");
			//Montando pacote
			ByteBuffer sendData = ByteBuffer.allocate(13); //Aloca um espaço de 13 bytes
			byte codeMessage[] = {(byte)(2)};
			sendData.put(codeMessage);					//Codigo da mensagem de Lookup
			sendData.put(intToBytes(id));
			sendData.put(recvDataip);
			sendData.put(intToBytes(idWanted));
			//Cria um pacote onde as informações são anexadas.
			//Envia o pacote
			DatagramPacket sendPacket = new DatagramPacket(sendData.array() , sendData.capacity() , InetAddress.getByAddress(newNodeServer.getIpSuc().getAddress()), 12345);
			cservSocket.send(sendPacket);
		}
	}
	
	public void lookupServerAnswer(ByteArrayInputStream bin) throws UnknownHostException {
		
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
		
		System.out.println("look server Answer");
		newNodeServer.setIdSuc(bytesToInt(recvDataidSuc));
		newNodeServer.setIpSuc(InetAddress.getByAddress(recvDataipSuc));
		
		System.out.println();
		/*
		//Atualiza ip e id do sucessor
		newNodeServer.setId(bytesToInt(recvDataid));
		newNodeServer.setIdAnt(aux.getIdAnt());
		newNodeServer.setIpAnt(aux.getIpAnt());
		*/
	}
	
	public void updateAnswer(ByteArrayInputStream bin) throws IOException {
		byte sucess = (byte) bin.read();
		byte[] idSource = new byte[4];
		idSource[0] = (byte) bin.read();
		idSource[1] = (byte) bin.read();
		idSource[2] = (byte) bin.read();
		idSource[3] = (byte) bin.read();
	
		byte[] idSuc = new byte[4];
		idSuc[0] = (byte) bin.read();
		idSuc[1] = (byte) bin.read();
		idSuc[2] = (byte) bin.read();
		idSuc[3] = (byte) bin.read();
		
		byte[] ipSuc = new byte[4];
		ipSuc[0] = (byte) bin.read();
		ipSuc[1] = (byte) bin.read();
		ipSuc[2] = (byte) bin.read();
		ipSuc[3] = (byte) bin.read();
		
		newNodeServer.setIdSuc(bytesToInt(idSuc));
		newNodeServer.setIpSuc(InetAddress.getByAddress(ipSuc));
		
		// Montando pacote
		ByteBuffer sendData = ByteBuffer.allocate(6);
		byte codeMessage[] = { (byte) (131) };
		sendData.put(codeMessage); // Codigo da mensagem de update
		sendData.put((byte) 1);
		sendData.put(idSuc);
		// Cria um pacote onde as informações são anexadas.
		DatagramPacket sendPacket = new DatagramPacket(sendData.array(), sendData.capacity(), newNodeServer.getIpSuc(), 12345);
		// Envia o pacote
		cservSocket.send(sendPacket);
	}
	
	public void updateAnswerServer(ByteArrayInputStream bin) {
		//OK
	}
	
	public void leaveAnswer(ByteArrayInputStream bin) throws IOException {
		//Mensagem enviada para o sucessor e antecessor.
		// codigo/id do no saindo da rede/id e ip do suc saindo da rede/id e ip do ant saindo da rede.
		byte[] idNodeLeaving = new byte[4];
		idNodeLeaving[0] = (byte) bin.read();
		idNodeLeaving[1] = (byte) bin.read();
		idNodeLeaving[2] = (byte) bin.read();
		idNodeLeaving[3] = (byte) bin.read();
		
		byte[] idSuc = new byte[4];
		idSuc[0] = (byte) bin.read();
		idSuc[1] = (byte) bin.read();
		idSuc[2] = (byte) bin.read();
		idSuc[3] = (byte) bin.read();
				
		byte[] ipSuc = new byte[4];
		ipSuc[0] = (byte) bin.read();
		ipSuc[1] = (byte) bin.read();
		ipSuc[2] = (byte) bin.read();
		ipSuc[3] = (byte) bin.read();
		
		byte[] idAnt = new byte[4];
		idAnt[0] = (byte) bin.read();
		idAnt[1] = (byte) bin.read();
		idAnt[2] = (byte) bin.read();
		idAnt[3] = (byte) bin.read();

		byte[] ipAnt = new byte[4];
		ipAnt[0] = (byte) bin.read();
		ipAnt[1] = (byte) bin.read();
		ipAnt[2] = (byte) bin.read();
		ipAnt[3] = (byte) bin.read();
		
		// Montando pacote
		ByteBuffer sendData = ByteBuffer.allocate(5);
		byte codeMessage[] = { (byte) (129)};
		sendData.put(codeMessage);
		sendData.put(idNodeLeaving);
		// Cria um pacote onde as informações são anexadas.
		DatagramPacket sendPacket = new DatagramPacket(sendData.array(), sendData.capacity(), recvPacket.getAddress(), 12345);
		// Envia o pacote
		cservSocket.send(sendPacket);
		
		//Sucessor
		if(bytesToInt(idNodeLeaving) < newNodeServer.getId()) {
			newNodeServer.setIdAnt(bytesToInt(idAnt));
			newNodeServer.setIpAnt(InetAddress.getByAddress(ipAnt));
		}
		//Antecessor
		else if(bytesToInt(idNodeLeaving) > newNodeServer.getId()) {
			newNodeServer.setIdSuc(bytesToInt(idSuc));
			newNodeServer.setIpSuc(InetAddress.getByAddress(ipSuc));
		}
	}
	
	public void leaveServerAnswer(ByteArrayInputStream bin) throws UnknownHostException {
		newNodeServer.setIdAnt(0);
		newNodeServer.setIpAnt(InetAddress.getByName("0.0.0.0"));
		newNodeServer.setIdSuc(0);
		newNodeServer.setIpSuc(InetAddress.getByName("0.0.0.0"));
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










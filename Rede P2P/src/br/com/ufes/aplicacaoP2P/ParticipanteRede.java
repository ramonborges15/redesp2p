package br.com.ufes.aplicacaoP2P;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ParticipanteRede {
	private int id;
	private InetAddress IPname;
	private ParticipanteRede successor;
	private ParticipanteRede antecessor;
	
	public static void main(String[] args) {
		
	}
	
	public ParticipanteRede (int id, InetAddress IPname, ParticipanteRede successor, ParticipanteRede antecessor) {
		this.id = id;
		this.IPname = IPname;
		this.successor = successor;
		this.antecessor = antecessor;
	}
	
	public int getId() {
		return this.id;
	}
	
	public InetAddress getIPname() {
		return this.IPname;
	}
	
	public ParticipanteRede getSuccessor() {
		return this.successor ;
	}
	
	public ParticipanteRede getAntecessor() {
		return this.antecessor ;
	}
}








package br.com.ufes.aplicacaoP2P;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class ParticipanteRede {
	private int id;
	private InetAddress IP;
	private int idSuccessor;
	private InetAddress IPSuccessor;
	private int idAntecessor;
	private InetAddress IPAntecessor;
	
	public ParticipanteRede () throws SocketException {
		
		int id = 0;
		Enumeration e = NetworkInterface.getNetworkInterfaces();
		NetworkInterface ni = (NetworkInterface) e.nextElement();
		ni.getInetAddresses().nextElement();
		Enumeration e2 = ni.getInetAddresses();
		e2.nextElement();
		
		this.id = id;
		this.IP = (InetAddress) e2.nextElement();
		this.idAntecessor = id;
		this.IPAntecessor = (InetAddress) e2.nextElement();
		this.idSuccessor = id;
		this.IPSuccessor = (InetAddress) e2.nextElement();
	}
	
	public int getId() {
		return this.id;
	}
	
	public InetAddress getIp() {
		return this.IP;
	}
	
	public int getIdAnt() {
		return this.idAntecessor;
	}
	
	public InetAddress getIpAnt() {
		return this.IPAntecessor;
	}
	
	public int getIdSuc() {
		return this.idSuccessor;
	}
	
	public InetAddress getIpSuc() {
		return this.IPSuccessor;
	}
	
}








package br.com.ufes.aplicacaoP2P;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;

public class ParticipanteRede {
	private int id;
	private InetAddress IP;
	private int idSuccessor;
	private InetAddress IPSuccessor;
	private int idAntecessor;
	private InetAddress IPAntecessor;
	
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public InetAddress getIp() {
		return this.IP;
	}
	
	public void setIp(InetAddress IP) {
		this.IP = IP;
	}
	
	public int getIdAnt() {
		return this.idAntecessor;
	}
	
	public void setIdAnt(int idAntecessor) {
		this.idAntecessor = idAntecessor;
	}
	
	public InetAddress getIpAnt() {
		return this.IPAntecessor;
	}
	
	public void setIpAnt(InetAddress IPAntecessor) {
		this.IPAntecessor = IPAntecessor;
	}
	
	public int getIdSuc() {
		return this.idSuccessor;
	}
	
	public void setIdSuc(int idSuccessor) {
		this.idSuccessor = idSuccessor;
	}
	
	public InetAddress getIpSuc() {
		return this.IPSuccessor;
	}
	
	public void setIpSuc(InetAddress IPSuccessor) {
		this.IPSuccessor = IPSuccessor;
	}
	
}
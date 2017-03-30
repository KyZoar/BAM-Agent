package jus.aor.mobilagent.kernel;

import java.net.Socket;
import java.net.URI;

public class AgentServer implements Runnable {
	protected _Service<?> monService;
	/** Le port ou sera attache l'agent par defaut on prendra le port 1880*/
	protected int port = 1880;
	/** Le nom logique de l'agent*/
	protected String name;
	/** Socket associe a l'agent */
	protected Socket socket;
	
	public AgentServer(int port, String name){
		this.port = port;
		this.name = name;
	}
	
	public void run(){
		
	}
	
	public void AddService(String st, _Service<?> se){
	
	}
	
	private _Agent getAgent(Socket s){
		return null;
	}
	
	public String toString(){
		return null;
	}
	
	public URI site(){
		return null;
	}
}

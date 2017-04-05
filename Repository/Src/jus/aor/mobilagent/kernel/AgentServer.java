package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class AgentServer implements Runnable {
	/** Le port ou sera attache l'agent par defaut on prendra le port 1880*/
	protected int port = 1880;
	/** Le nom logique de l'agent*/
	protected String name;
	/** Socket associe a l'agent */
	protected Socket socket;
	Map<String,_Service<?>> Services;
	
	public AgentServer(int port, String name){
		this.port = port;
		this.name = name;
		Services = new HashMap<String,_Service<?>>();
	}
	
	public void run(){
		ServerSocket socket;
		try {
			socket = new ServerSocket(port);
			
			while(true){
				Socket client = socket.accept();
				System.out.println("Bonjour toi !");
				_Agent agent = getAgent(client);
				agent.reInit(this, name);
				
				new Thread(agent).start();
				client.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void AddService(String st, _Service<?> se){
		Services.put(st, se);
	}
	
	private _Agent getAgent(Socket s) throws IOException, ClassNotFoundException{
		BAMAgentClassLoader bamAgent = new BAMAgentClassLoader(getClass().getClassLoader());
		
		ObjectInputStream input = new ObjectInputStream(s.getInputStream());
		AgentInputStream monAgent = new AgentInputStream(input, bamAgent);
		
		Jar jar = (Jar) input.readObject();
		bamAgent.integrateCode(jar);
		
		_Agent agent = (_Agent) monAgent.readObject();
		
		input.close();
		monAgent.close();
		
		return agent;
	}
	
	public String toString(){
		return name+"\n"+Services.toString();
	}
	
	public URI site(){
		try {
			return new URI("//localhost:"+port);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}

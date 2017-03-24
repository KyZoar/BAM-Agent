package jus.aor.mobilagent.kernel;

public class AgentServer {
	protected _Service<T> monService;
	/** Le port ou sera attache l'agent par defaut on prendra le port 1880*/
	protected int port = 1880;
	/** Le nom logique de l'agent*/
	protected String name;
	
	public AgentServer(int port, String name){
		
	}
}

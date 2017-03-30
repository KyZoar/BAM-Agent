package jus.aor.mobilagent.kernel;

import java.net.URI;
import java.util.List;

import com.sun.xml.internal.ws.api.wsdl.parser.ServiceDescriptor;

public abstract class Agent implements _Agent {

	protected AgentServer agsserv;
	protected Route route;
	public String servname;
	
	public Agent() {}

	@Override
	public final void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(AgentServer agentServer, String serverName) {
		// TODO Auto-generated method stub
		this.agsserv = agentServer;
		this.servname = serverName;
		this.route = new Route(agsserv.site(), _Action.NIHIL);
	}

	@Override
	public void reInit(AgentServer server, String serverName) {
		// TODO Auto-generated method stub
		init(server, serverName);
	}

	@Override
	public final void addEtape(Etape etape) {
		// TODO Auto-generated method stub
		route.add(etape);
	}
	
	protected abstract _Action retour();
	
	protected _Service<?> getService(String servicename) {
		return null;
	}
	
	private void move() {}
	
	protected void move(URI uri) {}
	
	public String route() {
		return route.toString();
	}
	
	public final String toString() {
		return null;
	}

}

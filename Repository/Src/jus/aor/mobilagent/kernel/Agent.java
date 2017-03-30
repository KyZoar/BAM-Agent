package jus.aor.mobilagent.kernel;

import java.util.List;

import com.sun.xml.internal.ws.api.wsdl.parser.ServiceDescriptor;

public class Agent implements _Agent {

	protected AgentServer agsserv;
	protected Route route;
	public String servname;

	@Override
	public void run() {
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
	public void addEtape(Etape etape) {
		// TODO Auto-generated method stub
		route.add(etape);
	}

	@Override
	public void init(List<ServiceDescriptor> services) {
		// TODO Auto-generated method stub
		
	}
	
	public String route() {
		return route.toString();
	}

}

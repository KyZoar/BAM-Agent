package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;

import com.sun.xml.internal.ws.api.wsdl.parser.ServiceDescriptor;

public abstract class Agent implements _Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected AgentServer agentserver;
	protected Route route;
	public String name;

	public Agent() {
	}

	public void init(AgentServer agentServer, String serverName) {
		this.agentserver = agentServer;
		this.name = serverName;
		this.route = new Route(new Etape(agentserver.site(), _Action.NIHIL));
	}

	public void reInit(AgentServer server, String serverName) {
		init(server, serverName);
	}

	public final void addEtape(Etape etape) {
		route.add(etape);
	}

	protected abstract _Action retour();

	protected _Service<?> getService(String servicename) {
		
		return null;
	}

	private void move() {
		this.move(route.get().server);
	}

	protected void move(URI uri) {
		Starter.getLogger().log(Level.FINE,
				String.format("L'agent %s se dirige vers %s:%d ", this, uri.getHost(), uri.getPort()));

		try {
			Socket socket = new Socket(uri.getHost(), uri.getPort());

			OutputStream output = socket.getOutputStream();
			ObjectOutputStream objoutput = new ObjectOutputStream(output);
			ObjectOutputStream objoutput2 = new ObjectOutputStream(output);

			BAMAgentClassLoader bamAgent = (BAMAgentClassLoader) this.getClass().getClassLoader();
			Jar jar = bamAgent.extractCode();

			objoutput.writeObject(jar);
			objoutput2.writeObject(this);

			objoutput2.close();
			objoutput.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final void run() {
		Starter.getLogger().log(Level.INFO, String.format("Agent %s starting execution", this));

		if (this.route.hasNext()) {
			Etape wNextStep = this.route.next();
			Starter.getLogger().log(Level.FINE, String.format("Agent %s running step %s", this, wNextStep));
			wNextStep.action.execute();

			if (this.route.hasNext()) {
				this.move();
			} else {
				Starter.getLogger().log(Level.FINE, String.format("Agent %s has finished its route", this));
			}
		} else {
			Starter.getLogger().log(Level.INFO, String.format("Agent %s had already finished", this));
		}
	}

	public String route() {
		return route.toString();
	}

	public final String toString() {
		return route();
	}

}

package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;

import com.sun.xml.internal.ws.api.wsdl.parser.ServiceDescriptor;

public abstract class Agent implements _Agent {

	protected AgentServer agsserv;
	protected Route route;
	public String servname;

	public Agent() {
	}

	@Override
	public void init(AgentServer agentServer, String serverName) {
		// TODO Auto-generated method stub
		this.agsserv = agentServer;
		this.servname = serverName;
		this.route = new Route(new Etape(agsserv.site(), _Action.NIHIL));
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

	private void move() {
		this.move(this.route.get().server);
	}

	protected void move(URI uri) {
		Starter.getLogger().log(Level.FINE,
				String.format("Agent %s moving to %s:%d ", this, uri.getHost(), uri.getPort()));

		try {
			// Creation of a socket to uri
			Socket wDestSocket = new Socket(uri.getHost(), uri.getPort());

			// Creation of a Stream and a ObjectOutputStream to uri
			OutputStream wOutputStream = wDestSocket.getOutputStream();
			ObjectOutputStream wObjectOutputStream = new ObjectOutputStream(wOutputStream);
			// TODO: Find out why wObjectOutputStream2 is needed
			ObjectOutputStream wObjectOutputStream2 = new ObjectOutputStream(wOutputStream);

			// Retrieve byte code to send
			BAMAgentClassLoader wAgentClassLoader = (BAMAgentClassLoader) this.getClass().getClassLoader();
			Jar wBaseCode = wAgentClassLoader.extractCode();

			// Send Jar in BAMAgentClassLoader
			wObjectOutputStream.writeObject(wBaseCode);
			// Send Agent (this)
			wObjectOutputStream2.writeObject(this);

			// Close the socket
			wObjectOutputStream2.close();
			wObjectOutputStream.close();
			wDestSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String route() {
		return route.toString();
	}

	@Override
	public final void run() {
		// TODO Auto-generated method stub
		Starter.getLogger().log(Level.INFO, String.format("Agent %s starting execution", this));

		// If there is something to do
		if (this.route.hasNext()) {
			Etape wNextStep = this.route.next();
			Starter.getLogger().log(Level.FINE, String.format("Agent %s running step %s", this, wNextStep));
			wNextStep.action.execute();

			if (this.route.hasNext()) {
				// Send Agent to next AgentServer
				this.move();
			} else {
				// There is nothing left to do, Agent has finished
				Starter.getLogger().log(Level.FINE, String.format("Agent %s has finished its route", this));
			}
		} else {
			// If this is reached, it means that the Agent had nothing to do. So
			// just log it ended
			Starter.getLogger().log(Level.INFO, String.format("Agent %s had already finished", this));
		}
	}

	public final String toString() {
		return null;
	}

}

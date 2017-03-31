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
	
	protected AgentServer agsserv;
	protected Route route;
	public String servname;

	public Agent() {
	}

	@Override
	public void init(AgentServer agentServer, String serverName) {
		this.agsserv = agentServer;
		this.servname = serverName;
		this.route = new Route(new Etape(agsserv.site(), _Action.NIHIL));
	}

	@Override
	public void reInit(AgentServer server, String serverName) {
		init(server, serverName);
	}

	@Override
	public final void addEtape(Etape etape) {
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

	@Override
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

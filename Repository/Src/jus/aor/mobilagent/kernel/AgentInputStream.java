package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class AgentInputStream extends ObjectInputStream{
	BAMAgentClassLoader loader;
	
	public AgentInputStream(InputStream input, BAMAgentClassLoader bamAgent) throws IOException{
		super(input);
		loader = bamAgent;
	}
	
	protected Class<?> resolveClass(ObjectStreamClass obj) throws ClassNotFoundException{
		return loader.loadClass(obj.getName());
	}
}

package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.util.jar.JarException;

public class BAMAgentClassLoader extends ClassLoader {

	protected Jar jarRessources;
	
	public BAMAgentClassLoader(String path, ClassLoader className) throws JarException, IOException{
		super(className);
		jarRessources = new Jar(path);
		this.integrateCode(jarRessources);
	}

	public void integrateCode(Jar jarRessources) {
		
		
	}
}

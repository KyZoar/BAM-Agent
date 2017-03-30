package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarException;

public class BAMAgentClassLoader extends ClassLoader {

	protected Jar jar;
	Map<String, byte[]> mapmap = new HashMap<String, byte[]>();
	
	public BAMAgentClassLoader(String path, ClassLoader className) throws JarException, IOException{
		super(className);
		jar = new Jar(path);
		integrateCode(jar);
	}

	public void integrateCode(Jar j) {
		if(jar ==null){
			jar = j;	
		}
		for(Map.Entry<String, byte[]> res : j.classIterator()){
			mapmap.put(res.getKey(),res.getValue());
		}
	}
}

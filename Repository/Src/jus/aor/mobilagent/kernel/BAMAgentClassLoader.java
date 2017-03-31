package jus.aor.mobilagent.kernel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarException;
import java.util.jar.JarOutputStream;

public class BAMAgentClassLoader extends ClassLoader {

	protected Jar jar;
	Map<String, byte[]> mapmap;
	
	public BAMAgentClassLoader(ClassLoader className){
		super(className);
		mapmap = new HashMap<String, byte[]>();
	}
	
	public BAMAgentClassLoader(String path, ClassLoader className) throws JarException, IOException{
		this(className);
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

	public Jar extractCode() throws FileNotFoundException, IOException {
		File monJar = File.createTempFile("jartemporaire", ".jar");
		JarOutputStream outputJar = new  JarOutputStream(new FileOutputStream(monJar));
		for(String className : mapmap.keySet()){
			outputJar.putNextEntry(new JarEntry(className));
			outputJar.write(mapmap.get(className));
		}
		outputJar.close();
		
		return new Jar(monJar.getPath());
	}
}

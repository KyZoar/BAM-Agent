package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarException;

public class BAMServerClassLoader extends URLClassLoader{

	private Jar jar;
	Map<String, byte[]> tabVal = new HashMap<String, byte[]>();
	
	
	public BAMServerClassLoader(URL[] u,ClassLoader cl){
		super(u,cl);
	}	
	
	public Jar getJar() {
		return jar;
	}
	
	public void addUrl(URL u){
		try {
			Jar j = new Jar(u.toString());
			if(jar == null){
				jar=j;
			}
			
			for(Map.Entry<String, byte[]> res : jar.classIterator()){
				String name = res.getKey();
				byte[] T = res.getValue();
				tabVal.put(name, T);
			}
			
		} catch (JarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	public String ToString(){
		return tabVal.toString();
	}
}

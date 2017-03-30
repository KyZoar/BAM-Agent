package objective1;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Chaine extends UnicastRemoteObject implements _Chaine {
	 
	private static final long serialVersionUID = 1L;
	private List<Hotel> nosHotels = new ArrayList<Hotel>();
	
	public Chaine(String... args) throws RemoteException {
	 DocumentBuilder docBuilder = null;
     Document doc = null;
     try {
         docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
         doc = docBuilder.parse(new File(args[0]));

         String name, localisation;
         NodeList list = doc.getElementsByTagName("Hotel");
         NamedNodeMap attrs;
        
         // Boucle principale de lecture
         for (int i = 0; i < list.getLength(); i++) {
             attrs = list.item(i).getAttributes();
             name = attrs.getNamedItem("name").getNodeValue();
             localisation = attrs.getNamedItem("localisation").getNodeValue();
             nosHotels.add(new Hotel(name, localisation));
         }
     } catch (ParserConfigurationException | SAXException | IOException ex) {
         Logger.getLogger(Chaine.class.getName()).log(Level.SEVERE, null, ex);
     }
 }

	@Override
	public List<Hotel> get(String localisation) {
     List<Hotel> resultats = new ArrayList<Hotel>();
     for (Hotel h : nosHotels) {
         if (h.localisation.equals(localisation)) {
             resultats.add(h);
         }
     }

     return resultats;
	}

}

package objective1;

import java.io.File;
import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Annuaire extends UnicastRemoteObject implements _Annuaire  {
	
	private static final long serialVersionUID = 1L;
	private HashMap<String,Numero> annuaire = new HashMap<String, Numero>();

	public Annuaire(String adresseFileHotel) throws ParserConfigurationException, SAXException, IOException {
		
		// Lecture du fichier XML
		DocumentBuilder docBuilder = null;
		Document doc = null;
		docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		doc = docBuilder.parse(new File(adresseFileHotel));

		String name, numero;
		NodeList list = doc.getElementsByTagName("Telephone");
		NamedNodeMap attrs;

		// Boucle principale de lecture
		for (int i = 0; i < list.getLength(); i++) {
			attrs = list.item(i).getAttributes();
			name = attrs.getNamedItem("name").getNodeValue();
			numero = attrs.getNamedItem("numero").getNodeValue();
			annuaire.put(name, new Numero(numero));
		}
	}

	
        @Override
	public Numero get(String abonne) {
		// On renvoie le numero associe
		return annuaire.get(abonne);
	}



}

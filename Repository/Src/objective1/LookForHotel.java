package objective1;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LookForHotel{

	private String localisation;
	public _Annuaire annuaire;
	public _Chaine chaine;
	private List<Hotel> listHotel = new ArrayList<Hotel>();
	private Map<String, Numero> listNumero = new HashMap<String, Numero>();

	private Registry regS1;
	private Registry regS2;
	private Registry regS3;
	private Registry regS4;

	private Logger logger;
	
	
	/**
	 * Définition de l'objet représentant l'interrogation.
	 * @param args les arguments n'en comportant qu'un seul qui indique le critère
	 *          de localisation
	 */
	public LookForHotel(String... args){
		java.util.logging.Level level;
		try {
			level = Level.parse(System.getProperty("LEVEL"));			
		}catch(NullPointerException e) {
			level=java.util.logging.Level.OFF;
		}catch(IllegalArgumentException e) {
			level=java.util.logging.Level.SEVERE;
		}
		
		try{
		String loggerName = "/"+InetAddress.getLocalHost().getHostName()+"/";
		logger = Logger.getLogger(loggerName);
		logger.addHandler(new IOHandler());
		logger.setLevel(level);
		
		logger.log(Level.INFO, "Demarrage du client RMI");
		}catch (java.net.UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(args.length != 1){
			System.out.println("Merci d'entrer un parametre de localisation");
			System.exit(1);
		}
		
		this.localisation = args[0];

		try {
			
			
			this.regS1 = LocateRegistry.getRegistry("localhost", 2222);
			this.regS2 = LocateRegistry.getRegistry("localhost", 3333);
			this.regS3 = LocateRegistry.getRegistry("localhost", 4444);
			this.regS4 = LocateRegistry.getRegistry("localhost", 5555);

		} catch (RemoteException e) {
			logger.log(Level.INFO,"Echec de la connexion RMI" + e);
		}

	}

	
	
	
	
	
	/**
	 * réalise une intérrogation
	 * @return la durée de l'interrogation
	 * @throws RemoteException
	 */
	public long call() {
		long timer = System.currentTimeMillis();
		try{
			logger.log(Level.INFO, "Recuperation des hotels");

			List<_Chaine> listeChaine = new ArrayList<_Chaine>();
			
			listeChaine.add((Chaine) regS1.lookup("Hotels1"));
			listeChaine.add((Chaine) regS2.lookup("Hotels2"));
			listeChaine.add((Chaine) regS3.lookup("Hotels3"));


			for(int i = 0; i < listeChaine.size(); i++){
				//on recupere les hotels de la chaine en cours a : localisation
				List<Hotel> temps =listeChaine.get(i).get(localisation);	
				listHotel.addAll(temps);
			}
			
			logger.log(Level.INFO, "Fin de la recherche");
			
			_Annuaire annuaire = (_Annuaire) regS4.lookup("Annuaire");

			for (Hotel h : listHotel){
				listNumero.put(h.name, annuaire.get(h.name));
			}
			
			logger.log(Level.INFO, "Annuaires OK");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		for (Hotel h : listHotel){
			logger.log(Level.INFO, "resultat : Hotel " + h.name + ", numero " + listNumero.get(h.name));
		}
		long t=System.currentTimeMillis()-timer;
		logger.log(Level.INFO, "Requete execute en " + t  + " ms");

		
		return (t);
		

	}
	
	public static void main(String[] args){
		LookForHotel lfh;
		if(args==null){
			// on g�re le cas sans argument pour faciliter le lancement
			String[] s=new String[1];
			s[0]="Paris";
			lfh=new LookForHotel(s);
		}
		else lfh= new LookForHotel(args);
		
		lfh.call();	
	}

}

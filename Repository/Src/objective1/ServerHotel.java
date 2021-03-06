package objective1;

import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@SuppressWarnings("deprecation")
public class ServerHotel {

	public static void main(String[] args){
		int port = 1099;
		String nomService = "null";
		int numService = 1;
		Registry registry = null;
		
		String[] s;
		
		if(args.length !=3 ){
			System.out.println("Argument par defaut retablis.");
			s= new String[3];
			s[0]="3333";
			s[1]="Hotel";
			s[2]="2";
		}
		else{
			s=args;
		}
		if(args.length != 3 && false){
			//Mis a faux car on g�re le cas sans argument
			System.out.println("Arguments de la forme Port Service Numero");
			System.exit(1);
		}else{
			port = Integer.parseInt(s[0]);
			nomService = s[1];
			numService = Integer.parseInt(s[2]);
		}

		if(System.getSecurityManager()==null){
			System.setSecurityManager(new RMISecurityManager());
		}

		try{
			registry=LocateRegistry.createRegistry(port);
		}catch(RemoteException e){
			System.out.println("Probleme sur le registre : " + e);
		}

		try{

			if(nomService.equals("chaine")){
				String nomGenerique = "Hotels" + numService;
				_Chaine nomChaine = new Chaine("Repository/DataStore/"+nomGenerique+".xml");
				registry.bind(nomGenerique, (Remote) nomChaine);
				System.out.println("Enregistrement sur le serveur reussi (chaine)");
			}else if(nomService.equals("annuaire")){
				_Annuaire nomAnnuaire = new Annuaire("DataStore/Annuaire.xml");
				registry.bind("Annuaire", (Remote) nomAnnuaire);
				System.out.println("Enregistrement sur le serveur reussi (annuaire)");
			}else{
				System.out.println("mauvais nom de service...");
				System.exit(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
import java.io.IOException;
import java.util.Map;
import java.util.List;
/**
 * Programmation multi paradigme TD2
 * @author COGOLUEGNES Charles
 */
public class Main{
	/**
	 * Test de la fonction lireMetro
	 * @param file_ Le nom du fichier
	 */
	private static void testFonctionLireMetro(String file_){
		System.out.println("\n*****testFonctionLireMetro*****\n");
		try{
			Map<Integer, List<Pair>> metro = Util.lireMetro(file_);
			for(Integer ligneMetro : metro.keySet()) {
				System.out.println("Ligne : "+ligneMetro);
				for(Pair coupleStations : metro.get(ligneMetro)) System.out.println("\tDe "+coupleStations.getKey()+" vers "+coupleStations.getValue());
			}
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		System.out.println();
	}

	/**
	 * Test de l'ajout de 2 lignes ayant le même numéro dans une station
	 */
	private static void testStationAjouteLigneRepetition(){
		//Au départ la taille était de 2
		System.out.println("\n*****testStationAjouteLigneRepetition*****\n");
		Station station = new Station("Nation");
		Ligne ligne1 = new Ligne(1);
		Ligne ligne2 = new Ligne(1);
		station.ajouteLigne(ligne1);
		station.ajouteLigne(ligne2);
		System.out.println("Ajout de 2 lignes identiques");
		System.out.println("Taille de la liste des lignes : "+station.getListeLignes().size());
		System.out.println();
		//Pour résoudre le problème il suffit de redéfinir les méthodes hashCode et equals dans Ligne
		//En effet HashSet se base sur ces méthodes pour ne pas ajouter 2 éléments identiques
	}

	/**
	 * Test de l'itérativité de la classe Station
	 */
	private static void testIterationStation(){
		//Station doit implémenter la classe Iterable
		System.out.println("\n*****testIterationStation*****\n");
		Station station = new Station("Nation");
		Ligne ligne1 = new Ligne(1);
		Ligne ligne2 = new Ligne(2);
		Ligne ligne3 = new Ligne(3);
		station.ajouteLigne(ligne1);
		station.ajouteLigne(ligne2);
		station.ajouteLigne(ligne3);
		System.out.println("La station "+station.getNom()+" contient les lignes : ");
		for(Ligne l : station) System.out.println(l.getLigne());
		System.out.println();
	}

	/* Le main */
	public static void main(String[] args){
		boolean testActive = true;

		if(args.length == 0) System.out.println("Usage : Main pathToTheFile");
		else{
			if(testActive){
				testFonctionLireMetro(args[0]);
				testStationAjouteLigneRepetition();
				testIterationStation();
			}
			System.out.println("\n***** Représentation d'un réseau *****");
			System.out.println("Le toString rend comme résultat ^nomStation : [ligne ]*$\n");
			Reseau reseau = null;
			try{
				reseau = Reseau.creeReseauAPartirDuFichier(args[0]);
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
			System.out.println("\ntoString basique : \n");
			System.out.println(reseau.toString());
			System.out.println("\ntoString à partir du graphe : \n");
			System.out.println(reseau.toStringFromGraphe());

			String stationVoisine = "République";
			String[] voisines = reseau.stationsVoisinesDe(stationVoisine);
			System.out.println("\nStations voisines de "+stationVoisine+" : ");
			for(String s : voisines) System.out.println(s);
		}
	}
}

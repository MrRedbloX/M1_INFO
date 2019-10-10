package util;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collection;
import java.text.Normalizer;
/**
 * Cette classe est vouée à proposer des fonctions utiles à d'autres classes
 * @author COGOLUEGNES Charles
 */
public class Util{
	private static String charSpe= "àâäéèêëîïôöùûüç";
  private static String charNor= "aaaeeeeiioouuuc";
  /**
	 * Fonction permettant d'extraire la ligne de métro et les couples de noms de station contenu dans un fichier
	 * @param nomDeFichier_ Le nom du fichier
	 * @return Une map faisant le lien avec la ligne de métro et une liste de couples de noms de station
	 * @throws IOException lors d'une erreur de lecture sur le fichier
	 */
	public static Map<Integer, Collection<Pair>> lireMetro(String nomDeFichier_) throws IOException{
		Map<Integer, Collection<Pair>> ret = new HashMap<Integer, Collection<Pair>>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(nomDeFichier_)));
			StringTokenizer st;
			String ligne = br.readLine();
			while(ligne != null){
				st = new StringTokenizer(ligne, "\""); //On peut utiliser également la méthode split de la classe String
				Integer ligneMetro = Integer.parseInt(st.nextToken().replace(" ", "")); //On récupère le numéro de la ligne avec un espace, qu'on supprime, pour transformer le String en Integer
				String firstStation = normaliser(st.nextToken());
				st.nextToken(); //Juste des espaces rien de très utile
				String secondStation = normaliser(st.nextToken());
				Pair coupleStations = new Pair(firstStation, secondStation);
				if(!ret.containsKey(ligneMetro)) ret.put(ligneMetro, new ArrayList<Pair>()); //Si la map ne contient pas encore la ligne de métro, on l'ajoute et on initialise la liste des couples de sations
				ret.get(ligneMetro).add(coupleStations);
				ligne = br.readLine();
			}
		}
		catch (IOException ioe){
			throw new IOException(ioe);
		}
		return ret;
	}

	/**
	 * Fonction permettant de normaliser une chaine de caractères de la manière suivante :
	 * -Enlève les accents
	 * -Mets les lettres en minuscule
	 * -Enlève les espaces
	 * @param chaine_ La chaine à modifier
	 * @return la chaine modifiée
	 */
	public static String normaliser(String chaine_){
		String ret = new String(chaine_);
		ret = ret.replaceAll("—", "-");
		ret = Normalizer.normalize(ret, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")
			.toLowerCase()
			.replaceAll(" ", "");
		return ret;
	}
}

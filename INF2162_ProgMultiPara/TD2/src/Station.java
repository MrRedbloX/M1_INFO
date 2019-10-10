import java.util.HashSet;
import java.util.Collection;
import java.util.Iterator;
/**
 * Cette classe représente une station de métro
 * @author COGOLUEGNES Charles
 */
public class Station implements Iterable<Ligne>{
	private String nom;
	private HashSet<Ligne> listeLignes;

	/**
	 * Le constructeur de la classe Station à partir du nom seulement
	 * @param nomStation_ Le nom de la station
	 */
	public Station(String nomStation_){
		this.nom = nomStation_;
		this.listeLignes = new HashSet<Ligne>();
	}

	/**
	 * Le constructeur de la classe Station à partir du nom et d'une liste de ligne
	 * @param nomStation_ Le nom de la station
	 * @param listeLignes_ La liste des lignes concernant cette station
	 */
	public Station(String nomStation_, Collection<Ligne> listeLignes_){
		this.nom = nomStation_;
		this.listeLignes = new HashSet<Ligne>(listeLignes_);
	}

	/**
	 * Getter du nom de la classe Station
	 * @return le nom de la station
	 */
	public String getNom(){
		return this.nom;
	}

	/**
	 * Setter du nom de la classe Station
	 * @param newNom_ Le nouveau nom de la station
	 */
	public void setNom(String newNom_){
		this.nom = newNom_;
	}

	/**
	 * Getter sur la liste des lignes de la station
	 * @return la liste des lignes de la station
	 */
	public HashSet<Ligne> getListeLignes(){
		return new HashSet<Ligne>(this.listeLignes);
	}

	/**
	 * Permet d'ajouter une ligne dans la liste
	 * @param ligne_ La ligne a ajouter
	 * @return si la ligne a correctement été ajoutée
	 */
	public boolean ajouteLigne(Ligne ligne_){
		return this.listeLignes.add(ligne_);
	}

	/**
	 * Permet de supprimer une ligne de la liste
	 * @param ligne_ La ligne à supprimer
	 * @return si la ligne a correctement été supprimée
	 */
	public boolean suppLigne(Ligne ligne_){
		return this.listeLignes.remove(ligne_);
	}

	@Override
	public Iterator<Ligne> iterator(){
		return this.listeLignes.iterator();
	}

	@Override
	public int hashCode(){
		return this.nom.hashCode();
	}

	@Override
	public boolean equals(Object o_){
		boolean ret = false;
		if(this.hashCode() == o_.hashCode()) ret = true;
		return ret;
	}
}

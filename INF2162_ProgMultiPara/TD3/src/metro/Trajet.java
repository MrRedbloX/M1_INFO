package metro;
import java.util.Iterator;
import java.util.LinkedList;
/**
 * Cette classe représente un trajet de métro
 * @author COGOLUEGNES Charles
 */
public class Trajet implements Iterable<Station>{
  private LinkedList<Station> trajet;

  /**
   * Le constructeur de Trajet
   */
  public Trajet(){
    this.trajet = new LinkedList<Station>();
  }

  /**
   * Le constructeur de copie de Trajet
   * @param trajet_ Le trajet à copier
   */
  public Trajet(Trajet trajet_){
    this.trajet = new LinkedList<Station>(trajet_.getTrajet());
  }

  /**
   * Permet d'ajouter une station dans le trajet
   * @param station_ La station à ajouter
   */
  public void addStation(Station station_){
    this.trajet.add(station_);
  }

  /**
   * Retourne la liste des stations du trajet
   * @return la liste des stations du trajet
   */
  public LinkedList<Station> getTrajet(){
    return this.trajet;
  }

  /**
   * Retourne le nombre de station présentes dans le trajet
   * @return le nombre de stations dans le trajet
   */
  public int getNbStations(){
    return this.trajet.size();
  }

  @Override
	public Iterator<Station> iterator(){
		return this.trajet.iterator();
	}

	@Override
	public int hashCode(){
		return this.trajet.hashCode();
	}

	@Override
	public boolean equals(Object o_){
		boolean ret = false;
		if(this.hashCode() == o_.hashCode()) ret = true;
		return ret;
	}
}

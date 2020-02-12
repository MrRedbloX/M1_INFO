package metro;
/**
 * Cette classe représente un vecteur entre 2 stations, valué par la ligne
 * @author COGOLUEGNES Charles
 */
public class VecteurStations{
  private Station stationDepart;
  private Station stationArrivee;
  private Ligne ligneCorr;

  /**
   * Le constructeur de VecteurStations
   * @param stationDepart_ La station de départ
   * @param stationArrivee_ La station d'arrivée
   * @param ligneCorr_ La ligne correspondante
   */
  public VecteurStations(Station stationDepart_, Station stationArrivee_, Ligne ligneCorr_){
    this.stationDepart = stationDepart_;
    this.stationArrivee = stationArrivee_;
    this.ligneCorr = ligneCorr_;
  }

  /**
   * Getter sur la station de départ
   * @return la station de départ
   */
  public Station getStationDepart(){
    return this.stationDepart;
  }

  /**
   * Getter sur la station d'arrivée
   * @return la station d'arrivée
   */
  public Station getStationArrivee(){
    return this.stationArrivee;
  }

  /**
   * Getter sur la ligne correspondante
   * @return la ligne correspondante
   */
  public Ligne getLigneCorr(){
    return this.ligneCorr;
  }

  @Override
	public int hashCode(){
		return this.stationDepart.hashCode()+this.stationArrivee.hashCode()+this.ligneCorr.hashCode();
	}

	@Override
	public boolean equals(Object o_){
		boolean ret = false;
		if(this.hashCode() == o_.hashCode()) ret = true;
		return ret;
	}
}

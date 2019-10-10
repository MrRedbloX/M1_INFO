import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.HashSet;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
/**
 * Cette classe représente un réseau de métro
 * @author COGOLUEGNES Charles
 */
public class Reseau{
  private ArrayList<Station> listeStations; //Une liste contenant des stations
  private SimpleWeightedGraph<Station,DefaultWeightedEdge> graphe; //Un graphe de stations valué par les lignes

  /**
   * Le constructeur de la classe Reseau
   */
  private Reseau(){
    this.listeStations = new ArrayList<Station>();
    this.graphe = new SimpleWeightedGraph<Station,DefaultWeightedEdge>(DefaultWeightedEdge.class);
  }

  /**
   * Cette méthode de classe permet de créer un réseau de métro à partir d'un fichier texte
   * @param nomDeFichier_ Le nom du fichier
   * @return Le réseau de métro construit
   * @throws IOException lors d'une erreur de lecture sur le fichier
   */
  public static Reseau creeReseauAPartirDuFichier(String nomDeFichier_) throws IOException{
    Reseau ret = new Reseau();
    Map<Integer, List<Pair>> metro;
    try{
      metro = Util.lireMetro(nomDeFichier_);
    }
    catch (IOException ioe){
      throw new IOException(ioe);
    }
    ret.buildStations(metro);
    return ret;
  }

  /**
   * Cette méthode permet de constuire la liste des stations
   * @param map La map de la lecture du fichier
   */
  private void buildStations(Map<Integer, List<Pair>> map_){
    for(Integer i : map_.keySet()){
      for(Pair p : map_.get(i)){
        Ligne ligne = new Ligne(i); //On créé la ligne
        //On créé les stations
        Station stationDepart = new Station(p.getKey());
        Station stationArrivee = new Station(p.getValue());
        //On leur ajoute la ligne
        stationDepart.ajouteLigne(ligne);
        stationArrivee.ajouteLigne(ligne);
        this.addLigneAndStation(ligne, stationDepart);
        this.addLigneAndStation(ligne, stationArrivee);
        
        //On ajoute les sommets au graphe qui correspondent aux stations
        if(!this.graphe.containsVertex(stationDepart)) this.graphe.addVertex(stationDepart);
        if(!this.graphe.containsVertex(stationArrivee)) this.graphe.addVertex(stationArrivee);
        //On va utiliser les arrêtes par défaut
        DefaultWeightedEdge edge = new DefaultWeightedEdge();
        //On connecte les 2 stations
        this.graphe.addEdge(stationDepart, stationArrivee, edge);
        //On set la valeur de l'arrête au numéro de la ligne
        this.graphe.setEdgeWeight(edge, ligne.getLigne());

      }
    }
  }
  /**
   * Cette méthode permet d'ajouter une station ou si elle existe déjà, d'ajouter une ligne à cette station
   * @param ligne_ La ligne à ajouter
   * @param station_ La station à ajouter
   */
  private void addLigneAndStation(Ligne ligne_, Station station_){
    boolean contains = false;
    for(Station st : this.listeStations){
      if(station_.getNom().equals(st.getNom())){ //Si la station existe déjà
        contains = true;
        st.ajouteLigne(ligne_); //On lui ajoute une nouvelle ligne
        break;
      }
    }
    if(!contains) this.listeStations.add(station_); //Sinon on ajoute la station (qui contient déjà la nouvelle ligne)
  }

  @Override
  public String toString(){
    //Dans ce to String le format est le suivant : ^nomStation : [ligne ]*$
    String ret = "";
    for(Station s : this.listeStations){
      ret+= s.getNom()+" : ";
      for(Ligne l : s.getListeLignes()) ret += l.getLigne()+" ";
      ret += "\n";
    }
    return ret;
  }
  
  /**
   * Un autre toString se basant sur la graphe
   */
  public String toStringFromGraphe(){
    //Dans ce to String le format est le suivant : ^nomStation : [ligne ]*$
    String ret = "";
    for(Station s : this.graphe.vertexSet()){
      ret+= s.getNom()+" : ";
      for(DefaultWeightedEdge dwe : this.graphe.edgesOf(s)) ret += (int)this.graphe.getEdgeWeight(dwe)+" ";
      ret += "\n";
    }
    return ret;
  }

  /**
   * Permet de connaitre les stations voisines à une station donnée
   * @param nomDeStation_ Le nom de la station
   * @return un tableau des noms de stations voisines
   */
  public String[] stationsVoisinesDe(String nomDeStation_){
    HashSet<String> voisines = new HashSet<String>();
    for(Station s : this.graphe.vertexSet()){ //Ici on récupère tout les sommets (ie. toutes les stations)
      if(s.getNom().equals(nomDeStation_)){ //Quand on trouve le bon sommet, qui correspond au nom de la station recherchée
        for(DefaultWeightedEdge dwe : this.graphe.incomingEdgesOf(s)){ //On regarde toutes les arrêtes entrantes
          String stationSource = this.graphe.getEdgeSource(dwe).getNom();
          if(!stationSource.equals(nomDeStation_))voisines.add(stationSource); //On vérifie que l'on ajoute pas le nom de la station recherchée
         }
        for(DefaultWeightedEdge dwe : this.graphe.outgoingEdgesOf(s)){ //On regarde toutes les arrêtes sortantes
          String stationDest = this.graphe.getEdgeTarget(dwe).getNom();
          if(!stationDest.equals(nomDeStation_))voisines.add(stationDest); //On ajoute
         }
        break;
      }
    }
    return voisines.toArray(new String[voisines.size()]);
  }
}

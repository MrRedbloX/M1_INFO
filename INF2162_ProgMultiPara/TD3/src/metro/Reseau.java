package metro;

import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.ArrayList;
import java.io.IOException;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.Optional;
import util.*;
/**
 * Cette classe représente un réseau de métro
 * @author COGOLUEGNES Charles
 */
public class Reseau{
  private Set<Station> listeStations; //Un set contenant des stations
  private Set<VecteurStations> vecteursStations; //Un set contenant des vecteurs de stations valué par une ligne
  private Trajet plusCourtTrajet;

  /**
   * Le constructeur de la classe Reseau
   */
  private Reseau(){
    this.listeStations = new HashSet<Station>();
    this.vecteursStations = new HashSet<VecteurStations>();
  }

  /**
   * Cette méthode de classe permet de créer un réseau de métro à partir d'un fichier texte
   * @param nomDeFichier_ Le nom du fichier
   * @return Le réseau de métro construit
   * @throws IOException lors d'une erreur de lecture sur le fichier
   */
  public static Reseau creeReseauAPartirDuFichier(String nomDeFichier_) throws IOException{
    Reseau ret = new Reseau();
    Map<Integer, Collection<Pair>> metro;
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
  private void buildStations(Map<Integer, Collection<Pair>> map_){
    for(Integer i : map_.keySet()){
      for(Pair p : map_.get(i)){
        Ligne ligne = new Ligne(i); //On créé la ligne
        //On créé les stations
        Station stationDepart = new Station(p.getKey());
        Station stationArrivee = new Station(p.getValue());
        //On leur ajoute la ligne
        stationDepart.ajouteLigne(ligne);
        stationArrivee.ajouteLigne(ligne);
        //On ajoute les stations
        if(!this.listeStations.add(stationDepart)) this.listeStations
          .stream()
          .filter(ls -> ls.getNom().equals(stationDepart.getNom()))
          .forEach(ls -> ls.ajouteLigne(ligne));
        if(!this.listeStations.add(stationArrivee)) this.listeStations
          .stream()
          .filter(ls -> ls.getNom().equals(stationArrivee.getNom()))
          .forEach(ls -> ls.ajouteLigne(ligne));
        //On créé un vecteur
        VecteurStations vs = new VecteurStations(stationDepart, stationArrivee, ligne);
        //On ajoute le vecteur dans le tas
        this.vecteursStations.add(vs);
      }
    }
  }

  @Deprecated
  public String oldToString(){
    //Dans ce to String le format est le suivant : ^nomStation : [ligne ]*$
    String ret = "";
    for(Station s : this.listeStations){
      ret+= s.getNom()+" : ";
      for(Ligne l : s.getListeLignes()) ret += l.getLigne()+" ";
      ret += "\n";
    }
    return ret;
  }

  @Override
  public String toString(){
    String ret = "";
    for(VecteurStations vs : this.vecteursStations){
      ret += vs.getLigneCorr().getLigne()+"  ";
      ret += "\""+vs.getStationDepart().getNom()+"\"  ";
      ret += "\""+vs.getStationArrivee().getNom()+"\"";
      ret += "\n";
    }
    return ret;
  }

  /**
   * Permet de connaitre les stations voisines à une station donnée
   * @param nomDeStation_ Le nom de la station
   * @return une liste des stations voisines
   */
  public Collection<Station> stationsVoisinesDe(String nomDeStation_){
    String nomDeStationNor = Util.normaliser(nomDeStation_);
    Collection<Station> ret = new HashSet<Station>();
    this.vecteursStations //Ici on regarde les stations d'arrivée voisines à la station donnée
      .stream()
      .parallel()
      .filter(vs -> vs.getStationDepart().getNom().equals(nomDeStationNor))
      .forEach(vs -> ret.add(vs.getStationArrivee()));
    this.vecteursStations //Ici on regarde les stations de départ voisines à la station donnée
      .stream()
      .parallel()
      .filter(vs -> vs.getStationArrivee().getNom().equals(nomDeStationNor))
      .forEach(vs -> ret.add(vs.getStationDepart()));
    return ret;
  }

  /**
   * Affiche le chemin d'une station vers une autre station
   * @param stationDepart_ Le nom de la station de départ
   * @param stationArrivee_ Le nom de la station d'arrivée
   * @return Le trajet le plus court entre les 2 stations, null si une ou plusieures stations n'existe pas, un trajet si le trajet n'est pas possible
   */
  public Trajet cheminDeVers(String stationDepart_, String stationArrivee_){
    Trajet ret = null;
    String stationDepartNor = Util.normaliser(stationDepart_);
    String stationArriveeNor = Util.normaliser(stationArrivee_);
    Optional<Station> s1 = this.listeStations //On récupère la station de départ dans la liste
      .stream()
      .filter(ls -> ls.getNom().equals(stationDepartNor))
      .findFirst();
    if(s1.isPresent()){
      Optional<Station> s2 = this.listeStations //On récupère la station d'arrivée dans la liste
        .stream()
        .filter(ls -> ls.getNom().equals(stationArriveeNor))
        .findFirst();
      if(s2.isPresent()){
        Collection<Station> stationVisitees = new HashSet<Station>();
        Trajet t = new Trajet();
        this.plusCourtTrajet = null;
        cheminDeVersRec(s1.get(), s2.get(), stationVisitees, t);
        if(this.plusCourtTrajet != null) ret = new Trajet(this.plusCourtTrajet);  //Quand le trajet est null on renvoie un trajet vide
        else ret = new Trajet();
      }
    }
    return ret;
  }
  /**
   * Methode récursive de cheminDeVers
   * @param stationDepart_ La station de départ
   * @param stationArrivee_ La station d'arrivée
   * @param stationVisitees_ Une collection de station déjà visitées
   * @param trajet_ Le trajet
   */
  private void cheminDeVersRec(Station stationDepart_, Station stationArrivee_, Collection<Station> stationVisitees_, Trajet trajet_){
    Trajet trajet = new Trajet(trajet_); //On créé un trajet à partir du trajet actuel
    trajet.addStation(stationDepart_); //On lui ajoute la station courante
    Collection<Station> stationVisitees = new HashSet<Station>(stationVisitees_); //On duplique la liste des stations déjà visitées
    stationVisitees.add(stationDepart_); //On lui ajoute la station courante
    if(stationDepart_.equals(stationArrivee_)){ //Si un chemin est trouvé
      this.plusCourtTrajet = new Trajet(trajet); //On dit que c'est lui le trajet le plus court
     }
    else{
      Collection<Station> nextStations = this.stationsVoisinesDe(stationDepart_.getNom());
      if(!nextStations.isEmpty()){
        nextStations
          .stream()
          .filter(ns -> !stationVisitees.contains(ns)) //On se base uniquement sur les stations non visitées
          .forEach(ns ->{
            //Si la taile du trajet dépasse celle du trajet le plus court ça ne sert à rien de continuer à explorer
            boolean continueExplorer = true;
            if(plusCourtTrajet != null && plusCourtTrajet.getNbStations() < trajet.getNbStations()) continueExplorer = false;
            if(continueExplorer) cheminDeVersRec(ns, stationArrivee_, stationVisitees, trajet);
          });
      }
    }
  }

  /**
   * Retourne le nombre de station dans le reseau
   * @return le nombre de stations dans le reseau
   */
  public int getNbStations(){
    return this.listeStations.size();
  }

  /**
   * Permet d'ajouter un vecteur de stations
   * @param ligne_ La ligne correspondante
   * @param stationDepart_ La station de départ
   * @param stationArrivee_ La station d'arrivée
   * @return si le vecteur a été ajouté
   */
  private boolean addVecteur(Ligne ligne_, Station stationDepart_, Station stationArrivee_){
    boolean ret = false;
    VecteurStations vs = new VecteurStations(stationDepart_, stationArrivee_, ligne_);
    if(!this.vecteursStations.contains(vs)){
       ret = this.vecteursStations.add(vs);
       if(!this.listeStations.contains(stationDepart_)) this.listeStations.add(stationDepart_);
       if(!this.listeStations.contains(stationArrivee_)) this.listeStations.add(stationArrivee_);
     }
    return ret;
  }

  /**
   * Permet de supprimer un vecteur de stations
   * @param ligne_ La ligne correspondante
   * @param stationDepart_ La station de départ
   * @param stationArrivee_ La station d'arrivée
   * @return si le vecteur a été supprimé
   */
  private boolean suppVecteur(Ligne ligne_, Station stationDepart_, Station stationArrivee_){
    boolean ret = false;
    VecteurStations vs = new VecteurStations(stationDepart_, stationArrivee_, ligne_);
    if(this.vecteursStations.contains(vs)) ret = this.vecteursStations.remove(vs);
    return ret;
  }
}

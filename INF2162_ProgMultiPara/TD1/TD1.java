/**
 * Programmation multi-paradigme TD1
 * @author COGOLUEGNES Charles
 */
public class TD1{
  /**
   * Méthode récursive permettant de faire la somme des valeurs dans un tableau
   * @param tab_ Le tableau
   * @param pos_ La position d'un élément du tableau
   * @return la somme (partielle ou totale) des éléments du tableau
   */
  private static int sommeRecUtil(int[] tab_, int pos_){
    int ret = tab_[pos_];
    if(pos_ < tab_.length-1) ret += sommeRecUtil(tab_, pos_+1);
    return ret;
  }

  /**
   * Méthode permettant de calculer la somme des éléments d'un tableau en utilisant la méthode récursive sommeRecUtil
   * @param tab_ Le tableau
   * @return la somme des éléments du tableau 
   */
  public static int sommeRec(int[] tab_){
    int ret = 0;
    if(tab_.length > 0) ret = sommeRecUtil(tab_, 0);
    return ret;
  }

  /**
   * Méthode récursive permettant de savoir si un élément se trouve ou non dans un tableau
   * @param tab_ Le tableau
   * @param cherche_ L'élément à chercher
   * @param pos_ La position d'un élément
   * @return vrai si l'élément est présent dans le tableau, faux sinon
   */
  private static boolean trouveRecUtil(int[] tab_, int cherche_, int pos_){
    boolean ret = false;
    if(pos_ < tab_.length)
      ret = (cherche_ == tab_[pos_]) ? true : trouveRecUtil(tab_, cherche_, pos_+1);
    return ret;
  }

  /**
   * Méthode permettant de savoir si un élément se trouve ou non dans un tableau
   * @param tab_ Le tableau
   * @param cherche_ L'élément à chercher
   * @return vrai si l'élément est présent dans le tableau, faux sinon
   */
  public static boolean trouveRec(int[] tab_, int cherche_){
    boolean ret = false;
    if(tab_.length > 0) ret = trouveRecUtil(tab_, cherche_, 0);
    return ret;
  }

  /**
   * Méthode récursive permettant de connaitre le nombre d'éléments qui sont strictement inférieurs à une limite donnée
   * @param tab_ Le tableau
   * @param limiteSup_ La limite supérieure
   * @param pos_ La position d'un élément
   * @return le nombre d'éléments inférieurs à la limite
   */
  private static int combienInfARecUtil(int[] tab_, int limiteSup_, int pos_){
    int ret = 0;
    if(pos_ < tab_.length){
      ret = combienInfARecUtil(tab_, limiteSup_, pos_+1);
      if(tab_[pos_] < limiteSup_) ret += 1;

    }
    return ret;
  }

  /**
   * Méthode permettant de connaitre le nombre d'éléments qui sont strictement inférieurs à une limite donnée
   * @param tab_ Le tableau
   * @param limiteSup_ La limite supérieure
   * @return le nombre d'éléments inférieurs à la limite
   */
  public static int combienInfARec(int[] tab_, int limiteSup_){
    int ret = 0;
    if(tab_.length > 0) ret = combienInfARecUtil(tab_, limiteSup_, 0);
    return ret;
  }

  /**
   * Méthode récursive permettant de séparer les éléments pairs et impairs d'un tableau en retournant un tableau à 2 dimensions contenant respectivement les éléments pairs puis impairs
   * @param tab_ Le tableau
   * @return un tableau à 2 dimensions contenant les éléments pairs et impairs
   */
  public static int[][] separePairImpairRec(int[] tab_){
    int[][] ret = new int[1][1];
    return ret;
  }

  /**
   * Méthode récursive permettant d'inverser les éléments d'un tableau dans un autre tableau
   * @param tab_ Le tableau
   * @param pos_ La position d'un élément
   * @return un tableau des éléments inversés
   */
  private static int[] inverseRecUtil(int[] tab_, int pos_){
    int[] ret;
    if(pos_ == tab_.length-1){
      ret = new int[tab_.length];
      ret[0] = tab_[pos_];
    }
    else{
      ret = inverseRecUtil(tab_, pos_+1);
      ret[tab_.length-pos_-1] = tab_[pos_];
    }
    return ret;
  }

  /**
   * Méthode récursive permettant d'inverser les éléments d'un tableau dans un autre tableau
   * @param tab_ Le tableau
   * @return un tableau des éléments inversés
   */
  public static int[] inverseRec(int[] tab_){
    int[] ret = new int[0];
    if(tab_.length > 0 ) ret = inverseRecUtil(tab_, 0);
    return ret;
  }

  /*Le main */
  public static void main(String[] args){
    int[] data = {17,90,32,85,76,22,80,20,36,42,50,62,72,80,99,91,46};
    System.out.print("Le tableau : {");
    for(int i=0; i<data.length; i++){
      if(i == data.length-1) System.out.print(data[i]);
      else System.out.print(data[i]+",");
    }
    System.out.println("}");
    System.out.println();

    System.out.println("La somme des éléments du tableau est : "+sommeRec(data));
    System.out.println();

    int numTrue = 80;
    int numFalse = 78;
    System.out.println(numTrue+" est dans le tableau ? "+trouveRec(data, numTrue));
    System.out.println(numFalse+" est dans le tableau ? "+trouveRec(data, numFalse));
    System.out.println();

    int numCb0 = 5;
    int numCb1 = 18;
    int numCb2 = 80;
    System.out.println(combienInfARec(data, numCb0)+" élément(s) sont inférieur(s) à "+numCb0);
    System.out.println(combienInfARec(data, numCb1)+" élément(s) sont inférieur(s) à "+numCb1);
    System.out.println(combienInfARec(data, numCb2)+" élément(s) sont inférieur(s) à "+numCb2);
    System.out.println();

    System.out.print("Le tableau inversé : {");
    int[] inverse = inverseRec(data);
    for(int i=0; i<inverse.length; i++){
      if(i == inverse.length-1) System.out.print(inverse[i]);
      else System.out.print(inverse[i]+",");
    }
    System.out.println("}");
    System.out.println();
  }
}

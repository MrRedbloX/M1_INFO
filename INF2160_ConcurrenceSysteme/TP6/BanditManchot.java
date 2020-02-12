import java.io.IOException;
public class BanditManchot{
  public static void main(String[] args){
    if(args.length == 1){
      int i;
      Rouleau[] listeRouleaux = new Rouleau[Integer.parseInt(args[0])];
      Verrou verrou = new Verrou();
      for(i=0; i<listeRouleaux.length; i++){
         listeRouleaux[i] = new Rouleau(verrou);
         listeRouleaux[i].start();
       }
      Afficheur afficheur = new Afficheur(listeRouleaux, verrou);
      afficheur.start();
      try{
        Thread.sleep(500); //On sleep un peu avant de permettre au joueur d'arrêter afin d'éviter la triche
        System.in.read();
        verrou.lock();
        afficheur.join();
      }
      catch (IOException ioe){
        ioe.printStackTrace();
      }
      catch (InterruptedException ie){
        ie.printStackTrace();
      }
      finally{
        System.out.println((win(listeRouleaux)) ? "Bravo vous avez gagné !" : "C'est perdu.");
      }
    }
  }
  private static boolean win(Rouleau[] listeRouleaux){
    for(int i=0; i<listeRouleaux.length-1; i++){
      if(listeRouleaux[i].getValue() != listeRouleaux[i+1].getValue()) return false;
    }
    return true;
  }
}

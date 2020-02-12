public class Afficheur extends Thread{
  private Rouleau[] listeRouleaux;
  private Verrou verrou;
  public Afficheur(Rouleau[] listeRouleaux, Verrou verrou){
    this.listeRouleaux = listeRouleaux;
    this.verrou = verrou;
  }
  public void run(){
    int i;
    while(this.verrou.isLock()){
      System.out.print("\r");
      for(i=0; i < this.listeRouleaux.length; i++) System.out.print(this.listeRouleaux[i].getValue()+" ");
      try{
        Thread.sleep(250);
      }
      catch (InterruptedException ie){
        ie.printStackTrace();
      }
    }
    try{
      for(i=0; i < this.listeRouleaux.length; i++) listeRouleaux[i].join();
    }
    catch (InterruptedException ie){
      ie.printStackTrace();
    }
    finally{
      System.out.print("\r");
      for(i=0; i < this.listeRouleaux.length; i++) System.out.print(this.listeRouleaux[i].getValue()+" ");
      System.out.println();
    }
  }
}

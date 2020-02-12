import java.util.Random;
public class Rouleau extends Thread{
  private int val;
  private Verrou verrou;
  public Rouleau(Verrou verrou){
    this.val = 0;
    this.verrou = verrou;
  }
  public void run(){
    Random rand = new Random();
    while(this.verrou.isLock()){
      this.incrementeVal();
      try{
        Thread.sleep(100 + rand.nextInt(401));
      }
      catch (InterruptedException ie){
        ie.printStackTrace();
      }
    }
  }
  private synchronized void incrementeVal(){
    if(this.val == 9) this.val = 0;
    else this.val = this.val+1;
  }
  public synchronized int getValue(){
    return this.val;
  }
}

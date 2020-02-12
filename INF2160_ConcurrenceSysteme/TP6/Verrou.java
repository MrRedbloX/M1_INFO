public class Verrou{
  private boolean verrou;
  public Verrou(){
    this.verrou = true;
  }
  public synchronized boolean isLock(){
    return this.verrou;
  }
  public synchronized void lock(){
    this.verrou = false;
  }
}

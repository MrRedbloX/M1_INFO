package messagebox;
import java.util.HashMap;
import java.util.LinkedList;
import messagebox.Filter;
import messagebox.Message;
import java.util.concurrent.Semaphore;
/**
 * class MessageBox
 *  boite aux lettres
 *  pour la communication de threads
 */
public class MessageBox {
	private LinkedList<Message> tampon;
	private HashMap<Thread,Filter> blockThreads;
	private Semaphore sMutex,sPlaceLibre,sArticle;
	
  /**
   * constructeur
   **/
  public MessageBox(){
	  blockThreads = new ArrayList<Thread>();
	  tampon = new LinkedList<Message>();
	  sMutex = new Semaphore(1,true);
	  sPlaceLibre = new Semaphore(taille,true);// true FIFO
	  sArticle = new Semaphore(0,true);
   };


  /**
   * Depose un message dans la boite aux lettres
   *@param mes le message a deposer
   **/
   public  void  deposit(Message mes){
	   sPlaceLibre.acquireUninterruptibly();
	   sMutex.acquireUninterruptibly();
	   tampon.add(mes);
	   sMutex.release();
	   sArticle.release();
	   for(Thread t : blockThreads.keySet(){
		   if(blockThreads.get(t).isGood(mes) t.notify();
	   }
   }

  /**
   * extrait le plus viel objet dans la MessageBox 
   *         correspondant a un fitre
   *@param f filtre a appliquer (eventuellement null)
   *@return le message extrait
   **/
   public  Message receive(Filter f){
	   if(!tryReceive(f)) {
		   blockThreads.put(Thread.currentThread(),f);
		   Thread.currentThread().wait();
	   }
	   sArticle.acquireUninterruptibly();
	   sMutex.acquireUninterruptibly();
	   Message obj = null;
	   for(Message m : tampon){
		   if(f.isGood(m)){
			   obj = m;
			   break;
		   }
	   }
	   sMutex.release();
	   sPlaceLibre.release();
	   return obj;
   }

 /**
   * test si la MessageBox contient un message qui
   *         repond a un fitre donne
   *@param f filtre a appliquer (eventuellement null)
   *@return Message  en cas de succes (null sinon)
   **/
   private  boolean tryReceive(Filter f){
	   for(Message m : tampon){
		   if(f.isGood(m)) return true;
	   }
	   return false;
   };



}


package messagebox;
import java.util.HashMap;
import java.util.LinkedList;
import messagebox.Filter;
import messagebox.Message;
/**
 * class MessageBox
 *  boite aux lettres
 *  pour la communication de threads
 */
public class MessageBox {
	private LinkedList<Message> tampon;
	private HashMap<Thread,Filter> blockThreads;

  /**
   * constructeur
   **/
  public MessageBox(){
	  this.blockThreads = new HashMap<Thread,Filter>();
	  this.tampon = new LinkedList<Message>();
   };

  /**
   * Depose un message dans la boite aux lettres
   *@param mes le message a deposer
   **/
   public void deposit(Message mes){
		 synchronized(this.tampon){
		   this.tampon.add(mes);
			 synchronized(this.blockThreads){
			   for(Thread t : this.blockThreads.keySet()) {
				   if(this.blockThreads.get(t).isGood(mes)){
						 synchronized(t){
							 t.notify();
							 this.blockThreads.remove(t);
						 }
						 break;
					 }
			   }
			 }
		 }
   }

  /**
   * extrait le plus viel objet dans la MessageBox
   *         correspondant a un fitre
   *@param f filtre a appliquer (eventuellement null)
   *@return le message extrait
   **/
   public Message receive(Filter f){
		 Message ret = tryReceive(f);
	   while(ret == null){
			 synchronized(Thread.currentThread()){
				 try{
					 blockThreads.put(Thread.currentThread(),f);
					 Thread.currentThread().wait();
					 ret = tryReceive(f);
				 }
			 	 catch(InterruptedException ie){
					 ie.printStackTrace();
				 }
			 }
	   }
	   return ret;
   }

 /**
   * test si la MessageBox contient un message qui
   *         repond a un fitre donne
   *@param f filtre a appliquer (dont l'emt peut être éventuellement null)
   *@return Message  en cas de succes (null sinon)
   **/
   private Message tryReceive(Filter f){
		 Message ret = null;
		 synchronized(this.tampon){
		   for(Message m : this.tampon){
			   if(f.isGood(m)){
					  ret =  m;
						this.tampon.remove(m);
						break;
					}
		   }
	 	 }
	   return ret;
   }
}

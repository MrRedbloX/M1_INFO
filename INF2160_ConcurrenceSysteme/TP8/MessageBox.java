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
	private LinkedList<Message> tampon; //La liste des messages
	private HashMap<Thread,Filter> blockThreads; //La map des threads qui attendent un message d'un autre thread

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
				   if(this.blockThreads.get(t).isGood(mes)){ //Si l'éméteur du message correspond au filtre
						 synchronized(t){
							 t.notify(); //On réveil le thread en wait
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
	   while(ret == null){ //Si aucun message n'est trouvé
			 synchronized(Thread.currentThread()){
				 try{
					 blockThreads.put(Thread.currentThread(),f); //On ajoute le thread dans la liste des bloqués
					 Thread.currentThread().wait(); //On l'endort
					 ret = tryReceive(f); //Lorsqu'il est réveilé, il va donc chercher le fameux message
				 }
			 	 catch(InterruptedException ie){
					 ie.printStackTrace();
				 }
			 }
	   }
	   return ret;
   }

 /**
   * test si la MessageBox contient un message qui repond a un fitre donne
   *@param f filtre a appliquer (dont l'emt peut être éventuellement null)
   *@return Message  en cas de succes (null sinon)
   **/
   private Message tryReceive(Filter f){
		 Message ret = null;
		 synchronized(this.tampon){
		   for(Message m : this.tampon){
			   if(f.isGood(m)){ //Si le message correspond au filtre
					  ret = m; //On va le retouner
						this.tampon.remove(m); //Et on le supprime de la liste
						break;
					}
		   }
	 	 }
	   return ret;
   }
}

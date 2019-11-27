package messagebox;
/**
 * structure des messages
 * contenu dans les MessageBox
 */

public class Message{
	private Object message;
   /**
   * constructeur
   *@ o :  object data du messae
   */
    public Message(Object o){
		this.message = o;
   }

  /**
   * extrait l'objet du message
   *@return objet
   */
    public Object getObject(){     
	    return this.message;
    };

  /**
   * extrait le thread emtteur du message
   *@return Thread
   */
    public Thread getSender(){
	    return Thread.currentThread();
    };



}



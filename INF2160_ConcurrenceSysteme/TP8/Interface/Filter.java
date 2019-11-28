package messagebox;

/**
 * class Filter
 * pour filtrer les message d'une MessageBox
 */
/**
   *   class Filter
   *  pour filtere les Message d'une  MessageBox
   */
public class Filter{
	private Thread emt;
  /**
   * contruire un filtre vide
   *  le plus viel objet de la MessageBox
   */
  public Filter(){
	  this.emt = null;
  }

  /**
   *  le plus viel objet de la MessageBox
   *         venant d'un thread particulier
   *@param emt thread emetteur du message
   **/
  public  Filter(Thread emt){
	  this.emt = emt;
  }

  /**
   * applique le filtre sur un message
   *@param mes le message a tester
   *@return true si le message correspond au filtre courant
   */
  public boolean isGood(Message mes){
	  boolean result=false;
	  if(emt != null){
	     if(mes.getSender().hashCode() == emt.hashCode()) result = true;
	  }
	  else result = true;
	  return result;
  };
}

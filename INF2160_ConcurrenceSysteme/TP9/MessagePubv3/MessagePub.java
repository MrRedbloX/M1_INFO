import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Classe Abstraite SynchroLecteurRedacteur
 *     Definit la synchornisation d'un algo de type lecteurs/redacteur
 **/

abstract class SynchroLecteurRedacteur{
	public abstract boolean wantToRedac();
	public abstract void setRedac(boolean redac);
	public abstract void freeLect();
	public abstract void waitLect();
}




class SynchroLecteurRedacteurWaitNotify extends SynchroLecteurRedacteur{
	private boolean redacPrio;
	public SynchroLecteurRedacteurWaitNotify(){
		this.redacPrio = true;
	}
	public boolean wantToRedac(){
		return this.redacPrio;
	}
	public void setRedac(boolean redac){
		this.redacPrio = redac;
	}
	public void freeLect(){
		synchronized(this){
			notifyAll();
		}
	}
	public void waitLect(){
		synchronized(this){
			try{
				wait();
			}
			catch(InterruptedException ie){
				ie.printStackTrace();
			}
		}
	}
}

/**
 * Application d'affichage de  messages publicitaires
 */

/**
 * Afficheur
 */
public  class MessagePub extends Thread{
	static SynchroLecteurRedacteur Synchro = new SynchroLecteurRedacteurWaitNotify();
	static String Messages[];
	static final int NBAFF=3;
	private  int id;

	public MessagePub(int id){
		this.id=id;
	}

	public void run() {
		while(true) {
			try {
				if(Synchro.wantToRedac()) Synchro.waitLect();
				synchronized(Messages){
					if (Messages[id]!= null) System.out.println( Messages[id]);
				}
				Thread.sleep(5000);
			} catch ( Exception  e) {}
		}
	}

	public static void main (String[] args)  {

		Messages = new String[NBAFF];

		for (int i=0; i < NBAFF;i++){
			//			MessagePub mp= new MessagePub(i);
			//			mp.start();
			(new MessagePub(i)).start();
		}

		BufferedReader Clavier = new BufferedReader(new InputStreamReader(System.in));
		int num;

		System.out.println("Saisir un numero d'afficheur < "+NBAFF);

		try {
			while(true) {
				do {
					num=  Integer.parseInt(Clavier.readLine());
				} while (num < 0 || num >= NBAFF);
				Synchro.setRedac(true);
				System.out.println("Saisir le message");
				synchronized(Messages){
					Messages[num]=Clavier.readLine();
				}
				Synchro.setRedac(false);
				Synchro.freeLect();
			}

		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // end of try-catch




} // end of main ()

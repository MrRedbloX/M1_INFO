import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Application d'affichage de  messages publicitaires
 */

/**
 * Afficheur
 */
public  class MessagePub extends Thread{
	static String Messages[];
	static final int NBAFF=3;
	private  int id;

	public MessagePub(int id){
		this.id=id;
	}

	public void run() {
		while(true) {
			try {
				synchronized(Messages){
					if (Messages[id]!= null) System.out.println(Messages[id]);
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

				System.out.println("Saisir le message");

				synchronized(Messages){
					Messages[num]=Clavier.readLine();
				}
			}

		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // end of try-catch




} // end of main ()
